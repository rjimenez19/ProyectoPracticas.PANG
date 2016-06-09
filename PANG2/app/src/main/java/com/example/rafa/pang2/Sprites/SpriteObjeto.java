package com.example.rafa.pang2.Sprites;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.rafa.pang2.Pantalla;

public class SpriteObjeto {

    private int ancho, alto;
    private int x = 50, y = 50;
    private Bitmap bmp;
    private int yVelocidad = 10;
    private int borde, borde2;
    private Pantalla pantalla;

    public SpriteObjeto(Pantalla pantalla, Bitmap bmp) {
        this.bmp = bmp;
        this.pantalla = pantalla;
        ancho = bmp.getWidth();
        alto = bmp.getHeight();
    }

    private void moverse() {
        borde2 = (pantalla.getHeight() / 30);

        //Abajo
        if (y > pantalla.getHeight() - ((pantalla.getHeight() / 26) * 7)) {
            yVelocidad = 0;
        }

        y = y + yVelocidad;
    }

    public void onDraw(Canvas canvas) {
        moverse();
        canvas.drawBitmap(bmp, x, y, null);
    }

    public boolean hayColision(SpritePj sp) {
        int w1,h1,w2,h2,x1,y1,x2,y2;

        w1 = ancho;		                // ancho del sprite1
        h1 = alto;		                // altura del sprite1
        w2 = sp.getAnchoBmpQuietoIz();	// ancho del sprite2
        h2 = sp.getAltoBmpQuietoIz();	// alto del sprite2
        x1 = x;		                    // pos. X del sprite1
        y1 = y;		                    // pos. Y del sprite1
        x2 = sp.getX();	                // pos. X del sprite2
        y2 = sp.getY();	                // pos. Y del sprite2

        if (((x1 + w1) > x2) && ((y1 + h1) > y2) && ((x2 + w2) > x1) && ((y2 + h2) > y1)) {
            return true;
        } else {
            return false;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
