package com.example.rafa.pang2.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.rafa.pang2.Pantalla;

public class SpriteDisparoGancho {

    private int ancho, alto;
    private int x = 0, y = 0, y2;
    private Bitmap bmp;
    private int currentFrame;
    private int yVelocidad = 20;
    private int incremento;
    private int incremento2;

    public SpriteDisparoGancho(Pantalla pantalla, Bitmap bmp, int x, int y) {
        this.bmp = bmp;
        this.x = x;
        this.y = y;
        this.y2 = y;
        this.bmp = bmp;

        ancho = bmp.getWidth() / 2;
        alto = bmp.getHeight();
    }

    private void moverse() {
        y = y - yVelocidad;
        currentFrame = ++currentFrame % 2;
        incremento = incremento + 19;
        incremento2 = incremento2 + 14;
    }

    public void onDraw(Canvas canvas) {
        moverse();
        int srcX = currentFrame * ancho;
        Rect src = new Rect(srcX, 1, srcX + ancho, y2 + incremento);
        Rect dst = new Rect(x, y, x + ancho, y2 + incremento2);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean hayColision(SpriteBola sp) {
        int w1,h1,w2,h2,x1,y1,x2,y2;

        w1 = ancho;		    // ancho del sprite1
        h1 = alto;		    // altura del sprite1
        w2 = sp.getAncho();	// ancho del sprite2
        h2 = sp.getAlto();	// alto del sprite2
        x1 = x;		        // pos. X del sprite1
        y1 = y;		        // pos. Y del sprite1
        x2 = sp.getX();	    // pos. X del sprite2
        y2 = sp.getY();	    // pos. Y del sprite2

        if (((x1 + w1) > x2) && ((y1 + h1) > y2) && ((x2 + w2) > x1) && ((y2 + h2) > y1)) {
            return true;
        } else {
            return false;
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
