package com.pass.grabarmapas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager lm = null;

    Spinner spnRutas = null;
    Button btnGrabar = null;
    ArrayList<Ubicacion> listaLocalizaciones = null;
    Button btnParar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        spnRutas = findViewById(R.id.spnRutas);
        btnGrabar = findViewById(R.id.btnGrabar);
        listaLocalizaciones = new ArrayList<Ubicacion>();
        btnParar = findViewById(R.id.btnParar);
        btnParar.setVisibility(View.INVISIBLE);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnParar.setVisibility(View.VISIBLE);
                btnGrabar.setVisibility(View.INVISIBLE);
                comprobarPermisos();
            }
        });

        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGrabar.setVisibility(View.VISIBLE);
                btnParar.setVisibility(View.INVISIBLE);
                Log.d("POSICION", listaLocalizaciones.toString());
                ListaUbicaciones listaUbicaciones = new ListaUbicaciones();
                listaUbicaciones.setUbicaciones(listaLocalizaciones);
                AccesoADatos.grabarRuta(listaUbicaciones);
            }
        });
    }

    public void comprobarPermisos() {
        //Esto significa que si no tenemos permiso FINE y tampovo el COARSE, entra en el if y hace
        // return para evitar acceder a la ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] permisos = {Manifest.permission.ACCESS_FINE_LOCATION};

            requestPermissions(permisos, 666);

            // TODO: Consider callings
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            obtenerUbicacion();
        }


    }

    private void obtenerUbicacion() {

        LocationListener oyenteLocalizacion = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                listaLocalizaciones.add(new Ubicacion(location.getLatitude(), location.getLongitude()));
                Log.d("POSICION", location.getLatitude() + ", " + location.getLongitude());
                lm.removeUpdates(this);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Aquí no entramos nunca porque la llamada a este método se hace desde el else
            //del método comprobarPermisos()
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, oyenteLocalizacion);
        Location localizacion = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (localizacion != null) {
            Log.d("POSICION", localizacion.getLatitude() + ", " + localizacion.getLongitude());
        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}