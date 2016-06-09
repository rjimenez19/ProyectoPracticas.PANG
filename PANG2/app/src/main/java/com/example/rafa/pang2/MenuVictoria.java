package com.example.rafa.pang2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rafa.pang2.BD.GestorPuntuacion;
import com.example.rafa.pang2.BD.Puntuacion;

public class MenuVictoria extends AppCompatActivity {

    private EditText ed;
    private String puntuacion;
    private Puntuacion score;
    private GestorPuntuacion gp;
    private Cursor c;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_victoria);

        tv = (TextView) findViewById(R.id.textView11);
        puntuacion = getIntent().getExtras().getString("puntuacion");
        gp = new GestorPuntuacion(this);
        tv.setText(puntuacion);
    }

    @Override
    protected void onResume(){
        gp.open();
        gp.open();
        c = gp.getCursor();
        super.onResume();
    }

    @Override
    protected void onPause() {
        gp.close();
        gp.close();
        super.onPause();
    }

    public void guardar(View v){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Nombre");
        LayoutInflater inflater= LayoutInflater.from(this);

        final View vista = inflater.inflate(R.layout.guardar, null);

        alert.setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ed = (EditText) vista.findViewById(R.id.editText);
                        score = new Puntuacion(ed.getText().toString(), Integer.parseInt(puntuacion));
                        gp.insert(score);
                    }
                });
        alert.setView(vista);
        alert.setNegativeButton(R.string.cancelar, null);
        alert.show();
    }

    public void ver(View v){
        Intent i = new Intent(this, Puntuaciones.class);
        this.startActivity(i);
    }

    public void jugar(View v){
        Intent i = new Intent(this, Juego.class);
        this.startActivity(i);
    }
}
