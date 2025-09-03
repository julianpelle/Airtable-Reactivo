package com.example.demo.DTO;

import java.util.List;

public class AirtableListResponse<T> {
    private java.util.List<AirtableRecord<T>> records;
    private String offset; // para paginación

    // Getters y Setters


    public List<AirtableRecord<T>> getRecords() {
        return records;
    }

    public void setRecords(List<AirtableRecord<T>> records) {
        this.records = records;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
