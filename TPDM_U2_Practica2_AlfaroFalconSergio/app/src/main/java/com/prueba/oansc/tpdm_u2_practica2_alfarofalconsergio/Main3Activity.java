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

public class Main3Activity extends AppCompatActivity {

    ListView lista;
    String[] elementos;
    String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.Activity3);

        lista = findViewById(R.id.listaPoliza);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                irADetalles(true, ids[position]);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irADetalles(false, "0");
            }
        });
    }

    protected void onStart () {
        super.onStart();
        Poliza baseDatos = new Poliza(this);
        Poliza[] polizas = baseDatos.obtenerPolizas();
        ArrayAdapter<String> contenidoLista;
        if (polizas == null) {
            String[] vacio = {};
            contenidoLista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vacio);
            miniMensaje("No hay pólizas");
        } else {
            elementos = new String[polizas.length];
            ids = new String[elementos.length];
            for (int i = 0; i < elementos.length; i++) {
                elementos[i] = polizas[i].idDueno + " - " + polizas[i].modelo;
                ids[i] = polizas[i].idPoliza + "";
            }
            contenidoLista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos);
        }
        lista.setAdapter(contenidoLista);
    }

    private void irADetalles (boolean consulta, String id){
        int indice = Integer.parseInt(id);
        Intent detalles = new Intent(Main3Activity.this, Main5Activity.class);
        detalles.putExtra("registro", !consulta);
        detalles.putExtra("id", indice);
        startActivity(detalles);
    }

    private void miniMensaje (String mensaje) {
        Toast.makeText(Main3Activity.this, mensaje, Toast.LENGTH_LONG).show();
    }

}
