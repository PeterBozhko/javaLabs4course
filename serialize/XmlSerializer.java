package serialize;

import org.dom4j.*;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class XmlSerializer {

    public Document serialize(Object object) {
        Document document = DocumentHelper.createDocument();

        XmlObject xmlObject = object.getClass().getAnnotation(XmlObject.class);

        if (xmlObject == null){
            try {
                throw new Exception("This is not XmlObject");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        document.add(serializeObject(object, object.getClass()));

        return document;
    }

    private static <T> Element serializeObject(Object object, Class<T> clazz) {
        XmlObject xmlObject = clazz.getAnnotation(XmlObject.class);

        String name;
        if (xmlObject.name().isEmpty()){
            name = clazz.getSimpleName();
        }else{
            name = xmlObject.name();
        }

        Element root = new DefaultElement(name);

        //Проверка на наличие класса-родителя
        Class<?> parent = clazz.getSuperclass();

        XmlObject parentXmlObject = parent.getAnnotation(XmlObject.class);

        if (parentXmlObject != null) {
            Element p = serializeObject(object, parent); //сериализуем родителя

            for (Element el : p.elements()) {
                root.add(el.detach()); // добавляем всех детей, но без ссылок на родителей, чтобы не зациклиться.
            }

            for (Attribute attr : p.attributes()) {
                root.addAttribute(attr.getName(), attr.getValue()); //добавляем все атрибуты
            }
        }
        //Сериализуем теги полей
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(XmlTag.class) != null) {
                Element el = serializeTag(field, object);

                if (root.element(el.getName()) != null) {
                    try {
                        throw new Exception("This tag exists.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                root.add(el);
            }
        }
        //Сериализуем теги методов
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(XmlTag.class) != null) {
                if (method.getReturnType() == void.class
                    || method.getParameterCount() != 0) {
                    try {
                        throw new Exception("Invalid method.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Element el = serializeTag(method, object);

                if (root.element(el.getName()) != null) {
                    try {
                        throw new Exception("This tag exists");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                root.add(el);
            }
        }
        //сериализуем аттрибуты для полей
        for (Field field : clazz.getDeclaredFields()) {
            XmlAttribute xmlAttr = field.getAnnotation(XmlAttribute.class);

            if (xmlAttr != null) {
                Element tag = root;

                //Если аттрибут ссылается на определённый тег, то ставим его
                if (!xmlAttr.tag().isEmpty()) {
                    tag = root.element(xmlAttr.tag());
                }

                serializeAttr(field, object, tag);
            }
        }
        //сериализуем атрибуты для методов
        for (Method method : clazz.getDeclaredMethods()) {
            XmlAttribute xmlAttr = method.getAnnotation(XmlAttribute.class);

            //Если метод void или принимает параметры, то кидаем ошибку
            if (xmlAttr != null) {
                if (method.getReturnType() == void.class || method.getParameterCount() != 0) {
                    try {
                        throw new Exception("Invalid method");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Element tag = root;

                //Если аттрибут ссылается на определённый тег, то ставим его
                if (!xmlAttr.tag().isEmpty()) {
                    tag = root.element(xmlAttr.tag());
                }

                serializeAttr(method, object, tag);
            }
        }

        return root;
    }

    private static Element serializeTag(Member member, Object object) {
        XmlTag xmlTag = null;

        if (member instanceof Method) {
            xmlTag = ((Method) member).getAnnotation(XmlTag.class);
        } else if (member instanceof Field) {
            xmlTag = ((Field) member).getAnnotation(XmlTag.class);
        }

        if (xmlTag == null) {
            return null;
        }

        String tagName;
        if (xmlTag.name().isEmpty()){
            tagName = member.getName();
        }
        else{
            tagName = xmlTag.name();
        }

        if (member instanceof Method && tagName.startsWith("get")) {
            tagName = tagName.substring(3);
        }

        Object val = getReturnedVal(member, object);

        Element el = new DefaultElement(tagName);
        el.add(serializeVal(val));
        return el;
    }

    private static void serializeAttr(Member member, Object object, Element tag) {
        if (tag == null) {
            return;
        }

        XmlAttribute xmlAttr = null;

        if (member instanceof Method) {
            xmlAttr = ((Method) member).getAnnotation(XmlAttribute.class);
        } else if (member instanceof Field) {
            xmlAttr = ((Field) member).getAnnotation(XmlAttribute.class);
        }

        if (xmlAttr == null) {
            return;
        }

        String attrName;

        if (xmlAttr.name().isEmpty()){
            attrName = member.getName();
        }
        else {
            attrName = xmlAttr.name();
        }

        Object val = getReturnedVal(member, object);

        if (member instanceof Method && attrName.startsWith("get")) {
            attrName = Character.toLowerCase(attrName.charAt(3))
                    + attrName.substring(4);
        }

        if (tag.attribute(attrName) != null) {
            try {
                throw new Exception("This attribute exists.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tag.addAttribute(attrName, val.toString());
    }

    private static Object getReturnedVal(Member member, Object object) {
        Object val = null;

        if (member instanceof Method) {
            Method method = (Method) member;
            try {
                method.setAccessible(true);
                val = method.invoke(object);
            } catch (InvocationTargetException | IllegalAccessException ignored) {

            }
        } else if (member instanceof Field) {
            Field field = (Field) member;
            try {
                field.setAccessible(true);
                val = field.get(object);
            } catch (IllegalAccessException ignored) {
            }
        }
        return val;
    }

    private static Node serializeVal(Object object) {
        if (object == null) {
            return new DefaultText("");
        }
        //Если метод возвращает объект, который тоже сериализуется, то сериализуем его
        XmlObject xmlObject = object.getClass().getAnnotation(XmlObject.class);

        if (xmlObject != null) {
            return serializeObject(object, object.getClass());
        } else {
            return new DefaultText(object.toString());
        }
    }
}
