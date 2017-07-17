package com.atsistemas.alopezcastillo.myrss;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;
import com.atsistemas.alopezcastillo.myrss.ln.AsyncResponse;
import com.atsistemas.alopezcastillo.myrss.ln.Conexiones;
import com.atsistemas.alopezcastillo.myrss.ln.DownloadImageTask;
import com.atsistemas.alopezcastillo.myrss.servicio.ServicioSQLite;
import com.atsistemas.alopezcastillo.myrss.utils.Constantes;
import com.atsistemas.alopezcastillo.myrss.utils.Conversor;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad principal. Muestra el listado de noticias obtenidos por rss.
 */
public class MainActivity extends AppCompatActivity implements AsyncResponse {


    List<NoticiaObtenida> listaNoticias = new ArrayList<NoticiaObtenida>();
    TableLayout tablaNoticias = null;
    ServicioSQLite servicioBD ;
    //indicador de estado de la conexión
    boolean conexion =true;

    Conexiones con = new Conexiones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        servicioBD = new ServicioSQLite(this);
        tablaNoticias = (TableLayout)findViewById(R.id.tablaNoticias);

        con.delegate =this;
        con.execute(Constantes.URL);


    }

    @Override
    public void onStart() {
        super.onStart();
       // SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    }

    /**
     * Navega a la página de detalle pasando como parámetros la información de la noticia seleccionada.
     * @param id identificador de la posición que ocupa en la lista de noticias el objeto seleccionado.
     */
    public void navegar(int id)

    {
        Intent i = new Intent(this, NoticiaActivity.class );

        i.putExtra("titulo", listaNoticias.get(id).getTitulo());
        //Convertimos a imagen a array de bytes
        i.putExtra("imagen", Conversor.getBytes(listaNoticias.get(id).getImagen()));
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

     /*   //Si hemos obtenido noticias las guardamos. Si no buscamos en bbdd las ultimas.
        ServicioSQLite servicioBD = new ServicioSQLite(this);
        if(null!=noticiasObtenidas && noticiasObtenidas.size()>0)
        {servicioBD.noticiasAlta(listaNoticias);}
        else*/
        if(null==noticiasObtenidas || noticiasObtenidas.isEmpty())
        {
            conexion=false;
         listaNoticias= servicioBD.noticiasObtenerUltimas();
            Toast.makeText(this,R.string.alert_no_news,Toast.LENGTH_LONG).show();}

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

            //Recuperación asíncrona de la imagen si hay conexión
            if(conexion)
            {new DownloadImageTask (c2,listaNoticias.get(i)).execute(listaNoticias.get(i).getLinkImagen());
            }
            else{//imagen de bbdd
                c2.setImageBitmap(listaNoticias.get(i).getImagen());
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menuAcercaDe) {
            Toast.makeText(this,R.string.menu_aboutText,Toast.LENGTH_LONG).show();
        }
        if (id==R.id.menuBusqueda) {
            Toast.makeText(this,"En construcción",Toast.LENGTH_LONG).show();
        }
        if (id==R.id.menuOpciones) {
            Toast.makeText(this,"En construcción",Toast.LENGTH_LONG).show();
        }if (id==R.id.menuGuardar) {

            //Si hemos obtenido noticias las guardamos.
            ServicioSQLite servicioBD = new ServicioSQLite(this);
            if(null!=listaNoticias && listaNoticias.size()>0)
            {servicioBD.noticiasAlta(listaNoticias);}
        }
        return super.onOptionsItemSelected(item);
    }



}
