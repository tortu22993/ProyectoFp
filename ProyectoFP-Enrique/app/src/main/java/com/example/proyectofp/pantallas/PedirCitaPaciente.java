package com.example.proyectofp.pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Arrays;
import java.util.List;

public class PedirCitaPaciente extends AppCompatActivity {

    String dni;
    ImageView homeBoton;
    View btnReservar;
    Spinner espe, spinnerCitas;
    List<String> especialidadesMedicas = Arrays.asList(
            "Traumatologia",
            "Dermatologia",
            "Digestivo",
            "Neurologia",
            "Cardiologia",
            "Otorrinolaringologia"
            // Agrega las especialidades médicas que desees
    );
    private DatabaseReference citasRef;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_cita_paciente);
        citasRef = FirebaseDatabase.getInstance().getReference("Citas");
        btnReservar = findViewById(R.id.reservarButton);
        homeBoton = findViewById(R.id.homeBoton);
        espe = findViewById(R.id.especialidadSpinner);
        spinnerCitas = findViewById(R.id.citaSpinner);
        spinnerCitas.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, especialidadesMedicas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        espe.setAdapter(spinnerAdapter);
        Pacientes paciente = (Pacientes) getIntent().getSerializableExtra("Pacientes");
        if (paciente != null) {
            dni = paciente.getDni();
        }

        homeBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SesionPaciente.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });

        espe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnReservar.setVisibility(View.VISIBLE);
                spinnerCitas.setVisibility(View.VISIBLE);
                String especialidadSeleccionada = (String) espe.getSelectedItem();
                List<Citas> listaCitas = new ArrayList<>();
                Query consulta = citasRef.orderByChild("Especialidad").equalTo(especialidadSeleccionada);
                consulta.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //StringBuilder stringBuilder = new StringBuilder();
                        // Se ejecuta cuando los datos cambian o se obtienen por primera vez
                        listaCitas.clear();
                        // Recorre los resultados de la consulta
                        for (DataSnapshot citaSnapshot : snapshot.getChildren()) {
                            // Obtén los datos de cada cita
                            String centro = citaSnapshot.child("Centro").getValue(String.class);
                            String doctor = citaSnapshot.child("Doctor").getValue(String.class);
                            String fechaHora = citaSnapshot.child("FechaHora").getValue(String.class);
                            boolean ocup = citaSnapshot.child("Ocupada").getValue(boolean.class);
                            Log.d("Firebase", "Cita encontrada: " + citaSnapshot.getKey());
                            Citas cita = new Citas(centro, doctor, fechaHora, ocup);
                            cita.setUid(citaSnapshot.getKey());

                            if (cita != null && !cita.isOcupada()) {
                                listaCitas.add(cita);
                            }
                        }

                        ArrayAdapter<Citas> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, listaCitas);
                        spinnerCitas.setAdapter(adapter);
                        //citas1.setText(stringBuilder.toString());
                        btnReservar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // Obtén la cita seleccionada del Spinner
                                Citas citaSeleccionada = (Citas) spinnerCitas.getSelectedItem();
                                if (citaSeleccionada != null && citaSeleccionada.getUid() != null) {
                                    // Realiza los cambios en la base de datos (actualiza los valores de Ocupada y Paciente)
                                    DatabaseReference citaRef = citasRef.child(citaSeleccionada.getUid());
                                    citaRef.child("Ocupada").setValue(true);
                                    citaRef.child("Paciente").setValue(dni);

                                    Toast.makeText(getApplicationContext(), "Cita reservada correctamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), SesionPaciente.class);
                                    intent.putExtra("Pacientes", paciente);
                                    startActivity(intent);
                                    finish();
                                    // Realiza cualquier otra acción necesaria después de confirmar la cita
                                    // ...
                                } else {
                                    // No se ha seleccionado ninguna cita, mostrar un mensaje de error o tomar otra acción
                                    Toast.makeText(getApplicationContext(), "Valor nulo", Toast.LENGTH_SHORT).show();
                                    if (citaSeleccionada == null) {
                                        Log.d("Firebase", "La cita seleccionada es nula");
                                    } else {
                                        Log.d("Firebase", "El UID de la cita seleccionada es nulo");
                                    }
                                }

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Se ejecuta si hay un error en la consulta
                        Log.d("Firebase", "Error al obtener las citas: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Entra 2");
            }
        });

    }

    /*private void ShowNotification(){
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(Channel_id,"NEW", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            showNewNotification();
        }

    }
    private void showNewNotification(){
        setPendingIntent(SesionPaciente.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),
                Channel_id).setContentTitle("Mi primera notificacion")
                .setContentText("Ha reservado una cita").setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1, builder.build());

    }
    private void setPendingIntent(Class<?> clsActivity){
        Intent intent = new Intent(this, clsActivity);
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(this);
        stackBuilder.addParentStack(clsActivity);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

    }*/
    /*Button btnConfirmarCita = findViewById(R.id.btnConfirmarCita);
btnConfirmarCita.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Obtén la cita seleccionada del Spinner
        Cita citaSeleccionada = (Cita) spinnerCitas.getSelectedItem();

        // Realiza los cambios en la base de datos (actualiza los valores de Ocupada y Paciente)
        DatabaseReference citaRef = citasRef.child(citaSeleccionada.getUid());
        citaRef.child("Ocupada").setValue(true);
        citaRef.child("Paciente").setValue(dniUsuario);

        // Realiza cualquier otra acción necesaria después de confirmar la cita
        // ...
    }
});*/

}