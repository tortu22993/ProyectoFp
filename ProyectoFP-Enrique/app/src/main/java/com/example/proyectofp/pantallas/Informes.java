package com.example.proyectofp.pantallas;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectofp.R;
import com.example.proyectofp.clasespojo.Pacientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Informes extends AppCompatActivity {
    private static final int REQUEST_WRITE_STORAGE = 1;

    private Spinner spinnerArchivos;

    private View descargarBoton, homeBoton;
    private ArrayAdapter<String> archivosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);
        homeBoton = findViewById(R.id.homeBoton);
        Pacientes paciente = (Pacientes) getIntent().getSerializableExtra("Pacientes");
        homeBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SesionPaciente.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });
        String dniUsuario = paciente.getDni().trim();
        StorageReference carpetaRef = FirebaseStorage.getInstance().getReference().child(dniUsuario);
        spinnerArchivos = findViewById(R.id.spinnerArchivos);
        descargarBoton = findViewById(R.id.descargarBoton);
        archivosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        archivosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArchivos.setAdapter(archivosAdapter);
        carpetaRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        List<StorageReference> archivos = listResult.getItems();
                        List<String> nombresArchivos = new ArrayList<>();

                        for (StorageReference archivo : archivos) {
                            nombresArchivos.add(archivo.getName());
                        }

                        archivosAdapter.clear();
                        archivosAdapter.addAll(nombresArchivos);
                        archivosAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Maneja el caso en el que ocurra un error al obtener la lista de archivos
                    }
                });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Si el permiso no está concedido, solicítalo al usuario
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
            return;
        }

        spinnerArchivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Informes", "onItemSelected: Item seleccionado");
                String nombreArchivo = (String) parent.getItemAtPosition(position);
                StorageReference archivoRef = carpetaRef.child(nombreArchivo);
                File localFile = new File(getApplicationContext().getExternalFilesDir(DIRECTORY_DOWNLOADS), nombreArchivo);
                //File localFile = new File(getApplicationContext().getExternalFilesDir(null), nombreArchivo);
                archivoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Aquí puedes hacer algo con el archivo descargado, como mostrarlo o abrirlo en otra aplicación
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Maneja el caso en el que ocurra un error al descargar el archivo
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Maneja el caso en el que no se seleccione ningún archivo

            }
        });

        descargarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreArchivo = spinnerArchivos.getSelectedItem().toString();
                StorageReference st= FirebaseStorage.getInstance().getReference().child(dniUsuario);
                StorageReference archivoRef = st.child(nombreArchivo);
                Log.d("Nombre archivo",nombreArchivo);
                int dotIndex = nombreArchivo.lastIndexOf(".");
                String name = nombreArchivo.substring(0, dotIndex); // "Convenio_Enrique"
                String extension = nombreArchivo.substring(dotIndex);

                archivoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url=uri.toString();
                        downloadFiles(Informes.this,name,extension,DIRECTORY_DOWNLOADS,url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });


    }
    public void downloadFiles(Context context, String fileName, String fileExtension, String destino, String url){
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destino,fileName+fileExtension);

        dm.enqueue(request);

    }
}