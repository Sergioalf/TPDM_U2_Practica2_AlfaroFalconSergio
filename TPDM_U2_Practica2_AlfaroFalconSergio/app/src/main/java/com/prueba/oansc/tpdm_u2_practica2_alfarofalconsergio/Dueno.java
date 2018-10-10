package com.prueba.oansc.tpdm_u2_practica2_alfarofalconsergio;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Dueno {

    String id;
    String nombre;
    String domicilio;
    String telefono;
    BaseDeDatos baseDatos;

    public Dueno (Activity ventana) {
        baseDatos = new BaseDeDatos(ventana, "segurocoche", null, 3);
    }

    public Dueno (String id, String nombre, String domicilio, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public Dueno[] obtenerDuenos () {
        Dueno[] duenos = null;
        try {
            SQLiteDatabase leible = baseDatos.getReadableDatabase();
            String columnas[] = {"ID", "NOMBRE", "DOMICILIO", "TELEFONO"};
            Cursor resultado = leible.query("DUENO", columnas, null, null, null, null, null, null);
            if (resultado.moveToFirst()) {
                duenos = new Dueno[resultado.getCount()];
                int i = 0;
                do {
                    duenos[i++] = new Dueno(resultado.getString(0), resultado.getString(1), resultado.getString(2), resultado.getString(3));
                } while (resultado.moveToNext());
            }
            leible.close();
        } catch (SQLiteException e) {
            return null;
        }
        return duenos;
    }

    public boolean insertar (Dueno dueno) {
        try {
            SQLiteDatabase escribible = baseDatos.getWritableDatabase();
            ContentValues datosDueno = new ContentValues();
            datosDueno.put("ID", dueno.id);
            datosDueno.put("NOMBRE", dueno.nombre);
            datosDueno.put("DOMICILIO", dueno.domicilio);
            datosDueno.put("TELEFONO", dueno.telefono);
            long funciono = escribible.insert("DUENO", null, datosDueno);
            escribible.close();
            if (funciono < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public Dueno consultar (String parametro, boolean telefono) {
        Dueno recuperado = null;
        try {
            SQLiteDatabase leible = baseDatos.getReadableDatabase();
            String[] columnas = {"ID", "NOMBRE", "DOMICILIO", "TELEFONO"};
            String[] parametros = {parametro};
            Cursor resultado;
            if (telefono){
                resultado = leible.query("DUENO", columnas, "TELEFONO = ?", parametros, null, null, null, null);
            } else {
                resultado = leible.query("DUENO", columnas, "ID = ?", parametros, null, null, null, null);
            }
            if (resultado.moveToFirst()) {
                recuperado = new Dueno(resultado.getString(0), resultado.getString(1), resultado.getString(2), resultado.getString(3));
            }
            leible.close();
        } catch (SQLiteException e) {
            return null;
        }
        return recuperado;
    }

    public boolean actualizar (String idOriginial, Dueno dueno) {
        try {
            SQLiteDatabase escribible = baseDatos.getReadableDatabase();
            ContentValues datosDueno = new ContentValues();
            datosDueno.put("ID", dueno.id);
            datosDueno.put("NOMBRE", dueno.nombre);
            datosDueno.put("DOMICILIO", dueno.domicilio);
            datosDueno.put("TELEFONO", dueno.telefono);
            String[] id = {idOriginial};
            long resultado = escribible.update("DUENO", datosDueno, "ID = ?", id);
            escribible.close();
            if (resultado < 0) {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public boolean eliminar (Dueno dueno) {
        try {
            SQLiteDatabase escribible = baseDatos.getWritableDatabase();
            String[] id = {dueno.id};
            long resultado = escribible.delete("DUENO", "ID = ?", id);
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
