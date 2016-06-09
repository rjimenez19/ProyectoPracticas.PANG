package com.example.rafa.pang2.BD;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GestorPuntuacion {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorPuntuacion(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public long insert(Puntuacion p){
        ContentValues valores= new ContentValues();
        valores.put(Contrato.TablaPuntuaciones.NOMBREJUGADOR, p.getNombre());
        valores.put(Contrato.TablaPuntuaciones.PUNTUACION, p.getPuntuacion());

        long id = bd.insert(Contrato.TablaPuntuaciones.TABLA,null,valores);
        return id;
    }

    public int delete(Puntuacion p){
        return delete(p.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaPuntuaciones._ID+"= ?";
        String[] argumentos = {id +"" };
        int cuenta = bd.delete(Contrato.TablaPuntuaciones.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Puntuacion p) {

        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPuntuaciones.NOMBREJUGADOR, p.getNombre());
        valores.put(Contrato.TablaPuntuaciones.PUNTUACION, p.getPuntuacion());
        String condicion = Contrato.TablaPuntuaciones._ID + " = ?";
        String[] argumentos = { p.getId() + "" };

        int cuenta = bd.update(Contrato.TablaPuntuaciones.TABLA, valores, condicion, argumentos);
        return cuenta;
    }

    public List<Puntuacion> select(){
        return select(null, null);
    }

    public List<Puntuacion> select(String condicion, String[] params) {
        List<Puntuacion> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, params);
        Puntuacion p;
        while (!cursor.isAfterLast()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public Puntuacion getRow(Cursor c) {
        Puntuacion p = new Puntuacion();
        Log.v("aadsql",""+c.getColumnCount());
        p.setId(c.getLong(c.getColumnIndex(Contrato.TablaPuntuaciones._ID)));
        p.setNombre(c.getString(c.getColumnIndex(Contrato.TablaPuntuaciones.NOMBREJUGADOR)));
        p.setPuntuacion(c.getInt(c.getColumnIndex(Contrato.TablaPuntuaciones.PUNTUACION)));
        return p;
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(Contrato.TablaPuntuaciones.TABLA, null, condicion, parametros, null, null, Contrato.TablaPuntuaciones.NOMBREJUGADOR + ", "+Contrato.TablaPuntuaciones._ID);
        return cursor;
    }
}
