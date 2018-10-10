package com.prueba.oansc.tpdm_u2_practica2_alfarofalconsergio;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.Date;

public class Poliza {

    int idPoliza;
    String modelo;
    String marca;
    int ano;
    String fechaInicio;
    float precio;
    String tipoPoliza;
    String idDueno;
    BaseDeDatos baseDatos;

    public Poliza (Activity ventana) {
        baseDatos = new BaseDeDatos(ventana, "segurocoche",null,3);
    }

    public Poliza(int idPoliza, String modelo, String marca, int ano, String fechaInicio, float precio, String tipoPoliza, String idDueno) {
        this.idPoliza = idPoliza;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.fechaInicio = fechaInicio;
        this.precio = precio;
        this.tipoPoliza = tipoPoliza;
        this.idDueno = idDueno;
    }

    public Poliza[] obtenerPolizas () {
        Poliza[] polizas = null;
        try {
            SQLiteDatabase leible = baseDatos.getReadableDatabase();
            String columnas[] = {"IDPOLIZA", "MODELO", "MARCA", "ANO", "FECHAINICIO", "PRECIO", "TIPOPOILIZA", "IDDUENO"};
            Cursor resultado = leible.query("POLIZA", columnas, null, null, null, null, null, null);
            if (resultado.moveToFirst()) {
                polizas = new Poliza[resultado.getCount()];
                int i = 0;
                do {
                    polizas[i++] = new Poliza(resultado.getInt(0), resultado.getString(1), resultado.getString(2), resultado.getInt(3), resultado.getString(4), resultado.getFloat(5), resultado.getString(6), resultado.getString(7));
                } while (resultado.moveToNext());
            }
            leible.close();
        } catch (SQLiteException e) {
            return null;
        }
        return polizas;
    }

    public boolean insertar (Poliza poliza) {
        try {
            SQLiteDatabase escribible = baseDatos.getWritableDatabase();
            ContentValues datosPoliza = new ContentValues();
            datosPoliza.put("MODELO", poliza.modelo);
            datosPoliza.put("MARCA", poliza.marca);
            datosPoliza.put("ANO", poliza.ano);
            datosPoliza.put("FECHAINICIO", poliza.fechaInicio);
            datosPoliza.put("PRECIO", poliza.precio);
            datosPoliza.put("TIPOPOILIZA", poliza.tipoPoliza);
            datosPoliza.put("IDDUENO", poliza.idDueno);
            long funciono = escribible.insert("POLIZA", "IDPOLIZA", datosPoliza);
            escribible.close();
            if (funciono < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public Poliza consultar (int id) {
        Poliza recuperada = null;
        try {
            SQLiteDatabase leible = baseDatos.getReadableDatabase();
            String[] columnas = {"IDPOLIZA", "MODELO", "MARCA", "ANO", "FECHAINICIO", "PRECIO", "TIPOPOILIZA", "IDDUENO"};
            String[] parametros = {id + ""};
            Cursor resultado = leible.query("POLIZA", columnas, "IDPOLIZA = ?", parametros, null, null, null, null);
            if (resultado.moveToFirst()) {
                recuperada = new Poliza(resultado.getInt(0), resultado.getString(1), resultado.getString(2), resultado.getInt(3), resultado.getString(4), resultado.getFloat(5), resultado.getString(6), resultado.getString(7));
            }
            leible.close();
        } catch (SQLiteException e) {
            return null;
        }
        return recuperada;
    }

    public boolean actualizar (Poliza poliza) {
        try {
            SQLiteDatabase escribible = baseDatos.getReadableDatabase();
            ContentValues datosPoliza = new ContentValues();
            datosPoliza.put("MODELO", poliza.modelo);
            datosPoliza.put("MARCA", poliza.marca);
            datosPoliza.put("ANO", poliza.ano);
            datosPoliza.put("FECHAINICIO", poliza.fechaInicio);
            datosPoliza.put("PRECIO", poliza.precio);
            datosPoliza.put("TIPOPOILIZA", poliza.tipoPoliza);
            datosPoliza.put("IDDUENO", poliza.idDueno);
            String[] id = {poliza.idPoliza + ""};
            long resultado = escribible.update("POLIZA", datosPoliza, "IDPOLIZA = ?", id);
            escribible.close();
            if (resultado < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public boolean eliminar (Poliza poliza) {
        try {
            SQLiteDatabase escribible = baseDatos.getWritableDatabase();
            String[] id = {poliza.idPoliza + ""};
            long resultado = escribible.delete("POLIZA", "IDPOLIZA = ?", id);
            escribible.close();
            if (resultado < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

}
