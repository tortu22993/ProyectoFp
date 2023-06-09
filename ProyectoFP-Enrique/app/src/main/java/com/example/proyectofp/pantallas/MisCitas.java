package com.example.proyectofp.pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofp.R;
import com.example.proyectofp.clasespojo.Citas;
import com.example.proyectofp.clasespojo.Pacientes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MisCitas extends AppCompatActivity {

    TextView txtArea;
    Spinner citas;
    View homeBoton, botonCancelar;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_citas);
        Pacientes paciente = (Pacientes) getIntent().getSerializableExtra("Pacientes");
        txtArea = findViewById(R.id.textoCita);
        citas = findViewById(R.id.listaCitas);
        homeBoton = findViewById(R.id.homeBoton);
        botonCancelar = findViewById(R.id.cancelaButton);
        DatabaseReference citasRef = FirebaseDatabase.getInstance().getReference("Citas");
        List<Citas> listaCitas = new ArrayList<>();
        String dniUsuario = paciente.getDni();

        homeBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SesionPaciente.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });
        // Realiza la consulta en la base de datos
        Query consulta = citasRef.orderByChild("Paciente").equalTo(dniUsuario);
        consulta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCitas.clear();
                // Se ejecuta cuando los datos cambian o se obtienen por primera vez
                for (DataSnapshot citaSnapshot : snapshot.getChildren()) {
                    // Obtén los datos de cada cita
                    String uid = citaSnapshot.getKey();
                    String doctor = citaSnapshot.child("Doctor").getValue(String.class);
                    String centro = citaSnapshot.child("Centro").getValue(String.class);
                    String fecha = citaSnapshot.child("FechaHora").getValue(String.class);
                    String especialidad = citaSnapshot.child("Especialidad").getValue(String.class);
                    // Obtén otros campos necesarios de la cita

                    // Crea un objeto Cita con los datos obtenidos
                    Citas cita = new Citas(centro,doctor, fecha, especialidad);
                    if (cita != null) {
                        listaCitas.add(cita);
                    }
                    // Haz algo con la cita (por ejemplo, mostrarla en un TextView)
                    // ...
                    ArrayAdapter<Citas> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, listaCitas);
                    citas.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Se ejecuta si la consulta es cancelada o hay un error en la base de datos
            }
        });

        // Agrega un listener al Spinner
        citas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtén la cita seleccionada del Spinner
                Citas citaSeleccionada = (Citas) parent.getItemAtPosition(position);

                // Actualiza el TextView con los datos de la cita seleccionada
                String textoCita = "Centro: " + citaSeleccionada.getCentro() + "\n"
                        + "Doctor: " + citaSeleccionada.getDoctor() + "\n"
                        + "Fecha y Hora: " + citaSeleccionada.getFechaHora() + "\n"
                        + "Especialidad: " + citaSeleccionada.getSpec();

                txtArea.setText(textoCita);
                botonCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.d("Cita seleccionada",citaSeleccionada.toString());
                        //Citas citaSeleccionada = (Citas) citas.getSelectedItem();
                        Query query = citasRef.orderByChild("FechaHora").equalTo(citaSeleccionada.getFechaHora());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot citaSnapshot : snapshot.getChildren()) {
                                    String doctor = citaSnapshot.child("Doctor").getValue(String.class);
                                    if (doctor != null && doctor.equals(citaSeleccionada.getDoctor())) {
                                        // Realiza los cambios en el registro
                                        DatabaseReference citaRef = citaSnapshot.getRef();
                                        citaRef.child("Ocupada").setValue(false);
                                        citaRef.child("Paciente").setValue(" ");
                                        // Resto del código...
                                        Toast.makeText(getApplicationContext(), "Cita cancelada correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), SesionPaciente.class);
                                        intent.putExtra("Pacientes", paciente);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Maneja el caso de error o cancelación de la consulta
                            }
                        });

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Maneja el caso de que no se haya seleccionado ningún elemento en el Spinner
                txtArea.setText("");
            }
        });
    }
}