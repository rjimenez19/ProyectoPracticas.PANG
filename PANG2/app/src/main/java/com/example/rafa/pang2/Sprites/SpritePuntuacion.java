package com.example.rafa.pang2.Sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.rafa.pang2.Pantalla;

public class SpritePuntuacion {

    private int x = 0, y = 0;
    private String texto;
    private Paint paint;
    private Pantalla pantalla;

    public SpritePuntuacion(Pantalla pantalla, String texto, int x, int y) {
        this.x = x;
        this.y = y;
        this.texto = texto;
        this.pantalla = pantalla;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(pantalla.getWidth() / 30);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawText("Puntuacion: " + texto, x, y, paint);
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
