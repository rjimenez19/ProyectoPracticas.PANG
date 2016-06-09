package com.example.rafa.pang2;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.rafa.pang2.Sprites.SpritePj;
import com.example.rafa.pang2.Util.joyStickClass;

public class Juego extends Activity{

    private Pantalla pantalla;
    private joyStickClass js;
    public SpritePj personaje;
    RelativeLayout layout_joystick;
    private MediaPlayer cancion;
    protected PowerManager.WakeLock wakelock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //Para que no se apague la pantalla
        final PowerManager pm = (PowerManager)getSystemService(this.POWER_SERVICE);
        this.wakelock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,"juego");
        this.wakelock.acquire();

        //Cancion
        cancion = MediaPlayer.create(this.getApplicationContext(), R.raw.cancion);
        cancion.start();

        pantalla = (Pantalla)findViewById(R.id.surfaceView1);
        pantalla.setJuego(this);

        //Joystick
        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);
        js = new joyStickClass(getApplicationContext(), layout_joystick, R.drawable.joystick);
        js.setStickSize(50, 50);
        js.setLayoutSize(200, 200);
        js.setLayoutAlpha(150);
        js.setStickAlpha(45);
        js.setOffset(40);
        js.setMinimumDistance(30);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    int direction = js.get8Direction();
                    if(direction == joyStickClass.STICK_UP) {

                    } else if(direction == joyStickClass.STICK_UPRIGHT) {

                    } else if(direction == joyStickClass.STICK_RIGHT) {
                        personaje.moverDerecha();
                    } else if(direction == joyStickClass.STICK_DOWNRIGHT) {

                    } else if(direction == joyStickClass.STICK_DOWN) {

                    } else if(direction == joyStickClass.STICK_DOWNLEFT) {

                    } else if(direction == joyStickClass.STICK_LEFT) {
                        personaje.moverIzquierda();
                    } else if(direction == joyStickClass.STICK_UPLEFT) {

                    } else if(direction == joyStickClass.STICK_NONE) {

                    }
                }
                if (arg1.getAction() == MotionEvent.ACTION_UP){
                    personaje.quieto();
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.wakelock.acquire();
        cancion.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancion.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.wakelock.release();
        cancion.stop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.wakelock.release();
    }

    public void personaje(SpritePj personaje){
        this.personaje = personaje;
    }
}

