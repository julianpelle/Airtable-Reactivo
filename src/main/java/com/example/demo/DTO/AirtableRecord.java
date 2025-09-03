package com.example.demo.DTO;

public class AirtableRecord<T> {
    private String id; // recordId recXXXX
    private T fields;
    private String createdTime;

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  T getFields() {
        return fields;
    }

    public void setFields(T fields) {
        this.fields = fields;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}