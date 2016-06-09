package com.example.rafa.pang2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.rafa.pang2.Sprites.Boton;
import com.example.rafa.pang2.Sprites.SpritePuntuacion;
import com.example.rafa.pang2.Sprites.SpriteBola;
import com.example.rafa.pang2.Sprites.SpriteDisparoGancho;
import com.example.rafa.pang2.Sprites.SpriteDisparoPistola;
import com.example.rafa.pang2.Sprites.SpriteObjeto;
import com.example.rafa.pang2.Sprites.SpritePj;
import com.example.rafa.pang2.Util.Reloj;

import java.util.Timer;
import java.util.TimerTask;

public class Pantalla extends SurfaceView {

    private Context contexto;
    private Motor motor;
    private Pantalla pantalla;
    private Juego juego;
    private Reloj tempo;

    private SurfaceHolder surface;
    private Handler handler;

    private SoundPool sonido, sonido2;

    private SpritePj personaje;
    private SpriteDisparoGancho disparoGancho, disparoGancho2;
    private SpriteDisparoPistola disparoPistola, disparoPistola2;
    private SpriteObjeto objetoPistola, objetoGanchoDoble;
    private Boton botonDisparo;
    private SpritePuntuacion puntuacion;

    private SpriteBola bolaGrande1;
    private SpriteBola bolaMediana1, bolaMediana2;
    private SpriteBola bolaPequeña1, bolaPequeña2, bolaPequeña3, bolaPequeña4;

    private Bitmap bmpQuietoIz, bmpQuietoDe, bmpIzquierda, bmpDerecha, bmpArriba, bmpAbajo, bmpDispararIzquierda, bmpDispararDerecha, bmpMorirDerecha;
    private Bitmap bmpDisparoGancho, bmpDisparoPistola;
    private Bitmap bmpObjetoPistola, bmpObjetoGanchoDoble;
    private Bitmap bmpMapa1;
    private Bitmap bmpBotonDisparo;
    private Bitmap bmpBolaGrande, bmpBolaMediana, bmpBolaPequeña;

    private boolean bolaGrande1Up = true, bolaGrande2Up = true;
    private boolean bolaMediana1Up = false, bolaMediana2Up = false;
    private boolean bolaPequeña1Up = false, bolaPequeña2Up = false, bolaPequeña3Up = false, bolaPequeña4Up;

    private boolean gameover = false;
    private boolean disparar = false;
    private boolean dispararGancho = true;
    private boolean dispararPistola = false;
    private boolean dispararGanchoDoble = false;
    private boolean objetoPistolaUp = false, objetoGanchoDobleUp = false;
    private boolean disparo1 = false, disparo2 = false;

    private int sonidoGancho, sonidoGancho2, sonidoPop, sonidoPistola;
    private int reloj = 0, reloj2 = 0;
    private long lastClick;
    private int drop, score = 0;

    private static final long SPLASH_SCREEN_DELAY = 200;

    //Constructores------------------------------------------------------------------------------------
    public Pantalla(Context context) {
        super(context);
        contexto = context;
        constructor();
    }

    public Pantalla(Context context, AttributeSet attrs) {
        super(context, attrs);
        contexto = context;
        constructor();
    }

    public Pantalla(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        contexto = context;
        constructor();
    }

    //Constructor comun--------------------------------------------------------------------------------
    public void constructor() {

        motor = new Motor(this);
        pantalla = this;
        gameover = false;

        //BMP-Disparos
        bmpDisparoGancho = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.disparonormal);
        bmpDisparoPistola = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.disparopistola);

        //BMP-Objetos
        bmpObjetoPistola = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.pistola);
        bmpObjetoGanchoDoble = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.ganchodoble);

        //BMP-Personaje
        bmpQuietoIz = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.quietoizquierda);
        bmpQuietoDe = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.quietoderecha);
        bmpIzquierda = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.personajeizquierda);
        bmpDerecha = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.personajederecha);
        bmpArriba = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.subir);
        bmpAbajo = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.subir);
        bmpDispararIzquierda = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.dispararizquierda);
        bmpDispararDerecha = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.dispararderecha);
        bmpMorirDerecha = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.morirderecha);

        //BMP-Bola
        bmpBolaGrande = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.bolagrande);
        bmpBolaMediana = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.bolamediana);
        bmpBolaPequeña = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.bolapequenia);

        //BMP-Mapa
        bmpMapa1 = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.mapa1);

        //BMP-Botones
        bmpBotonDisparo = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.botondisparar);

        surface = getHolder();
        surface.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder surface) {
                motor.setRunning(false);
                motor.interrumpirJuego();
            }

            private void Pause() {
                motor.setRunning(false);
                motor.interrumpirJuego();
            }

            @Override
            public void surfaceCreated(SurfaceHolder surface) {
                motor.setRunning(true);
                motor.start();

                //Personaje
                personaje = new SpritePj(pantalla, getWidth() - ((getWidth() / 10) * 5), getHeight() - ((getHeight() / 25) * 8), bmpQuietoIz, bmpQuietoDe, bmpIzquierda, bmpDerecha, bmpArriba, bmpAbajo, bmpDispararIzquierda,bmpDispararDerecha, bmpMorirDerecha);
                juego.personaje(personaje);

                //Bolas
                bolaGrande1 = new SpriteBola(pantalla, bmpBolaGrande, 640, 1, true, false);
                bolaMediana1 = new SpriteBola(pantalla, bmpBolaMediana, 640, 1, true, false);
                bolaMediana2 = new SpriteBola(pantalla, bmpBolaMediana, 300, -1, true, false);
                bolaPequeña1 = new SpriteBola(pantalla, bmpBolaPequeña, 300, 1, false, true);
                bolaPequeña2 = new SpriteBola(pantalla, bmpBolaPequeña, 300, -1, false, true);
                bolaPequeña3 = new SpriteBola(pantalla, bmpBolaPequeña, 300, 1, false, true);
                bolaPequeña4 = new SpriteBola(pantalla, bmpBolaPequeña, 300, -1, false, true);

                //Boton disparar
                botonDisparo = new Boton(bmpBotonDisparo, getWidth() - ((getWidth() / 10) * 3), getHeight() - (getHeight() / 25) * 5);

                //Objetos
                objetoPistola = new SpriteObjeto(pantalla, bmpObjetoPistola);
                objetoGanchoDoble = new SpriteObjeto(pantalla, bmpObjetoGanchoDoble);

                //Sonidos
                sonido = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
                sonidoGancho = sonido.load(getContext(), R.raw.disparo, 0);
                sonidoPop = sonido.load(getContext(), R.raw.bolapop, 0);
                sonidoPistola = sonido.load(getContext(), R.raw.shoot, 0);

                sonido2 = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
                sonidoGancho2 = sonido2.load(getContext(), R.raw.disparo, 0);

                //SpritePuntuacion
                puntuacion = new SpritePuntuacion(pantalla, String.valueOf(score), getWidth() - ((getWidth() / 30) * 29), getHeight() - ((getHeight() / 25)) * 23);

                //Reloj
                tempo = new Reloj(reloj);
                tempo.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder surface, int format, int width, int height) {
            }
        });
    }

    //OnDraw-------------------------------------------------------------------------------------------
    @SuppressLint("WrongCall")
    protected void onDraw(Canvas canvas) {

        if(!gameover) {
            if (canvas != null) {

                //Reloj
                reloj2 = reloj2 + 1;
                if(reloj2 == 20) {
                    reloj = reloj + 1;
                    reloj2 = 0;
                }

                //Fondo
                canvas.drawBitmap(bmpMapa1, null, new Rect(0, 0, this.getWidth(), this.getHeight() - ((this.getHeight() / 10) * 2)), null);

                //SpritePuntuacion
                puntuacion.onDraw(canvas);

                //Bolas
                if (bolaGrande1Up) {
                    bolaGrande1.onDraw(canvas);
                }
                if (bolaMediana1Up) {
                    bolaMediana1.onDraw(canvas);
                }
                if (bolaMediana2Up) {
                    bolaMediana2.onDraw(canvas);
                }
                if (bolaPequeña1Up) {
                    bolaPequeña1.onDraw(canvas);
                }
                if (bolaPequeña2Up) {
                    bolaPequeña2.onDraw(canvas);
                }
                if (bolaPequeña3Up) {
                    bolaPequeña3.onDraw(canvas);
                }
                if (bolaPequeña4Up) {
                    bolaPequeña4.onDraw(canvas);
                }

                //Boton disparo
                try {
                    botonDisparo.onDraw(canvas);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

                //Personaje
                if (personaje != null) {
                    personaje.onDraw(canvas);
                }

                //Disparo-techo
                if (dispararGancho) {
                    if (disparar) {
                        disparoGancho.onDraw(canvas);
                        sonido.play(sonidoGancho, 1, 1, 1, 0, 1);
                        sonido.autoResume();
                        if (disparoGancho.getY() < this.getHeight() / 20) {
                            disparar = false;
                        }
                    } else {
                        sonido.autoPause();
                    }
                }
                if (dispararPistola) {
                    if (disparo1) {
                        disparoPistola.onDraw(canvas);
                        sonido(sonidoPistola);
                        if (disparoPistola.getY() < this.getHeight() / 20) { //Disparo1
                            disparo1 = false;
                        }
                    }
                    if (disparo2) {
                        disparoPistola2.onDraw(canvas);
                        sonido(sonidoPistola);
                        if (disparoPistola2.getY() < this.getHeight() / 20) { //Disparo2
                            disparo2 = false;
                        }
                    }
                }
                if (dispararGanchoDoble) {
                    if (disparo1) {
                        disparoGancho.onDraw(canvas);
                        sonido.play(sonidoGancho, 1, 1, 1, 0, 1);
                        sonido.autoResume();
                        if (disparoGancho.getY() < this.getHeight() / 20) { //Disparo1
                            disparo1 = false;
                        }
                    } else if (!disparo1) {
                        sonido.autoPause();
                    }
                    if (disparo2) {
                        disparoGancho2.onDraw(canvas);
                        sonido2.play(sonidoGancho2, 1, 1, 1, 0, 1);
                        sonido.autoResume();
                        if (disparoGancho2.getY() < this.getHeight() / 20) { //Disparo2
                            disparo2 = false;
                        }
                    } else if (!disparo2) {
                        sonido2.autoPause();
                    }
                }

                //Colisiones-------------------------------------------------------------------------------

                //Disparo-bola
                //Bolas Grandes -------------------------------------
                if (bolaGrande1Up) { //BolaGrande1 ------------------
                    if (dispararGancho) { //DisparoGancho
                        if (disparar) {
                            if (disparoGancho.hayColision(bolaGrande1)) {
                                sonido(sonidoPop);
                                bolaGrande1Up = false;
                                disparar = false;
                                bolaMediana1Up = true;
                                bolaMediana2Up = true;
                                configurarBola(bolaGrande1, bolaMediana1, bolaMediana2);
                                bolaMediana1.setMediana(true);
                                bolaMediana2.setMediana(true);
                                aunmentarPuntuacion(500);
                            }
                        }
                    }
                    if (dispararPistola) { //DisparoPistola
                        if (disparo1) {
                            if (disparoPistola.hayColision(bolaGrande1)) { //Disparo1
                                sonido(sonidoPop);
                                configurarBola(bolaGrande1, bolaMediana1, bolaMediana2);
                                bolaMediana1Up = true;
                                bolaMediana2Up = true;
                                bolaMediana1.setMediana(true);
                                bolaMediana2.setMediana(true);
                                aunmentarPuntuacion(500);
                                bolaGrande1Up = false;
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoPistola2.hayColision(bolaGrande1)) { //Disparo2
                                sonido(sonidoPop);
                                configurarBola(bolaGrande1, bolaMediana1, bolaMediana2);
                                bolaMediana1Up = true;
                                bolaMediana2Up = true;
                                bolaMediana1.setMediana(true);
                                bolaMediana2.setMediana(true);
                                aunmentarPuntuacion(500);
                                bolaGrande1Up = false;
                                disparo2 = false;
                            }
                        }
                    }
                    if (dispararGanchoDoble) { //DisparoGanchoDoble
                        if (disparo1) {
                            if (disparoGancho.hayColision(bolaGrande1)) { //Disparo1
                                sonido(sonidoPop);
                                configurarBola(bolaGrande1, bolaMediana1, bolaMediana2);
                                bolaMediana1Up = true;
                                bolaMediana2Up = true;
                                bolaMediana1.setMediana(true);
                                bolaMediana2.setMediana(true);
                                aunmentarPuntuacion(500);
                                bolaGrande1Up = false;
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoGancho2.hayColision(bolaGrande1)) { //Disparo2
                                sonido(sonidoPop);
                                configurarBola(bolaGrande1, bolaMediana1, bolaMediana2);
                                bolaMediana1Up = true;
                                bolaMediana2Up = true;
                                bolaMediana1.setMediana(true);
                                bolaMediana2.setMediana(true);
                                aunmentarPuntuacion(500);
                                bolaGrande1Up = false;
                                disparo2 = false;
                            }
                        }
                    }
                }

                //Bolas Medianas --------------------------------------
                if (bolaMediana1Up) { //BolaMediana1 ------------------
                    if (dispararGancho) { //DisparoGancho
                        if (disparar) {
                            if (disparoGancho.hayColision(bolaMediana1)) {
                                sonido(sonidoPop);
                                bolaMediana1Up = false;
                                disparar = false;
                                bolaPequeña1Up = true;
                                bolaPequeña2Up = true;
                                bolaPequeña1.setPequeña(true);
                                bolaPequeña2.setPequeña(true);
                                configurarBola2(bolaMediana1, bolaPequeña1, bolaPequeña2);
                                aunmentarPuntuacion(1000);
                            }
                        }
                    }
                    if (dispararPistola) { //DisparoPistola
                        if (disparo1) {
                            if (disparoPistola.hayColision(bolaMediana1)) { //Disparo1
                                sonido(sonidoPop);
                                bolaMediana1Up = false;
                                configurarBola2(bolaMediana1, bolaPequeña1, bolaPequeña2);
                                bolaPequeña1Up = true;
                                bolaPequeña2Up = true;
                                bolaPequeña1.setPequeña(true);
                                bolaPequeña2.setPequeña(true);
                                aunmentarPuntuacion(1000);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoPistola2.hayColision(bolaMediana1)) { //Disparo2
                                sonido(sonidoPop);
                                bolaMediana1Up = false;
                                configurarBola2(bolaMediana1, bolaPequeña1, bolaPequeña2);
                                bolaPequeña1Up = true;
                                bolaPequeña2Up = true;
                                bolaPequeña1.setPequeña(true);
                                bolaPequeña2.setPequeña(true);
                                aunmentarPuntuacion(1000);
                                disparo2 = false;
                            }
                        }
                    }
                    if (dispararGanchoDoble) { //DisparoGanchoDoble
                        if (disparo1) {
                            if (disparoGancho.hayColision(bolaMediana1)) { //Disparo1
                                sonido(sonidoPop);
                                bolaMediana1Up = false;
                                configurarBola2(bolaMediana1, bolaPequeña1, bolaPequeña2);
                                bolaPequeña1Up = true;
                                bolaPequeña2Up = true;
                                bolaPequeña1.setPequeña(true);
                                bolaPequeña2.setPequeña(true);
                                aunmentarPuntuacion(1000);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoGancho2.hayColision(bolaMediana1)) { //Disparo2
                                sonido(sonidoPop);
                                bolaMediana1Up = false;
                                configurarBola2(bolaMediana1, bolaPequeña1, bolaPequeña2);
                                bolaPequeña1Up = true;
                                bolaPequeña2Up = true;
                                bolaPequeña1.setPequeña(true);
                                bolaPequeña2.setPequeña(true);
                                aunmentarPuntuacion(1000);
                                disparo2 = false;
                            }
                        }
                    }
                }

                if (bolaMediana2Up) { //BolaMediana2 ------------------
                    if (dispararGancho) { //DisparoGancho
                        if (disparar) {
                            if (disparoGancho.hayColision(bolaMediana2)) {
                                sonido(sonidoPop);
                                bolaPequeña3Up = true;
                                bolaPequeña4Up = true;
                                bolaPequeña3.setPequeña(true);
                                bolaPequeña4.setPequeña(true);
                                bolaMediana2Up = false;
                                disparar = false;
                                configurarBola2(bolaMediana2, bolaPequeña3, bolaPequeña4);
                                aunmentarPuntuacion(1000);
                            }
                        }
                    }
                    if (dispararPistola) { //DisparoPistola
                        if (disparo1) {
                            if (disparoPistola.hayColision(bolaMediana2)) { //Disparo1
                                sonido(sonidoPop);
                                bolaPequeña3Up = true;
                                bolaPequeña4Up = true;
                                bolaPequeña3.setPequeña(true);
                                bolaPequeña4.setPequeña(true);
                                bolaMediana2Up = false;
                                configurarBola2(bolaMediana2, bolaPequeña3, bolaPequeña4);
                                aunmentarPuntuacion(1000);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoPistola2.hayColision(bolaMediana2)) { //Disparo2
                                sonido(sonidoPop);
                                bolaPequeña3Up = true;
                                bolaPequeña4Up = true;
                                bolaPequeña3.setPequeña(true);
                                bolaPequeña4.setPequeña(true);
                                bolaMediana2Up = false;
                                configurarBola2(bolaMediana2, bolaPequeña3, bolaPequeña4);
                                aunmentarPuntuacion(1000);
                                disparo2 = false;
                            }
                        }
                    }
                    if (dispararGanchoDoble) { //DisparoGanchoDoble
                        if (disparo1) {
                            if (disparoGancho.hayColision(bolaMediana2)) { //Disparo1
                                sonido(sonidoPop);
                                bolaPequeña3Up = true;
                                bolaPequeña4Up = true;
                                bolaPequeña3.setPequeña(true);
                                bolaPequeña4.setPequeña(true);
                                bolaMediana2Up = false;
                                configurarBola2(bolaMediana2, bolaPequeña3, bolaPequeña4);
                                aunmentarPuntuacion(1000);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoGancho2.hayColision(bolaMediana2)) { //Disparo2
                                sonido(sonidoPop);
                                bolaPequeña3Up = true;
                                bolaPequeña4Up = true;
                                bolaPequeña3.setPequeña(true);
                                bolaPequeña4.setPequeña(true);
                                bolaMediana2Up = false;
                                configurarBola2(bolaMediana2, bolaPequeña3, bolaPequeña4);
                                aunmentarPuntuacion(1000);
                                disparo2 = false;
                            }
                        }
                    }
                }

                //Bolas pequeñas -----------------------------------------------------------------------
                if (bolaPequeña1Up) { //BolaPequeña1 ------------------
                    if (dispararGancho) { //DisparoGancho
                        if (disparar) {
                            if (disparoGancho.hayColision(bolaPequeña1)) {
                                sonido(sonidoPop);
                                drop(bolaPequeña1);
                                bolaPequeña1Up = false;
                                disparar = false;
                                aunmentarPuntuacion(1500);
                            }
                        }
                    }
                    if (dispararPistola) { //DisparoPistola
                        if (disparo1) {
                            if (disparoPistola.hayColision(bolaPequeña1)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña1);
                                bolaPequeña1Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoPistola2.hayColision(bolaPequeña1)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña1);
                                bolaPequeña1Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                    if (dispararGanchoDoble) { //DisparoGanchoDoble
                        if (disparo1) {
                            if (disparoGancho.hayColision(bolaPequeña1)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña1);
                                bolaPequeña1Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoGancho2.hayColision(bolaPequeña1)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña1);
                                bolaPequeña1Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                }

                if (bolaPequeña2Up) { //BolaPequeña2 ------------------
                    if (dispararGancho) { //DisparoGancho
                        if (disparar) {
                            if (disparoGancho.hayColision(bolaPequeña2)) {
                                sonido(sonidoPop);
                                drop(bolaPequeña2);
                                bolaPequeña2Up = false;
                                disparar = false;
                                aunmentarPuntuacion(1500);
                            }
                        }
                    }
                    if (dispararPistola) { //DisparoPistola
                        if (disparo1) {
                            if (disparoPistola.hayColision(bolaPequeña2)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña2);
                                bolaPequeña2Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoPistola2.hayColision(bolaPequeña2)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña2);
                                bolaPequeña2Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                    if (dispararGanchoDoble) { //DisparoGanchoDoble
                        if (disparo1) {
                            if (disparoGancho.hayColision(bolaPequeña2)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña2);
                                bolaPequeña2Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoGancho2.hayColision(bolaPequeña2)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña2);
                                bolaPequeña2Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                }

                if (bolaPequeña3Up) { //BolaPequeña3 ------------------
                    if (dispararGancho) { //DisparoGancho
                        if (disparar) {
                            if (disparoGancho.hayColision(bolaPequeña3)) {
                                sonido(sonidoPop);
                                drop(bolaPequeña3);
                                bolaPequeña3Up = false;
                                disparar = false;
                                aunmentarPuntuacion(1500);
                            }
                        }
                    }
                    if (dispararPistola) { //DisparoPistola
                        if (disparo1) {
                            if (disparoPistola.hayColision(bolaPequeña3)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña3);
                                bolaPequeña3Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoPistola2.hayColision(bolaPequeña3)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña3);
                                bolaPequeña3Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                    if (dispararGanchoDoble) { //DisparoGanchoDoble
                        if (disparo1) {
                            if (disparoGancho.hayColision(bolaPequeña3)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña3);
                                bolaPequeña3Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoGancho2.hayColision(bolaPequeña3)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña1);
                                bolaPequeña3Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                }

                if (bolaPequeña4Up) { //BolaPequeña4 ------------------
                    if (dispararGancho) { //DisparoGancho
                        if (disparar) {
                            if (disparoGancho.hayColision(bolaPequeña4)) {
                                sonido(sonidoPop);
                                drop(bolaPequeña4);
                                bolaPequeña4Up = false;
                                disparar = false;
                                aunmentarPuntuacion(1500);
                            }
                        }
                    }
                    if (dispararPistola) { //DisparoPistola
                        if (disparo1) {
                            if (disparoPistola.hayColision(bolaPequeña4)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña4);
                                bolaPequeña4Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoPistola2.hayColision(bolaPequeña4)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña4);
                                bolaPequeña4Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                    if (dispararGanchoDoble) { //DisparoGanchoDoble
                        if (disparo1) {
                            if (disparoGancho.hayColision(bolaPequeña4)) { //Disparo1
                                sonido(sonidoPop);
                                drop(bolaPequeña4);
                                bolaPequeña4Up = false;
                                aunmentarPuntuacion(1500);
                                disparo1 = false;
                            }
                        }
                        if (disparo2) {
                            if (disparoGancho2.hayColision(bolaPequeña4)) { //Disparo2
                                sonido(sonidoPop);
                                drop(bolaPequeña4);
                                bolaPequeña4Up = false;
                                aunmentarPuntuacion(1500);
                                disparo2 = false;
                            }
                        }
                    }
                }

                //Objetos-Personaje
                if (objetoPistolaUp) { //Pistola
                    objetoPistola.onDraw(canvas);
                    if (objetoPistola.hayColision(personaje)) {
                        objetoPistolaUp = false;
                        dispararPistola = true;
                        dispararGanchoDoble = false;
                        dispararGancho = false;
                    }
                }
                if (objetoGanchoDobleUp) { //Gancho doble
                    objetoGanchoDoble.onDraw(canvas);
                    if (objetoGanchoDoble.hayColision(personaje)) {
                        dispararGanchoDoble = true;
                        dispararGancho = false;
                        objetoGanchoDobleUp = false;
                        dispararPistola = false;
                    }
                }

                //Bolas-Personaje
                if (bolaGrande1Up) {
                    if (bolaGrande1.hayColision(personaje)) {
                        gameover = true;
                    }
                }
                if (bolaMediana1Up) {
                    if (bolaMediana1.hayColision(personaje)) {
                        gameover = true;
                    }
                }
                if (bolaMediana2Up) {
                    if (bolaMediana2.hayColision(personaje)) {
                        gameover = true;
                    }
                }
                if (bolaPequeña1Up) {
                    if (bolaPequeña1.hayColision(personaje)) {
                        gameover = true;
                    }
                }
                if (bolaPequeña2Up) {
                    if (bolaPequeña2.hayColision(personaje)) {
                        gameover = true;
                    }
                }
                if (bolaPequeña3Up) {
                    if (bolaPequeña3.hayColision(personaje)) {
                        gameover = true;
                    }
                }
                if (bolaPequeña4Up) {
                    if (bolaPequeña4.hayColision(personaje)) {
                        gameover = true;
                    }
                }
            }
        }else{
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent i = new Intent(contexto, MenuMuerte.class);
                    i.putExtra("puntuacion",String.valueOf(score));
                    contexto.startActivity(i);
                }
            };

            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }

        if(!bolaGrande1Up && !bolaMediana1Up && !bolaMediana2Up && !bolaPequeña1Up && !bolaPequeña2Up && !bolaPequeña3Up && !bolaPequeña4Up) {
            Intent i = new Intent(contexto, MenuVictoria.class);
            i.putExtra("puntuacion",String.valueOf(score));
            contexto.startActivity(i);
        }
    }


    //Boton disparar y control de los disparos -----------------------------------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 10) {
            lastClick = System.currentTimeMillis();
            float x = event.getX();
            float y = event.getY();
            synchronized ((getHolder())) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (botonDisparo.hayColosion(x, y)) {
                        if (dispararGancho && !disparar) {
                            disparoGancho = new SpriteDisparoGancho(pantalla, bmpDisparoGancho, personaje.getX(), personaje.getY());
                            disparar = true;
                        }
                        if (dispararPistola) {
                            if(!disparo1) {
                                disparoPistola = new SpriteDisparoPistola(pantalla, bmpDisparoPistola, personaje.getX(), personaje.getY());
                                if(!disparo1 && !disparo2 || !disparo1 && disparo2) {
                                    disparo1 = true;
                                }
                            }else if(!disparo2) {
                                disparoPistola2 = new SpriteDisparoPistola(pantalla, bmpDisparoPistola, personaje.getX(), personaje.getY());
                                if (disparo1 && !disparo2) {
                                    disparo2 = true;
                                }
                            }
                        }
                        if (dispararGanchoDoble) {
                            if(!disparo1) {
                                disparoGancho = new SpriteDisparoGancho(pantalla, bmpDisparoGancho, personaje.getX(), personaje.getY());
                                if(!disparo1 && !disparo2 || !disparo1 && disparo2) {
                                    disparo1 = true;
                                }
                            }else if(!disparo2) {
                                disparoGancho2 = new SpriteDisparoGancho(pantalla, bmpDisparoGancho, personaje.getX(), personaje.getY());
                                if (disparo1 && !disparo2) {
                                    disparo2 = true;
                                }
                            }
                        }
                        personaje.disparar();
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    personaje.quieto();
                }
            }
        }
        return true;
    }

    //Metodos utiles ------------------------------------------------------------------------------------

    //Set juego
    public void setJuego(Juego j){
        juego = j;
    }

    //Controla el drop de objetos
    public void drop(SpriteBola bola) {
        drop = bola.drop();
        if(drop == 1) {
            objetoPistola.setX(bola.getX());
            objetoPistola.setY(bola.getY());
            objetoPistolaUp = true;
        }else if(drop == 2) {
            objetoGanchoDoble.setX(bola.getX());
            objetoGanchoDoble.setY(bola.getY());
            objetoGanchoDobleUp = true;
        }
    }

    //Crear Bolas
    public void configurarBola(SpriteBola bola, SpriteBola bola2, SpriteBola bola3) {
        drop(bola);

        bola2.setX(bola.getX());
        bola2.setParametroGrande1(bola.getParametroGrande1());
        bola2.setParametroGrande2(bola.getParametroGrande2());
        bola2.setH(bola.getH());

        bola3.setX(bola.getX());
        bola3.setParametroGrande1(bola.getParametroGrande1());
        bola3.setParametroGrande2(bola.getParametroGrande2());
        bola3.setH(bola.getH());
    }

    public void configurarBola2(SpriteBola bola, SpriteBola bola2, SpriteBola bola3) {
        drop(bola);

        bola2.setX(bola.getX());
        bola2.setParametroMediano1(bola.getParametroMediano1());
        bola2.setParametroMediano2(bola.getParametroMediano2());
        bola2.setParametroPequeño1(bola.getParametroMediano1());
        bola2.setParametroPequeño2(bola.getParametroMediano2());
        bola2.setMediana(false);

        bola3.setX(bola.getX());
        bola3.setParametroMediano1(bola.getParametroMediano1());
        bola3.setParametroMediano2(bola.getParametroMediano2());
        bola3.setParametroPequeño1(bola.getParametroMediano1());
        bola3.setParametroPequeño2(bola.getParametroMediano2());
        bola3.setMediana(false);
    }

    //Sonido
    public void sonido(int sonido) {
        this.sonido.play(sonido, 1, 1, 1, 0, 1);
    }

    //SpritePuntuacion
    public void aunmentarPuntuacion(int puntuacion){
        score = score + puntuacion;
        this.puntuacion.setTexto(String.valueOf(score));
    }
}
