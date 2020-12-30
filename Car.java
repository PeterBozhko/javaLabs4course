package com.company;

import serialize.XmlAttribute;
import serialize.XmlObject;
import serialize.XmlTag;

@XmlObject(name = "Car")
public class Car {
    private int horsePower;

    @XmlTag
    Driver driver = new Driver("Stig");

    @XmlTag
    private String name;

    @XmlTag
    private Car child;

    @XmlAttribute(tag = "name")
    public int getHorsePower() {
        return horsePower;
    }

    public Car(String name, int horsePower) {
        this.name = name;
        this.horsePower = horsePower;
    }

    public Car(String name, int horsePower, Car child) {
        this.name = name;
        this.horsePower = horsePower;
        this.child = child;
    }

    @Override
    public String toString() {
        return "Car{" +
                "horsePower=" + horsePower +
                ", driver=" + driver +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }
}
