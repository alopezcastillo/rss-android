package com.atsistemas.alopezcastillo.myrss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;


import com.atsistemas.alopezcastillo.myrss.ln.DownloadImageTask;
import com.atsistemas.alopezcastillo.myrss.utils.Conversor;

/**
 * Vista de detalle de la noticia seleccionada.
 */
public class NoticiaActivity extends AppCompatActivity {


    private TextView tvTitulo;
    private TextView tvCuerpo;
    private ImageView ivImagen;
    private WebView wvNavegador;


    private String url;

    /**
     * Inicializa los objetos con los parámetros recibidos.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        Bundle bundle = getIntent().getExtras();
        String tit=   bundle.getString("titulo");
        String cue=   bundle.getString("cuerpo");
        //Bundle no utiliza Bitmap, por ello utilizamos conversión a array de bytes.
        byte[] ima=   bundle.getByteArray("imagen");
        url = bundle.getString("url");

        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        ivImagen = (ImageView)findViewById(R.id.imagen);
          tvCuerpo = (TextView)findViewById((R.id.tvDescip));
        wvNavegador=(WebView)findViewById(R.id.wvNav);
        tvTitulo.setText(tit);
        tvCuerpo.setText(cue);
        ivImagen.setImageBitmap(Conversor.getImage(ima));

    }


    /**
     * Cierra la vista de Detalle y vuelve al menú.
     * @param view
     */
    public void cerrar(View view) {

        finish();
    }

    /*Abre el navegador web con la url indicada */
    public void irNavegador(View view)
    {
        wvNavegador.loadUrl(url);

    }
}
