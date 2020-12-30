package com.company;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private String name;
    private List<Parameter<?>> content = new ArrayList<>();
    Section(String name){
        this.name = name;
    }
    public void addParameter(String name, String value){
        Double d = null;
        Integer i = null;
        try {
            d = new Double(value);
        } catch (NumberFormatException e) {
        }
        try {
            i = new Integer(value);
        } catch (NumberFormatException e) {
        }
        if (i != null){
            content.add(new Parameter<>(name, i));
        }else {
            if (d != null){
                content.add(new Parameter<>(name, d));
            }else{
                content.add(new Parameter<>(name, value));
            }
        }
    }

    public List<Parameter<?>> getParameters(){
        return content;
    }
    public String getName() {
        return name;
    }
    public void printAll(){
        for(Parameter parameter:content){
            System.out.println(parameter.getName()+ " " +parameter.getType()+" "+parameter.getValue());
        }
    }
}
