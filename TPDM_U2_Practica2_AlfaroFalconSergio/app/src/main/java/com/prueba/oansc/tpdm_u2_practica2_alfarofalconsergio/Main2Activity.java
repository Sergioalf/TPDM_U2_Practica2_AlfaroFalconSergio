package com.prueba.oansc.tpdm_u2_practica2_alfarofalconsergio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    ListView lista;
    String[] elementos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.Activity2);

        lista = findViewById(R.id.listaDuenos);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                irADetalles(true, elementos[position].split("-")[1]);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irADetalles(false, "");
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Dueno baseDatos = new Dueno(this);
        Dueno[] duenos = baseDatos.obtenerDuenos();
        ArrayAdapter<String> contenidoLista;
        if (duenos == null) {
            String[] vacio = {};
            contenidoLista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vacio);
            miniMensaje("No hay dueños");
        } else {
            elementos = new String[duenos.length];
            for (int i = 0; i < elementos.length; i++) {
                elementos[i] = duenos[i].nombre + "-" + duenos[i].telefono;
            }
            contenidoLista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos);
        }
        lista.setAdapter(contenidoLista);
    }

    private void irADetalles (boolean consulta, String telefono){
        Intent detalles = new Intent(Main2Activity.this, Main4Activity.class);
        detalles.putExtra("registro", !consulta);
        detalles.putExtra("telefono", telefono);
        startActivity(detalles);
    }

    private void miniMensaje (String mensaje) {
        Toast.makeText(Main2Activity.this, mensaje, Toast.LENGTH_LONG).show();
    }

}
