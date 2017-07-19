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

        db.execSQL("CREATE table  noticias(id REAL PRIMARY KEY, titulo TEXT,descripcion TEXT,cuerpo TEXT,linkImagen TEXT,linkNoticia TEXT, fecha TEXT, imagen BLOB)");

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
            long idTime=0;
            if(not.getFecha()!=null)
             //generamos un id a partir de la fecha de la noticia
            {idTime= not.getFecha().getTime();}
            registro.put("id", idTime);
            registro.put("titulo", not.getTitulo());
            registro.put("descripcion", not.getDesc());
            registro.put("cuerpo", not.getCuerpo());
            registro.put("linkImagen", not.getLinkImagen());
            registro.put("linkNoticia", not.getLinkNoticia());
            SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
            String date = sdf.format(not.getFecha());
            registro.put("fecha", date);
            registro.put("imagen", Conversor.getBytes(not.getImagen()));
          int res= (int) bd.insertWithOnConflict("noticias", null, registro,SQLiteDatabase.CONFLICT_ABORT);
           // bd.insert("noticias", null, registro);
            bd.close();}
        catch(Exception ex){
          System.out.println("LOG: "+ex.toString());
            retorno=false;}
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

    /**
     * Consulta noticias que cumplan los filtros indicados.
     * @param limite máximo de noticias a mostrar
     * @param tit cadena a buscar en título
     * @param bod cadena a buscar en cuerpo
     * @param timeId fecha comparable con nuestro formato de id utilizado
     * @return lista de noticias recuperadas
     */
    public ArrayList<NoticiaObtenida> consultaNoticiasBuscador(int limite, String tit, String bod,long timeId) {

        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor cursor = bd.rawQuery(
                "SELECT id,titulo,descripcion,cuerpo,linkImagen,linkNoticia,fecha,imagen FROM noticias " +
                        "WHERE (('"+tit+"'='' OR titulo LIKE '%"+tit+"%' )AND ("+timeId+"=0 OR "+timeId+"<id) AND('"+bod+"'  ='' OR cuerpo LIKE'%"+bod+"%') ) " +
                        "ORDER BY id DESC LIMIT '"+limite+"'",null);
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
            not.setFecha(Conversor.convierteStringFecha(cursor.getString(6),Constantes.FORMATO_FECHA));
            not.setImagen(Conversor.getImage(cursor.getBlob(7)));
            listaNoticias.add(not);
        }
        bd.close();
        return listaNoticias;
    }

}
