package com.prueba.oansc.tpdm_u2_practica2_alfarofalconsergio;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button dueno;
    Button poliza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.Activity1);

        dueno = findViewById(R.id.dueno);
        poliza = findViewById(R.id.poliza);

        dueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentana (Main2Activity.class);
            }
        });
        poliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentana(Main3Activity.class);
            }
        });
    }

    private void abrirVentana (Class ventana) {
        Intent nuevaVentana = new Intent(MainActivity.this, ventana);
        startActivity(nuevaVentana);
    }

}
