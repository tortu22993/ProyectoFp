package com.example.proyectofp.pantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.proyectofp.R;
import com.example.proyectofp.clasespojo.Pacientes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    private GoogleMap googleMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgencias);
        Pacientes paciente = (Pacientes) getIntent().getSerializableExtra("Pacientes");
        homeBoton = findViewById(R.id.homeBoton);
        editTextSearch = findViewById(R.id.buscarEditText);
        buttonSearch = findViewById(R.id.buscarBoton);

        // Verificar si el permiso ACCESS_FINE_LOCATION ha sido otorgado
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si el permiso no ha sido otorgado, solicitarlo al usuario
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }


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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Habilitar el indicador de ubicación del dispositivo
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }

                // Obtener la ubicación actual
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                // Añadir un marcador en la ubicación actual
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Mi ubicación"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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
