package com.example.rafa.pang2.Sprites;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.rafa.pang2.Pantalla;

public class SpritePj {

    private int anchoBmpQuietoIz, anchoBmpQuietoDe, anchoBmpIzquierda, anchoBmpDerecha, anchoBmpArriba, anchoBmpAbajo, anchoBmpDispararIzquierda, anchoBmpDispararDerecha, anchoBmpMorirIzquierda, anchoBmpMorirDerecha;
    private int altoBmpQuietoIz, altoBmpQuietoDe, altoBmpIzquierda, altoBmpDerecha, altoBmpArriba, altoBmpAbajo, altoBmpDispararIzquierda, altoBmpDispararDerecha, altoBmpMorirIzquierda, altoBmpMorirDerecha;
    private Bitmap bmpQuietoIz, bmpQuietoDe, bmpIzquierda, bmpDerecha, bmpArriba, bmpAbajo, bmpDispararIzquierda, bmpDispararDerecha, bmpMorir;
    private int x = 0, y = 0;
    private int frameActual = 0;
    private int control = 0;
    private Pantalla pantalla;
    private int xVelocidad = 0;
    private int currentFrame = 0;
    private int currentFrame2 = 0;
    private int borde;


    //Rect(int left, int top, int right, int bottom)

    public SpritePj(Pantalla pantalla, int x, int y, Bitmap bmp0, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5, Bitmap bmp6, Bitmap bmp7, Bitmap bmp9) {
        this.pantalla = pantalla;
        this.x = x;
        this.y = y;

        //Quieto izquierda
        bmpQuietoIz = bmp0;
        anchoBmpQuietoIz = bmpQuietoIz.getWidth();
        altoBmpQuietoIz = bmpQuietoIz.getHeight();

        //Quieto derecha
        bmpQuietoDe = bmp1;
        anchoBmpQuietoDe = bmpQuietoDe.getWidth();
        altoBmpQuietoDe = bmpQuietoDe.getHeight();

        //Izquierda
        bmpIzquierda = bmp2;
        anchoBmpIzquierda = bmpIzquierda.getWidth() / 5;
        altoBmpIzquierda = bmpIzquierda.getHeight();

        //Derecha
        bmpDerecha = bmp3;
        anchoBmpDerecha = bmpDerecha.getWidth() / 5;
        altoBmpDerecha = bmpDerecha.getHeight();

        //Arriba
        bmpArriba = bmp4;
        anchoBmpArriba = bmpArriba.getWidth() / 3;
        altoBmpArriba = bmpArriba.getHeight();

        //Abajo
        bmpAbajo = bmp5;
        anchoBmpAbajo = bmpAbajo.getWidth() / 3;
        altoBmpAbajo = bmpAbajo.getHeight();

        //Disparar izquierda
        bmpDispararIzquierda = bmp6;
        anchoBmpDispararIzquierda = bmpDispararIzquierda.getWidth() / 2;
        altoBmpDispararIzquierda = bmpDispararIzquierda.getHeight();

        //Disparar derecha
        bmpDispararDerecha = bmp7;
        anchoBmpDispararDerecha = bmpDispararDerecha.getWidth() / 2;
        altoBmpDispararDerecha = bmpDispararDerecha.getHeight();

        //Morir derecha
        bmpMorir = bmp9;
        anchoBmpMorirDerecha = bmpMorir.getWidth();
        altoBmpMorirDerecha = bmpMorir.getHeight();

    }

    private void moverse() {

        borde = (pantalla.getWidth() / 67);
        if (x > pantalla.getWidth() - anchoBmpIzquierda - xVelocidad - borde) {
            xVelocidad = 0;
        }
        if (x + xVelocidad < borde ) {
            xVelocidad = 0;
        }
        x = x + xVelocidad;
        currentFrame = ++currentFrame % 5;
        currentFrame2 = ++currentFrame2 % 2;
    }

    public void onDraw(Canvas canvas) {
        moverse();

        if (control == 0){ //Quieto izquierda
            canvas.drawBitmap(bmpQuietoIz, x, y, null);

        }else if (control == 1){ //Quieto derecha
            canvas.drawBitmap(bmpQuietoDe, x, y, null);

        }else if (control == 2){ //Izquierda
            int srcX = currentFrame * anchoBmpIzquierda;
            int srcY = altoBmpIzquierda;
            Rect src = new Rect(srcX, 1, srcX + anchoBmpIzquierda, altoBmpIzquierda);
            Rect dst = new Rect(x, y, x + anchoBmpIzquierda, y + altoBmpIzquierda);
            canvas.drawBitmap(bmpIzquierda, src, dst, null);

        }else if (control == 3){ // Derecha
            int srcX = currentFrame * anchoBmpDerecha;
            int srcY = altoBmpDerecha;
            Rect src = new Rect(srcX, 1, srcX + anchoBmpIzquierda, altoBmpDerecha);
            Rect dst = new Rect(x, y, x + anchoBmpDerecha, y + altoBmpDerecha);
            canvas.drawBitmap(bmpDerecha, src, dst, null);

        }else if (control == 6){ //Disparar Izquierda
            int srcX = currentFrame2 * anchoBmpDispararIzquierda;
            int srcY = altoBmpDispararIzquierda;
            Rect src = new Rect(srcX, 1, srcX + anchoBmpDispararIzquierda, altoBmpDispararIzquierda);
            Rect dst = new Rect(x, y, x + anchoBmpDispararIzquierda, y + altoBmpDispararIzquierda);
            canvas.drawBitmap(bmpDispararIzquierda , src, dst, null);

        }else if (control == 7){ //Disparar Derecha
            int srcX = currentFrame2 * anchoBmpDispararDerecha;
            int srcY = altoBmpDispararDerecha;
            Rect src = new Rect(srcX, 1, srcX + anchoBmpDispararDerecha, altoBmpDispararDerecha);
            Rect dst = new Rect(x, y, x + anchoBmpDispararDerecha, y + altoBmpDispararDerecha);
            canvas.drawBitmap(bmpDispararDerecha, src, dst, null);

        }else if (control == 8){ //Morir
            canvas.drawBitmap(bmpMorir, x, y, null);
        }
    }

    public void quieto() {
        if(control == 2 || control == 6){
            control = 0;
        }else if(control == 3 || control == 7){
            control = 1;
        }
        xVelocidad = 0;
    }

    public void moverIzquierda() {
        control = 2;
        xVelocidad = - 10;
    }

    public void moverDerecha() {
        control = 3;
        xVelocidad = 10;
    }

    public void disparar() {
        if (control == 0 || control == 2){
            control = 6;
        }else if (control == 1 || control == 3){
            control = 7;
        }
    }

    public void morir() {
        control = 8;
    }

    public boolean hayColosion(float x2, float y2) {

        if (control == 0){
            return x2 > x && x2 < x + bmpQuietoIz.getWidth() && y2 > y && y2 < y + bmpQuietoIz.getHeight();
        }else if (control == 1){
            return x2 > x && x2 < x + bmpQuietoDe.getWidth() && y2 > y && y2 < y + bmpQuietoDe.getHeight();
        }else if (control == 2){
            return x2 > x && x2 < x + bmpIzquierda.getWidth() && y2 > y && y2 < y + bmpIzquierda.getHeight();
        }else if (control == 3){
            return x2 > x && x2 < x + bmpDerecha.getWidth() && y2 > y && y2 < y + bmpDerecha.getHeight();
        }else if (control == 4){
            return x2 > x && x2 < x + bmpArriba.getWidth() && y2 > y && y2 < y + bmpArriba.getHeight();
        }else if (control == 5){
            return x2 > x && x2 < x + bmpAbajo.getWidth() && y2 > y && y2 < y + bmpAbajo.getHeight();
        }else if (control == 6){
            return x2 > x && x2 < x + bmpDispararIzquierda.getWidth() && y2 > y && y2 < y + bmpDispararIzquierda.getHeight();
        }else if (control == 7){
            return x2 > x && x2 < x + bmpDispararDerecha.getWidth() && y2 > y && y2 < y + bmpDispararDerecha.getHeight();
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAnchoBmpQuietoIz() {
        return anchoBmpQuietoIz;
    }

    public int getAltoBmpQuietoIz() {
        return altoBmpQuietoIz;
    }
}
