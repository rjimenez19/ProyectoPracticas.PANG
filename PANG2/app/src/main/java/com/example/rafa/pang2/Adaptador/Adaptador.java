package com.example.rafa.pang2.Adaptador;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rafa.pang2.BD.GestorPuntuacion;
import com.example.rafa.pang2.BD.Puntuacion;
import com.example.rafa.pang2.R;

public class Adaptador extends CursorAdapter {

    private GestorPuntuacion gestor;
    private Context contexto;
    private Cursor cu;

    public Adaptador (Context co, Cursor cu, GestorPuntuacion gp) {
        super(co, cu, true);
        this.gestor = gp;
        this.contexto = co;
        this.cu = cu;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.item, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1 = (TextView) view.findViewById(R.id.textView7);
        TextView tv2 = (TextView) view.findViewById(R.id.textView8);
        Puntuacion p = gestor.getRow(cursor);
        tv1.setText(p.getNombre());
        tv2.setText(Integer.toString(p.getPuntuacion()));
    }
}
