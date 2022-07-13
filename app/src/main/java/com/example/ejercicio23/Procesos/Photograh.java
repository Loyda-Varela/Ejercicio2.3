package com.example.ejercicio23.Procesos;

public class Photograh {
    // Nombre de la base de datos
    public static final String NameDatabase = "DBFOTOS";

    // Creacion de las tablas Fotos en la base de datos
    public static final String tablafotos = "Fotos";
    /*
      Campos especificos de la tabla
    */
    public static final String id = "id";
    public static final String descripcion = "descripcion";
    public static final String pathImage = "pathImage";
    public static final String imagen = "imagen";


    public static final String CreateTableFotos = "CREATE TABLE fotos " +
             "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
             "descripcion TEXT, pathImage TEXT " +
             "imagen BLOB)";

    public static final String DropTableFotos ="DROP TABLE IF EXISTS fotos";
    //Seleccionar todas las personas
    public static final String SELECT_ALL_TABLE_PICTURE = "SELECT * FROM " + tablafotos;

}
