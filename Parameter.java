package com.company;

import java.util.Objects;

public class Parameter <T>{
    private String name;
    private T value;
    private String type;

    public Parameter(String name, T value) {
        this.name = name;
        this.value = value;
        this.type = value.getClass().getSimpleName();
    }

    public String getName() { return name; }
    public T getValue() { return value; }
    public String getType() { return type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter<?> parameter = (Parameter<?>) o;
        return Objects.equals(name, parameter.name) &&
                Objects.equals(value, parameter.value) &&
                Objects.equals(type, parameter.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, type);
    }
}
//Убрать pair и заменить его на параметр, который сразу хранит тип,
// Убрать глобальный куррент в ридере
// Если дабл, то только дабл, елси стринг, то только стринг. Ничего другого.