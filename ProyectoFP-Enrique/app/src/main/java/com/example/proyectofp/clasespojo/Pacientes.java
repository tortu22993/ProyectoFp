package com.example.proyectofp.clasespojo;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Pacientes implements Serializable {

    private String dni;
    private String nombreApellidos;

    private Integer edad;

    private String contraseña;

    private List<Citas> citasPaciente;

    public Pacientes(String dni, String contraseña){
        this.dni=dni;
        this.contraseña=contraseña;

    }
    public Pacientes(){


    }

    public Pacientes(String dni, String nombre, ArrayList<Citas> citas, String contraseña) {
        this.dni = dni;
        this.nombreApellidos = nombre;
        this.citasPaciente = citas;
        this.contraseña = contraseña;
    }
    public String getNombre() {
        return nombreApellidos;
    }

    public void setNombre(String nombre) {
        this.nombreApellidos = nombre;
    }

    public String getContraseña() { return contraseña;}

    public void setContraseña(String contraseña) { this.contraseña = contraseña;}

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public List<Citas> getCitasPaciente() {
        return citasPaciente;
    }

    public void setCitasPaciente(List<Citas> citasPaciente) {
        this.citasPaciente = citasPaciente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
