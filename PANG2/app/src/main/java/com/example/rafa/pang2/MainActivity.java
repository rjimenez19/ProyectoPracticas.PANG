package com.example.rafa.pang2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jugar(View v){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }

    public  void verPuntuaciones(View v){
        Intent i = new Intent(this, Puntuaciones.class);
        this.startActivity(i);
    }
}