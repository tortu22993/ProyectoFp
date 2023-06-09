package com.example.proyectofp.clasespojo;

import java.util.ArrayList;
import java.util.List;

public class Doctores {

    private String dni;
    private String nombreApellidos;
    private String especialidad;

    private String contraseña;
    private List<Citas> citasDoctor;

    public Doctores(String dni, String nombre, String especialidad, ArrayList<Citas> citas, String contraseña) {
        this.dni = dni;
        this.nombreApellidos = nombre;
        this.especialidad = especialidad;
        this.citasDoctor = citas;
        this.contraseña = contraseña;
    }
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContraseña() { return contraseña; }

    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Citas> getCitasDoctor() {
        return citasDoctor;
    }

    public void setCitasDoctor(List<Citas> citasDoctor) {
        this.citasDoctor = citasDoctor;
    }
}
