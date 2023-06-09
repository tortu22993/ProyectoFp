package com.example.proyectofp.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.proyectofp.R;
import com.example.proyectofp.clasespojo.Pacientes;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

public class Urgencias extends AppCompatActivity {

    private EditText editTextSearch;
    private View buttonSearch;
    private ImageView homeBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgencias);
        Pacientes paciente = (Pacientes) getIntent().getSerializableExtra("Pacientes");
        homeBoton = findViewById(R.id.homeBoton);
        editTextSearch = findViewById(R.id.buscarEditText);
        buttonSearch = findViewById(R.id.buscarBoton);
        homeBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SesionPaciente.class);
                intent.putExtra("Pacientes", paciente);
                startActivity(intent);
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = editTextSearch.getText().toString();
                if (!searchTerm.isEmpty()) {
                    searchNearbyHospitals(searchTerm);
                }
            }
        });
    }

    private void searchNearbyHospitals(String searchTerm) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(searchTerm, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                String uriString = "geo:" + latitude + "," + longitude + "?q=hospitals";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
