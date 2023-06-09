package com.example.proyectofp.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofp.R;
import com.example.proyectofp.clasespojo.Pacientes;

public class SesionPaciente extends AppCompatActivity {
    TextView  nombre;
    View pedirCita, misCit, informes, urgencias;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_paciente);
        nombre = findViewById(R.id.txtnombre);
        pedirCita = findViewById(R.id.btnPedirCita);
        urgencias = findViewById(R.id.botonUrgencias);
        misCit = findViewById(R.id.btnProximaCi);
        informes = findViewById(R.id.btnInformes);
        Pacientes paciente = (Pacientes) getIntent().getSerializableExtra("Pacientes");
        if (paciente != null) {
            String dni = paciente.getDni();
            String contraseña = paciente.getContraseña();
            String nombre1 = paciente.getNombre();
            nombre.setText(nombre1);

            // Utilizar los datos del paciente como desees
            // ...
        }
        urgencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Urgencias.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });
        pedirCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PedirCitaPaciente.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });
        misCit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MisCitas.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });
        informes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Informes.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });
    }
}