package com.example.rafa.pang2.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.rafa.pang2.Pantalla;

public class SpriteBola {

    private int ancho, alto;
    private int x = 0, y = 0;
    private Bitmap bmp;
    private int xVelocidad = 10;
    private double parametroGrande1 = 809, parametroGrande2 = 957.19;
    private double parametroMediano1, parametroMediano2;
    private double parametroPequeño1, parametroPequeño2;
    private boolean direccion = false;
    private int borde, borde2;
    private Pantalla pantalla;
    private int random;
    private boolean grande, mediana, pequeña;
    private int h = 1;
    private int f = 1;

    public SpriteBola(Pantalla pantalla, Bitmap bmp, int x, int xVelocidad, boolean grande, boolean mediana) {
        this.bmp = bmp;
        this.x = x;
        this.bmp = bmp;
        this.pantalla = pantalla;
        this.grande = grande;
        this.mediana = mediana;
        this.xVelocidad = this.xVelocidad * xVelocidad;
        ancho = bmp.getWidth();
        alto = bmp.getHeight();
        borde = (pantalla.getWidth() / 67);
        borde2 = (pantalla.getHeight() / 20);
    }

    private void moverse() {

        if (xVelocidad > 0) {
            direccion = true;
        } else {
            direccion = false;
        }

        //Derecha
        if (x > pantalla.getWidth() - ancho - xVelocidad - borde) {
            xVelocidad = -xVelocidad;
        }
        //Izquierda
        if (x + xVelocidad < borde) {
            xVelocidad = -xVelocidad;
        }
        //Abajo
        if (y > pantalla.getHeight() - ((pantalla.getHeight() / 25) * 5) - alto - borde2) {
            if (mediana) {
                grande = false;
            }
            if (grande) { //Bola grande
                if (direccion) {
                    parametroGrande1 = parametroGrande1 + 680;
                    parametroGrande2 = parametroGrande2 + 680;
                    h = h + 1;
                } else if (!direccion) {
                    parametroGrande1 = parametroGrande1 - 680;
                    parametroGrande2 = parametroGrande2 - 680;
                    h = h - 1;
                }
            }
            if (mediana && !grande) { //Bola mediana
                if (h == 0) {
                    parametroMediano1 = 333.5;
                    parametroMediano2 = 730.59;
                    h = 3;
                } else if (h == 1 && direccion) {
                    parametroMediano1 = 1013.5;
                    parametroMediano2 = 1410.59;
                    h = 3;
                } else if (h == 1 && !direccion) {
                    parametroMediano1 = 703.5;
                    parametroMediano2 = 1100.59;
                    h = 3;
                } else if (h == 2) {
                    parametroMediano1 = 1383.5;
                    parametroMediano2 = 1780.59;
                    h = 3;
                }

                if (direccion) {
                    parametroMediano1 = parametroMediano1 + 370;
                    parametroMediano2 = parametroMediano2 + 370;
                } else if (!direccion) {
                    parametroMediano1 = parametroMediano1 - 370;
                    parametroMediano2 = parametroMediano2 - 370;
                }
            }
            if(pequeña && !mediana) { //Bola pequeña
                if (direccion) {
                    parametroPequeño1 = parametroPequeño1 + 370;
                    parametroPequeño2 = parametroPequeño2 + 370;
                } else if(!direccion){
                    parametroPequeño1 = parametroPequeño1 - 370;
                    parametroPequeño2 = parametroPequeño2 - 370;
                }
            }
            System.out.println("greande" + grande);
            System.out.println("mediana:" + mediana);
            System.out.println("pequeña:" + pequeña);
        }

        x = x + xVelocidad;
        if (grande) {
            y = (int)-((-0.005*(Math.pow((x - parametroGrande1),2)) + x - parametroGrande2));
        } else if (mediana && !grande) {
            y = (int)-((-0.01*(Math.pow((x - parametroMediano1),2)) + x - parametroMediano2));
        } else if (pequeña && !mediana){
            y = (int)-((-0.01*(Math.pow((x - parametroPequeño1),2)) + x - parametroPequeño2));
        }
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

    public int drop() {
        random = (int)(Math.random() * 5 + 1);
        if(random == 1) {
            return 1;
        }else if(random == 2) {
            return 2;
        }else {
            return 0;
        }
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setMediana(boolean mediana) {
        this.mediana = mediana;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getParametroGrande1() {
        return parametroGrande1;
    }

    public void setParametroGrande1(double parametro1) {
        this.parametroGrande1 = parametro1;
    }

    public double getParametroGrande2() {
        return parametroGrande2;
    }

    public void setParametroGrande2(double parametro2) {
        this.parametroGrande2 = parametro2;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setPequeña(boolean pequeña) {
        this.pequeña = pequeña;
    }

    public void setParametroPequeño1(double parametroPequeño1) {
        this.parametroPequeño1 = parametroPequeño1;
    }

    public void setParametroPequeño2(double parametroPequeño2) {
        this.parametroPequeño2 = parametroPequeño2;
    }

    public double getParametroMediano1() {
        return parametroMediano1;
    }

    public double getParametroMediano2() {
        return parametroMediano2;
    }

    public void setParametroMediano1(double parametroMediano1) {
        this.parametroMediano1 = parametroMediano1;
    }

    public void setParametroMediano2(double parametroMediano2) {
        this.parametroMediano2 = parametroMediano2;
    }

    public double getParametroPequeño1() {
        return parametroPequeño1;
    }

    public double getParametroPequeño2() {
        return parametroPequeño2;
    }

    public boolean getDireccion() {
        return direccion;
    }
}
