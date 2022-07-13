package com.example.ejercicio23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ejercicio23.Procesos.Fotos;
import com.example.ejercicio23.Procesos.Photograh;
import com.example.ejercicio23.Procesos.SQLiteConexion;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView lista;
    ArrayList<Fotos> listafotos;
    ArrayList<String> ArregloFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        conexion  = new SQLiteConexion(this, Photograh.NameDatabase, null, 1);
        lista = (ListView) findViewById(R.id.lista);

        ObtenerListaFotos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloFotos);
        lista.setAdapter(adp);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarPicture(i);
            }
        });
    }

    private void mostrarPicture(int i) {
        Fotos fotos = listafotos.get(i);

        Bundle bundle = new Bundle();
        bundle.putSerializable("fotos", fotos);

        Intent intent = new Intent(getApplicationContext(), DetalleFoto.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void ObtenerListaFotos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Fotos list_emple = null;
        listafotos = new ArrayList<Fotos>();

        Cursor cursor = db.rawQuery(Photograh.SELECT_ALL_TABLE_PICTURE,null);

        while(cursor.moveToNext())
        {
            list_emple = new Fotos();

            list_emple.setId(cursor.getInt(0));
            list_emple.setDescripcion(cursor.getString(1));
            list_emple.setPathImage(cursor.getString(2));
            list_emple.setImagen(cursor.getBlob(3));

            listafotos.add(list_emple);
        }

        cursor.close();

        llenalista();
    }

    private void llenalista() {
        ArregloFotos = new ArrayList<String>();

        for(int i=0; i<listafotos.size(); i++)
        {
            ArregloFotos.add(listafotos.get(i).getId() +" | "+
                    listafotos.get(i).getDescripcion() +" | "+
                    listafotos.get(i).getPathImage());
        }
    }
}