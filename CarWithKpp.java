package com.company;

import serialize.XmlObject;
import serialize.XmlTag;

@XmlObject(name = "CarWithKpp")
public class CarWithKpp extends Car {
    @XmlTag
    private int amountGears;

    public CarWithKpp(String name, int horsePower, int amountGears) {
        super(name, horsePower, new Car("aaa", 10));
        this.amountGears = amountGears;
    }

    public int getAmountGears() {
        return amountGears;
    }

    @Override
    public String toString() {
        return "CarWithKpp{" +
                "amountGears=" + amountGears +
                ", driver=" + driver +
                '}';
    }
}
