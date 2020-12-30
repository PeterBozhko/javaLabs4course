package com.company;

import serialize.XmlAttribute;
import serialize.XmlObject;
import serialize.XmlTag;

@XmlObject
public class Driver {
    @XmlTag
    private String name;

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                '}';
    }

    public Driver(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getName() {

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
