package com.atsistemas.alopezcastillo.myrss.ln;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;

import java.io.InputStream;

/**
 * Clase que obtiene de manera asíncrona una imagen de una URL.
 *
 * Created by alopez.castillo on 13/07/2017.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;
    NoticiaObtenida no;


    public DownloadImageTask(ImageView bmImage, NoticiaObtenida no) {
        this.bmImage = bmImage;
        this.no =no;
    }



    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        //Setea la imagen obtenida en los objetos pasados al constructor como parámetros
        bmImage.setImageBitmap(result);
        no.setImagen(result);

    }
}
