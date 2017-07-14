package com.atsistemas.alopezcastillo.myrss;

import android.content.Intent;
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

/**
 * Vista de detalle de la noticia seleccionada.
 */
public class Noticia extends AppCompatActivity {


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
        String ima=   bundle.getString("imagen");
        url = bundle.getString("url");

        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        ivImagen = (ImageView)findViewById(R.id.imagen);
        tvCuerpo = (TextView)findViewById((R.id.tvDesc));
        wvNavegador=(WebView)findViewById(R.id.wvNav);
        //TableLayout tl = (TableLayout)findViewById(R.id.tlNoticia);
       // tvTitulo.setMaxWidth(tl.getLayoutParams().width);
       // tvCuerpo.setMaxWidth(tl.getLayoutParams().width);
       // ivImagen.setMaxWidth(tl.getLayoutParams().width);

        tvTitulo.setText(tit);
        tvCuerpo.setText(cue);
        new DownloadImageTask(ivImagen).execute(ima);


    }


    /**
     * Cierra la vista de Detalle y vuelve al menú.
     * @param view
     */
    public void cerrar(View view) {

        finish();
    }


    public void irNavegador(View view)
    {
        wvNavegador.loadUrl(url);
       // Intent i = new Intent(this, NavegadorActivity.class );

      //  i.putExtra("url", url);
      //  startActivity(i);
    }
}
