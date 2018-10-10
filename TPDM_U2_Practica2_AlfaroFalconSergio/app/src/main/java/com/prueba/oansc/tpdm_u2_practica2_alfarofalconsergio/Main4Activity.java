package com.prueba.oansc.tpdm_u2_practica2_alfarofalconsergio;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.spec.ECField;

public class Main4Activity extends AppCompatActivity {

    String idActual;
    EditText id;
    EditText nombre;
    EditText domicilio;
    EditText telefono;
    Button eliminar;
    Button actualizar;
    Button guardar;
    Dueno baseDatos;
    boolean actualizando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        this.setTitle(R.string.Activity4);

        baseDatos = new Dueno(this);
        obtenerId();
        id = findViewById(R.id.id);
        nombre = findViewById(R.id.nombre);
        domicilio = findViewById(R.id.domicilio);
        telefono = findViewById(R.id.telefono);
        eliminar = findViewById(R.id.eliminar);
        actualizar = findViewById(R.id.actualizar);
        guardar = findViewById(R.id.guardar);
        actualizando = false;

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposValidos()) {
                    guardar();
                }
            }
        });

        cambiarContenido(getIntent().getBooleanExtra("registro", true));
    }

    //--------------------Métodos principales--------------------------//

    private void guardar () {
        final Dueno dueno = new Dueno(id.getText().toString(), nombre.getText().toString(), domicilio.getText().toString(), telefono.getText().toString());
        if (actualizando) {
            AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
            cuadro.setTitle("Atención").setMessage("Seguro que deseas acualizar el dueño").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (baseDatos.actualizar(idActual, dueno)) {
                        idActual = dueno.id;
                        miniMensaje("Se actualizó correctamente");
                        actualizando = false;
                        cambiarContenido(false);
                    } else {
                        miniMensaje("Error al actualizar");
                    }
                    dialog.dismiss();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        } else {
            if (baseDatos.insertar(dueno)) {
                miniMensaje("Se insertó correctamente");
                limpiarCampos();
            } else {
                miniMensaje("Error al insertar");
            }
        }
    }

    private void actualizar () {
        cambiarContenido(true);
        actualizando = true;
    }

    private void eliminar () {
        AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
        cuadro.setTitle("Atención").setMessage("¿Seguro que deseas eliminar el dueño?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dueno dueno = new Dueno(id.getText().toString(), nombre.getText().toString(), domicilio.getText().toString(), telefono.getText().toString());
                if (baseDatos.eliminar(dueno)) {
                    miniMensaje("Se ha eliminado correctamente");
                    finish();
                } else {
                    miniMensaje("Error al eliminar");
                }
                dialog.dismiss();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    //--------------------Métodos de Apoyo-----------------------------//

    private boolean camposValidos () {
        if (id.getText().toString().equals("")) {
            miniMensaje("Escribe un id");
            return false;
        }
        if (nombre.getText().toString().equals("")) {
            miniMensaje("Escribe un nombre");
            return false;
        }
        if (domicilio.getText().toString().equals("")) {
            miniMensaje("Escribe un domicilio");
            return false;
        }
        if (telefono.getText().toString().equals("")) {
            miniMensaje("Escribe un telefono");
            return false;
        }
        return true;
    }

    private void obtenerId () {
        final String telefono = getIntent().getStringExtra("telefono");
        if (telefono.equals("")){
            idActual = "1";
        } else {
            Dueno actual = baseDatos.consultar(telefono, true);
            idActual = actual.id;
        }
    }

    private void cambiarContenido (boolean cambio) {
        id.setEnabled(cambio);
        nombre.setEnabled(cambio);
        domicilio.setEnabled(cambio);
        telefono.setEnabled(cambio);
        if (cambio) {
            eliminar.setVisibility(View.INVISIBLE);
            actualizar.setVisibility(View.INVISIBLE);
            guardar.setVisibility(View.VISIBLE);
        } else {
            eliminar.setVisibility(View.VISIBLE);
            actualizar.setVisibility(View.VISIBLE);
            guardar.setVisibility(View.INVISIBLE);
            mostrarDueno();
        }
    }

    private void mostrarDueno () {
        Dueno dueno = baseDatos.consultar(idActual, false);
        id.setText(dueno.id);
        nombre.setText(dueno.nombre);
        domicilio.setText(dueno.domicilio);
        telefono.setText(dueno.telefono);
    }

    private void limpiarCampos () {
        id.setText("");
        nombre.setText("");
        domicilio.setText("");
        telefono.setText("");
    }

    private void miniMensaje (String mensaje) {
        Toast.makeText(Main4Activity.this, mensaje, Toast.LENGTH_LONG).show();
    }

}
