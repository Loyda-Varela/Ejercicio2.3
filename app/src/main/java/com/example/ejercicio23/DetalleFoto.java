package com.example.ejercicio23;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ejercicio23.Procesos.Fotos;

import java.io.ByteArrayInputStream;

public class DetalleFoto extends AppCompatActivity {
    ImageView imageViewMostrarIMG;
    EditText Descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_foto);

        imageViewMostrarIMG = (ImageView) findViewById(R.id.IMGVMostrarFotoP);
        Descripcion = (EditText) findViewById(R.id.txtMostDescripcion);
        Bundle objetEnvia = getIntent().getExtras();
        Fotos imagen = null;

        if(objetEnvia != null){
            imagen = (Fotos) objetEnvia.getSerializable("fotos");


            Descripcion.setText(imagen.getDescripcion());
            mostrarImagen(imagen.getImagen());
            Bitmap image = BitmapFactory.decodeFile(imagen.getPathImage());
            imageViewMostrarIMG.setImageBitmap(image);

        }
    }
    private void mostrarImagen(byte[] image) {
        Bitmap bitmap = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(image);
        bitmap = BitmapFactory.decodeStream(bais);
        imageViewMostrarIMG.setImageBitmap(bitmap);
    }

}