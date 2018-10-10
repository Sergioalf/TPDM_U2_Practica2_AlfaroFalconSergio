package com.prueba.oansc.tpdm_u2_practica2_alfarofalconsergio;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {

    EditText modelo;
    EditText marca;
    EditText ano;
    EditText fechaInicio;
    EditText precio;
    EditText tipoPoliza;
    Spinner duenos;
    Button eliminar;
    Button actualizar;
    Button guardar;
    String[] idsDuenos;
    boolean actualizando;
    int idActual;
    int posicion;
    Poliza baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        this.setTitle(R.string.Activity5);

        baseDatos = new Poliza(this);
        obtenerID();
        modelo = findViewById(R.id.modelo);
        marca = findViewById(R.id.marca);
        ano = findViewById(R.id.ano);
        fechaInicio = findViewById(R.id.fechaInicio);
        precio = findViewById(R.id.precio);
        tipoPoliza = findViewById(R.id.tipoPoliza);
        duenos = findViewById(R.id.duenos);
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
                //if (camposValidos()) {
                    guardar();
                //}
            }
        });
        cambiarContenido(getIntent().getBooleanExtra("registro", true));
    }

    protected void onStart () {
        super.onStart();
        Dueno baseDueno = new Dueno (this);
        Dueno[] Aduenos = baseDueno.obtenerDuenos();
        String[] nombres = new String[Aduenos.length+1];
        idsDuenos = new String[nombres.length];
        idsDuenos[0] = "Selecciona uno";
        for (int i = 0; i < Aduenos.length; i++) {
            idsDuenos[i+1] = Aduenos[i].id;
        }
        ArrayAdapter<String> lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, idsDuenos);
        this.duenos.setAdapter(lista);
        if (idActual != 0) {
            Poliza p = baseDatos.consultar(idActual);
            String id = p.idDueno;
            for (int i = 0; i < duenos.getCount(); i++) {
                if (duenos.getItemAtPosition(i).toString().equals(id)){
                    posicion = i;
                }
            }
        }
        duenos.setSelection(posicion);
    }

    //----------------------------Metodos principales---------------------------//

    private void guardar() {
        final Poliza poliza = new Poliza(idActual, modelo.getText().toString(), marca.getText().toString(), Integer.parseInt(ano.getText().toString()), fechaInicio.getText().toString(), Float.parseFloat(precio.getText().toString()), tipoPoliza.getText().toString(), idsDuenos[duenos.getSelectedItemPosition()]);
        if (actualizando) {
            AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
            cuadro.setTitle("Atencion").setMessage("Seguro que deseas actualizar la póliza").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (baseDatos.actualizar(poliza)) {
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

            if (baseDatos.insertar(poliza)) {
                miniMensaje("Se insertó correctamente");
                limpiarCampos();
            } else {
                miniMensaje("Error al insertar");
            }
        }
    }

    private void actualizar() {
        cambiarContenido(true);
        actualizando = true;
    }

    private void eliminar () {
        AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
        cuadro.setTitle("Atención").setMessage("¿Seguro que deseas eliminar la póliza?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Poliza poliza = new Poliza(idActual, modelo.getText().toString(), marca.getText().toString(), Integer.parseInt(ano.getText().toString()), fechaInicio.getText().toString(), Float.parseFloat(precio.getText().toString()), tipoPoliza.getText().toString(), idsDuenos[duenos.getSelectedItemPosition()]);
                if (baseDatos.eliminar(poliza)) {
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

    //-----------------------------Métodos Auxiliares---------------------------//

    private boolean camposValidos () {
        if (modelo.getText().toString().equals("")){
            miniMensaje("Escribe el modelo");
            return false;
        }
        if (marca.getText().toString().equals("")) {
            miniMensaje("Escribe la marca");
            return false;
        }
        if (ano.getText().toString().equals("")) {
            miniMensaje("Escribe el año de tu coche");
            return false;
        }
        if (ano.getText().toString().length() != 4) {
            miniMensaje("escribe cuatro dígitos para la fecha");
            return false;
        }
        try {
            int a = Integer.parseInt(ano.getText().toString());
        } catch (NumberFormatException e) {
            miniMensaje("Escribe solo dígitos para el año");
            return false;
        }
        if (fechaInicio.getText().toString().equals("")) {
            miniMensaje("Escribe una fecha de inicio");
            return false;
        }
        if (fechaInicio.getText().toString().split("/").length != 3) {
            miniMensaje("Sigue el formato dd/mm/aaaa");
            return false;
        }
        if (precio.getText().toString().equals("")) {
            miniMensaje("Escribe el precio");
            return false;
        }
        try {
            float a = Float.parseFloat(precio.getText().toString());
        } catch (NumberFormatException e) {
            miniMensaje("Solo puedes escribir numeros y puntos en el precio");
            return false;
        }
        if (tipoPoliza.getText().toString().equals("")) {
            miniMensaje("Escribe el tipo de póilza");
            return false;
        }
        if (duenos.getSelectedItemPosition() <= 0) {
            miniMensaje("Selecciona un duenño");
            return false;
        }
        return true;
    }

    private void cambiarContenido (boolean cambio) {
        modelo.setEnabled(cambio);
        marca.setEnabled(cambio);
        ano.setEnabled(cambio);
        fechaInicio.setEnabled(cambio);
        precio.setEnabled(cambio);
        tipoPoliza.setEnabled(cambio);
        duenos.setEnabled(cambio);
        if (cambio) {
            eliminar.setVisibility(View.INVISIBLE);
            actualizar.setVisibility(View.INVISIBLE);
            guardar.setVisibility(View.VISIBLE);
        } else {
            eliminar.setVisibility(View.VISIBLE);
            actualizar.setVisibility(View.VISIBLE);
            guardar.setVisibility(View.INVISIBLE);
            mostrarPoliza();
        }
    }

    private void obtenerID () {
        idActual = getIntent().getIntExtra("id", 0);
    }

    private void mostrarPoliza () {
        Poliza poliza = baseDatos.consultar(idActual);
        modelo.setText(poliza.modelo);
        marca.setText(poliza.marca);
        ano.setText(poliza.ano+"");
        fechaInicio.setText(poliza.fechaInicio);
        precio.setText(poliza.precio+"");
        tipoPoliza.setText(poliza.tipoPoliza);
        String id = poliza.idDueno;
        for (int i = 0; i < duenos.getCount(); i++) {
            if (duenos.getItemAtPosition(i).toString().equals(id)){
                posicion = i;
            }
        }
        duenos.setSelection(posicion);
    }

    private void limpiarCampos () {
        modelo.setText("");
        marca.setText("");
        ano.setText("");
        fechaInicio.setText("");
        precio.setText("");
        tipoPoliza.setText("");
        duenos.setSelection(0);
    }

    private void miniMensaje (String mensaje) {
        Toast.makeText(Main5Activity.this, mensaje, Toast.LENGTH_LONG).show();
    }

}
