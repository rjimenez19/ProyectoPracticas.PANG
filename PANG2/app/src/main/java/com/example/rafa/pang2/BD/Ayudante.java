package com.example.rafa.pang2.BD;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "puntuaciones.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1;
        sql1="create table " + Contrato.TablaPuntuaciones.TABLA
                + " (" + Contrato.TablaPuntuaciones._ID + " integer primary key autoincrement, "
                + Contrato.TablaPuntuaciones.NOMBREJUGADOR + " text, "
                + Contrato.TablaPuntuaciones.PUNTUACION + " integer)";
        Log.v("SQL1AAD", sql1);
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
        String sqlB1;
        sqlB1 = "DROP TABLE IF EXISTS " + Contrato.TablaPuntuaciones.TABLA ;
        db.execSQL(sqlB1);
        onCreate(db);
    }
}
