package com.example.rafa.pang2.BD;


import android.provider.BaseColumns;

public class Contrato {

    private Contrato() {
    }

    public static abstract class TablaPuntuaciones implements BaseColumns {
        public static final String TABLA = "puntuaciones";
        public static final String NOMBREJUGADOR = "nombre";
        public static final String PUNTUACION = "puntuacion";
    }
}
