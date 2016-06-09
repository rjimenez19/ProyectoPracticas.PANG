package com.example.rafa.pang2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.rafa.pang2.Adaptador.Adaptador;
import com.example.rafa.pang2.BD.GestorPuntuacion;

public class Puntuaciones extends AppCompatActivity {

    private ListView lv;
    private Adaptador adp;
    private GestorPuntuacion gp;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);

        gp = new GestorPuntuacion(this);
        lv = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onResume(){
        gp.open();
        c = gp.getCursor();
        adp = new Adaptador(this, c, gp);
        lv.setAdapter(adp);
        super.onResume();
    }

    @Override
    protected void onPause() {
        gp.close();
        super.onPause();
    }
}
