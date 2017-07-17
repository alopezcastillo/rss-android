package com.atsistemas.alopezcastillo.myrss.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;
import com.atsistemas.alopezcastillo.myrss.utils.Constantes;
import com.atsistemas.alopezcastillo.myrss.utils.Conversor;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by alopez.castillo on 17/07/2017.
 */

public class TablaNoticias extends SQLiteOpenHelper {

    public TablaNoticias(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table IF NOT EXISTS noticias(id integer key, titulo text,descripcion text,cuerpo text,linkImagen text,linkNoticia text, fecha text, imagen blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean alta(NoticiaObtenida not) {
        //   AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
        //           "administracion", null, 1);
        boolean retorno =true;
        try{

            SQLiteDatabase bd = this.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("id", not.getId());
            registro.put("titulo", not.getTitulo());
            registro.put("descripcion", not.getDesc());
            registro.put("cuerpo", not.getCuerpo());
            registro.put("linkImagen", not.getLinkImagen());
            registro.put("linkNoticia", not.getLinkNoticia());
            SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
            String date = sdf.format(not.getFecha());
            registro.put("fecha", date);
            registro.put("imagen", Conversor.getBytes(not.getImagen()));
            bd.insert("noticias", null, registro);
            bd.close();}
        catch(Exception ex){retorno=false;}
        return retorno;

    }
    public void eliminarTodo() {

        SQLiteDatabase bd = this.getWritableDatabase();

        ContentValues registro = new ContentValues();
        bd.delete("noticias",null,null);
        //registro.put("estado", estado);
        //int cant = bd.update("citas", registro, "doi= '"+ doi+"'", null);

        bd.close();

    }

    public ArrayList<NoticiaObtenida> consultaNoticias(int limite) {

        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor cursor = bd.rawQuery(
                "SELECT id,titulo,descripcion,cuerpo,linkImagen,linkNoticia,fecha,imagen FROM noticias ORDER BY id DESC LIMIT '"+limite+"'",null);
        ArrayList<NoticiaObtenida> listaNoticias = new ArrayList<NoticiaObtenida>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            NoticiaObtenida not = new NoticiaObtenida();
            // The Cursor is now set to the right position
            not.setId(Integer.valueOf(cursor.getInt(0)));
            not.setTitulo(cursor.getString(1));
            not.setDesc(cursor.getString(2));
            not.setCuerpo(cursor.getString(3));
            not.setLinkImagen(cursor.getString(4));
            not.setLinkNoticia(cursor.getString(5));
            DateFormat df = new SimpleDateFormat(Constantes.FORMATO_FECHA);
            Date fecha= null;
            try {
                 fecha= df.parse(cursor.getString(6));
            } catch (ParseException e) {
                System.out.println("LOG: Error en el parseo de la fecha "+cursor.getString(6));
                e.printStackTrace();
            }
            not.setFecha(fecha);
            not.setImagen(Conversor.getImage(cursor.getBlob(7)));
            listaNoticias.add(not);
        }
        bd.close();
        return listaNoticias;
    }

}
