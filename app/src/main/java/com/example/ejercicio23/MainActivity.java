package com.example.ejercicio23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ejercicio23.Procesos.AlertDialogo;
import com.example.ejercicio23.Procesos.Photograh;
import com.example.ejercicio23.Procesos.SQLiteConexion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText descripcion;
    Button btnGuardar,btnFoto,btnlista;
    Bitmap imagenGlobal;

    ImageView Imagen;
    String CurrentPhotoPath;
    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descripcion = (EditText) findViewById(R.id.txtdescripcion);
        Imagen = (ImageView) findViewById(R.id.Imagen);

        btnFoto = (Button) findViewById(R.id.btnFoto);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnlista = (Button) findViewById(R.id.btnLista);


        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!descripcion.getText().toString().isEmpty())
                {
                    agregarFoto();
                }
                else{
                    Mensaje();
                }
            }
        });

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityLista.class);
                startActivity(intent);
            }
        });
    }

    private void Mensaje() {
        AlertDialogo alerta = new AlertDialogo();
        alerta.show(getSupportFragmentManager(),"Mensaje");
    }

    private void agregarFoto(){

        SQLiteConexion conexion = new SQLiteConexion(this, Photograh.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Photograh.descripcion, descripcion.getText().toString());
        values.put(Photograh.pathImage, CurrentPhotoPath);


        ByteArrayOutputStream baos = new ByteArrayOutputStream(10480);

        imagenGlobal.compress(Bitmap.CompressFormat.JPEG, 0 , baos);

        byte[] blob = baos.toByteArray();

        values.put(Photograh.imagen, blob);

        Long result = db.insert(Photograh.tablafotos, Photograh.id, values);

        Toast.makeText(getApplicationContext(), "Registro exitoso " + result.toString()
                ,Toast.LENGTH_LONG).show();

        db.close();

        LimpiarPatalla();
    }

    private void LimpiarPatalla() {
        descripcion.setText("");
        Imagen.setImageBitmap(null);
        imagenGlobal = null;
    }

    private void permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESO_CAM);
        }
        else
        {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.toString();
            }
            // Continue only if the File was successfully created
            try {
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.ejercicio23.fileprovider",

                            photoFile);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    startActivityForResult(takePictureIntent, TAKE_PIC_REQUEST);
                }
            }catch (Exception e){
                Log.i("Error", "dispatchTakePictureIntent: " + e.toString());
            }
        }
    }

    //Para llenar en el almacenamiento
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpeg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        CurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_ACCESO_CAM)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                dispatchTakePictureIntent();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Se necesitan permisos para acceder a la camara", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Byte[] arreglo;

        if(requestCode == TAKE_PIC_REQUEST && resultCode == RESULT_OK)
        {
            Bitmap image = BitmapFactory.decodeFile(CurrentPhotoPath);
            imagenGlobal = image;
            Imagen.setImageBitmap(image);

            Toast.makeText(getApplicationContext(), "Registro exitoso"
                    ,Toast.LENGTH_LONG).show();
        }
    }
    
}