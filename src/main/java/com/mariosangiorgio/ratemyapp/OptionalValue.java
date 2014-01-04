package com.mariosangiorgio.ratemyapp;

public class OptionalValue<T> {
    private final T value;

    public OptionalValue(){
        this.value = null;
    }

    public OptionalValue(T value){
        if(value == null){
            throw new IllegalArgumentException("Expected a non-null value");
        }
        this.value = value;
    }

    public T value(){
        return value;
    }

    public boolean hasValue(){
        return value != null;
    }
}