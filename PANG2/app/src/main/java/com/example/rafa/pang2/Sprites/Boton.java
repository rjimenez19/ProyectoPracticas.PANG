package com.example.rafa.pang2.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Boton {

    private int x = 0, y = 0;
    private Bitmap bmp;

    public Boton(Bitmap bmp, int x, int y) {
        this.bmp = bmp;
        this.x = x;
        this.y = y;
    }

    public void onDraw(Canvas canvas) throws Throwable {
        canvas.drawBitmap(bmp, x, y, null);
    }

    public boolean hayColosion(float x2, float y2) {
        return x2 > x && x2 < x + bmp.getWidth() && y2 > y && y2 < y + bmp.getHeight();
    }
}
