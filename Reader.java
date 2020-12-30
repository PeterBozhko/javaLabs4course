package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class Reader {
    private List<Section> content = new ArrayList<>();
    Reader(String filename) {
        Section sec = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                String s = parcer1(line);
                if (!s.equals("")){
                    sec = parcer2(s, sec);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String parcer1(String str){
        str = str.replaceAll(";.+", "");
        str = str.replaceAll("\\s","");
        return str;
    }
    private Section parcer2(String str, Section current) {
        Pattern patternSection = Pattern.compile("\\[([a-zA-Z0-9_]+?)]");
        Matcher matcherSection = patternSection.matcher(str);
        if (matcherSection.find()) {
            str = matcherSection.group(1);
            current = new Section(str);
            content.add(current);

        }else{
            Pattern patternParaneter = Pattern.compile("^[a-zA-z0-9_]+?=[a-zA-Z0-9_.]+?$");
            Matcher matcherParameter = patternParaneter.matcher(str);
            if (matcherParameter.find()){
                String[] list = str.split("=", 2);
                if (current == null){
                    System.err.println("Неверный формат файла");
                    System.exit(0);
                }
                current.addParameter(list[0],list[1]);
            }else{
                System.err.println("Неверный формат файла");
                System.exit(0);
            }
        }
        return current;

    }

    public Integer getInteger(String section, String parameter){
        Section current = null;
        for (Section sec:content){
            if (sec.getName().equals(section)){
                current = sec;
                break;
            }
        }
        if (current == null){
            throw new RuntimeException("Нет такой секции");
        }
        for (Parameter parameter1:current.getParameters()){
            if (parameter1.getName().equals(parameter)){
                if (parameter1.getType().equals("Integer")){
                    return (Integer) parameter1.getValue();
                }else{
                    throw new RuntimeException("Неверный тип");
                }
            }
        }
        throw new RuntimeException("Нет такого параметра");
    }
    public Double getDouble(String section, String parameter){
        Section current = null;
        for (Section sec:content){
            if (sec.getName().equals(section)){
                current = sec;
                break;
            }
        }
        if (current == null){
            throw new RuntimeException("Нет такой секции");
        }
        for (Parameter parameter1:current.getParameters()){
            if (parameter1.getName().equals(parameter)){
                if (parameter1.getType().equals("Double")){
                    return (Double) parameter1.getValue();
                }else{
                    throw new RuntimeException("Неверный тип");
                }
            }
        }
        throw new RuntimeException("Нет такого параметра");
    }
    public String getString(String section, String parameter){
        Section current = null;
        for (Section sec:content){
            if (sec.getName().equals(section)){
                current = sec;
                break;
            }
        }
        if (current == null){
            throw new RuntimeException("Нет такой секции");
        }
        for (Parameter parameter1:current.getParameters()){
            if (parameter1.getName().equals(parameter)){
                if (parameter1.getType().equals("String")){
                    return (String) parameter1.getValue();
                }else{
                    throw new RuntimeException("Неверный тип");
                }
            }
        }
        throw new RuntimeException("Нет такого параметра");
    }


    public void printContent(){
        for (Section sec:content){
            System.out.println(sec.getName());
            sec.printAll();
        }
    }
}
