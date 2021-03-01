package com.pass.grabarmapas;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccesoADatos {

    public static void grabarRuta(ListaUbicaciones listaLocalizaciones) {
// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("localizacion");

        myRef.setValue(listaLocalizaciones);


    }
}
