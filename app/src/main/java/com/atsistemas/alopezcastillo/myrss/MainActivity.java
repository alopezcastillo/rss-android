package com.atsistemas.alopezcastillo.myrss;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;
import com.atsistemas.alopezcastillo.myrss.ln.AsyncResponse;
import com.atsistemas.alopezcastillo.myrss.ln.Conexiones;
import com.atsistemas.alopezcastillo.myrss.ln.DownloadImageTask;
import com.atsistemas.alopezcastillo.myrss.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad principal. Muestra el listado de noticias obtenidos por rss.
 */
public class MainActivity extends AppCompatActivity implements AsyncResponse {


    List<NoticiaObtenida> listaNoticias = new ArrayList<NoticiaObtenida>();
    TableLayout tablaNoticias = null;

    Conexiones con = new Conexiones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablaNoticias = (TableLayout)findViewById(R.id.tablaNoticias);

        con.delegate =this;
        con.execute(Constantes.URL);


    }

    @Override
    public void onStart() {
        super.onStart();
       // SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //ssssss


    }

    /**
     * Navega a la página de detalle pasando como parámetros la información de la noticia seleccionada.
     * @param id identificador de la posición que ocupa en la lista de noticias el objeto seleccionado.
     */
    public void navegar(int id)

    {
        Intent i = new Intent(this, Noticia.class );

        i.putExtra("titulo", listaNoticias.get(id).getTitulo());
        i.putExtra("imagen", listaNoticias.get(id).getLinkImagen());
        i.putExtra("cuerpo", listaNoticias.get(id).getCuerpo());
        i.putExtra("url",listaNoticias.get(id).getLinkNoticia());
        startActivity(i);
    }


    /**
     * Proceso que prepara la página tras haberse recuperado asíncronamente la lista de noticias.
     * @param noticiasObtenidas
     */
    @Override
    public void processFinish(final List<NoticiaObtenida> noticiasObtenidas) {

        listaNoticias=noticiasObtenidas;

        //Inicialización dinámica de la tabla de noticias
        tablaNoticias.setStretchAllColumns(true);
        tablaNoticias.bringToFront();

        for(int i = 0; i < listaNoticias.size(); i++){
            TableRow tr1 =  new TableRow(this);
            TableRow tr2 =  new TableRow(this);
            TableRow tr3 =  new TableRow(this);
            TableLayout.LayoutParams tableRowParamsTop=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tableRowParamsTop.setMargins(10, 15, 10, 5);
            tr1.setBackgroundColor(Color.BLUE);
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tableRowParams.setMargins(10, 5, 10, 5);
            tr1.setLayoutParams(tableRowParamsTop);
            tr2.setLayoutParams(tableRowParams);
            tr3.setLayoutParams(tableRowParams);

            TextView c1 = new TextView(this);
            c1.setTypeface(null, Typeface.BOLD);
            c1.setTextColor(Color.WHITE);
            c1.setPadding(5,1,2,1);
            c1.setMaxWidth(tr1.getLayoutParams().width);

            ImageView c2 = new ImageView(this);
            TextView c3 = new TextView(this);
            //Incluimos un evento onclick a los componentes de la tabla
             View.OnClickListener miEvento = new View.OnClickListener() {

                 //Evento lanzado al hacer click en el elemento, enviando como parámetro la posición de la lista que nos indicará a qué noticia pertenece
                @Override
                public void onClick(View v) {
                    int id =  v.getId();
                    navegar((int)id/100);


                }
            };
            //generamos manualmente IDs a los elementos para poder saber cual fue el que el que lanzó el evento.
            //formato XYY, donde YY indica el componente y X la fila de la tabla.
            c1.setId(i*100+1);
            c1.setOnClickListener(miEvento);
            c2.setId(i*100+2);
            c2.setOnClickListener(miEvento);
            c3.setId(i*100+3);
            c3.setOnClickListener(miEvento);

            //Recuperación asíncrona de la imagen
            new DownloadImageTask (c2).execute(listaNoticias.get(i).getLinkImagen());

            c1.setText(listaNoticias.get(i).getTitulo());
            c3.setText(listaNoticias.get(i).getDesc());
            //ajustamos el ancho máximo forzando a dividirse en filas si no cabe en una.
            c3.setMaxWidth(tr3.getLayoutParams().width);

            tr1.addView(c1);
            tr2.addView(c2);
            tr3.addView(c3);
            tablaNoticias.addView(tr1);
            tablaNoticias.addView(tr2);
            tablaNoticias.addView(tr3);
        }


    }


}