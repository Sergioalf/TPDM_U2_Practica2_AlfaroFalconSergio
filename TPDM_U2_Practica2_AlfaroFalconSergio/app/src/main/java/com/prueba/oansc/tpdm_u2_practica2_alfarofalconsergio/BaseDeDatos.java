package com.prueba.oansc.tpdm_u2_practica2_alfarofalconsergio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {

    public BaseDeDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DUENO (ID INTEGER PRIMARY KEY NOT NULL, NOMBRE VARCHAR (500), DOMICILIO VARCHAR (500), TELEFONO VARCHAR(30))");
        db.execSQL("CREATE TABLE POLIZA (IDPOLIZA INTEGER PRIMARY KEY AUTOINCREMENT, MODELO VARCHAR (60), MARCA VARCHAR (200), ANO INTEGER, FECHAINICIO DATE, PRECIO FLOAT, TIPOPOILIZA VARCHAR (200), IDDUENO VARCHAR(20), FOREIGN KEY (IDDUENO) REFERENCES DUENO(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE POLIZA");
        db.execSQL("DROP TABLE DUENO");
        db.execSQL("CREATE TABLE DUENO (ID VARCHAR (20) PRIMARY KEY NOT NULL, NOMBRE VARCHAR (500), DOMICILIO VARCHAR (500), TELEFONO VARCHAR(30))");
        db.execSQL("CREATE TABLE POLIZA (IDPOLIZA INTEGER PRIMARY KEY AUTOINCREMENT, MODELO VARCHAR (60), MARCA VARCHAR (200), ANO INTEGER, FECHAINICIO DATE, PRECIO FLOAT, TIPOPOILIZA VARCHAR (200), IDDUENO VARCHAR(20), FOREIGN KEY (IDDUENO) REFERENCES DUENO(ID))");
    }
}
