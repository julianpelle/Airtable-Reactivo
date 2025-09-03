package com.example.demo.DTO;

public class EmpleadoFields {
    private Long Id; // Autonumber de Airtable
    private String nombre;
    private String apellido;
    private String NOMBRE_PROYECTO;
    private Boolean activo;

    // Getters y Setters

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNOMBRE_PROYECTO() {
        return NOMBRE_PROYECTO;
    }

    public void setNOMBRE_PROYECTO(String NOMBRE_PROYECTO) {
        this.NOMBRE_PROYECTO = NOMBRE_PROYECTO;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}