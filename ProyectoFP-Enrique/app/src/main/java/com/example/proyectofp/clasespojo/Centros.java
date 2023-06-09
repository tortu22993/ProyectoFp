package com.example.proyectofp.clasespojo;

import java.util.ArrayList;
import java.util.List;

public class Centros {
    private String uid;
    private String nombre;
    private String direccion;
    private String ciudad;

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    private List<Doctores> doctoresCentro = new ArrayList<Doctores>();
    private List<Pacientes> pacientesCentro = new ArrayList<Pacientes>();
    private List<Citas> citasCentro = new ArrayList<Citas>();

    public Centros(String uid, String nombre, String direccion, String ciudad,
                   List<Doctores> doctoresCentro, List<Pacientes> pacientesCentro,
                   List<Citas> citasCentro) {
        this.uid = uid;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this. doctoresCentro = doctoresCentro;
        this.pacientesCentro = pacientesCentro;
        this.citasCentro = citasCentro;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDoctoresCentro(List<Doctores> doctoresCentro) {
        this.doctoresCentro = doctoresCentro;
    }

    public List<Doctores> getDoctoresCentro() {
        return doctoresCentro;
    }

    public void setPacientesCentro(List<Pacientes> pacientesCentro) {
        this.pacientesCentro = pacientesCentro;
    }

    public List<Pacientes> getPacientesCentro() {
        return pacientesCentro;
    }

    public void setCitasCentro(List<Citas> citasCentro) {
        this.citasCentro = citasCentro;
    }

    public List<Citas> getCitasCentro() {
        return citasCentro;
    }
}
