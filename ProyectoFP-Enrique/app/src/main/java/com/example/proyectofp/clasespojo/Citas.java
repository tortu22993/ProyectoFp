package com.example.proyectofp.clasespojo;

public class Citas {

    private String uid;
    private String fechaHora;
    private String doctor;
    private Pacientes paciente;
    private String centro;

    private String especialidad;

    private boolean ocupada;

    //public Citas(String fechaHora, Pacientes paciente,)

    public Citas(String uid, String fechaHora, String doctor, Pacientes paciente, String centro, boolean ocupada) {
        this.uid = uid;
        this.fechaHora = fechaHora;
        this.doctor = doctor;
        this.paciente = paciente;
        this.centro = centro;
        this.ocupada=ocupada;
    }

    public Citas(String centro, String doctor, String fechaHora, String especialidad) {
        this.centro = centro;
        this.doctor = doctor;
        this.fechaHora = fechaHora;
        this.especialidad = especialidad;
    }

    public Citas(String centro, String doctor, String fechaHora, boolean ocupada) {
        this.centro = centro;
        this.doctor = doctor;
        this.fechaHora = fechaHora;
        this.ocupada = ocupada;
    }
    public String toString() {
        return "Centro: " + centro + "\nDoctor: " + doctor + "\nFecha y Hora: " + fechaHora;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSpec() {
        return especialidad;
    }


    public void setSpec(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }
}
