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
import java.util.Date;
import java.util.List;

/**
 * Actividad principal. Muestra el listado de noticias obtenidos por rss.
 */
public class MainActivity extends AppCompatActivity implements AsyncResponse {


    /** Listado de noticias recuperadas.*/
    List<NoticiaObtenida> noticiasRecuperadas = new ArrayList<NoticiaObtenida>();
    /**Noticias a mostrar en pantalla, pueden ser las recuperadas o un filtro de ellas.*/
    List<NoticiaObtenida> noticiasMostrar = new ArrayList<NoticiaObtenida>();
    boolean filtro=false;
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



        i.putExtra("titulo", noticiasMostrar.get(id).getTitulo());
        //Convertimos a imagen a array de bytes
        i.putExtra("imagen", Conversor.getBytes(noticiasMostrar.get(id).getImagen()));
        i.putExtra("cuerpo", noticiasMostrar.get(id).getCuerpo());
        i.putExtra("url",noticiasMostrar.get(id).getLinkNoticia());
        startActivity(i);
    }


    /**
     * Proceso que prepara la página tras haberse recuperado asíncronamente la lista de noticias.
     * @param noticiasObtenidas
     */
    @Override
    public void processFinish(final List<NoticiaObtenida> noticiasObtenidas) {

        noticiasRecuperadas=noticiasObtenidas;

     /*   //Si hemos obtenido noticias las guardamos. Si no buscamos en bbdd las ultimas.
        ServicioSQLite servicioBD = new ServicioSQLite(this);
        if(null!=noticiasObtenidas && noticiasObtenidas.size()>0)
        {servicioBD.noticiasAlta(listaNoticias);}
        else*/
        if(null==noticiasObtenidas || noticiasObtenidas.isEmpty())
        {
            conexion=false;
            noticiasRecuperadas= servicioBD.noticiasObtenerUltimas();
          //  servicioBD.noticiasBorraTodo();
            Toast.makeText(this,R.string.alert_no_news,Toast.LENGTH_LONG).show();}
        if(!filtro)
        {noticiasMostrar.addAll(noticiasRecuperadas);}
        formaTablaNoticias(noticiasRecuperadas);


    }

    private void formaTablaNoticias(List<NoticiaObtenida> noticiasObtenidas)
    {
        tablaNoticias.removeAllViews();
        //Inicialización dinámica de la tabla de noticias
      //  tablaNoticias.setStretchAllColumns(true);
        //    tablaNoticias.bringToFront();

        for(int i = 0; i < noticiasObtenidas.size(); i++){
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
            {new DownloadImageTask (c2,noticiasObtenidas.get(i)).execute(noticiasObtenidas.get(i).getLinkImagen());
            }
            else{//imagen de bbdd
                c2.setImageBitmap(noticiasObtenidas.get(i).getImagen());
            }
            c1.setText(noticiasObtenidas.get(i).getTitulo());
            c3.setText(noticiasObtenidas.get(i).getDesc());
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



            Intent i = new Intent(this,BuscadorActivity.class);
            startActivityForResult(i, Constantes.COD_ACT_BUSCADOR);//100 identifiacará la actividad buscador

        }
        if (id==R.id.menuOpciones) {
            Toast.makeText(this,"En construcción",Toast.LENGTH_LONG).show();
        }if (id==R.id.menuGuardar) {

            //Si hemos obtenido noticias las guardamos.
            ServicioSQLite servicioBD = new ServicioSQLite(this);
            if(null!=noticiasMostrar && noticiasMostrar.size()>0)
            {servicioBD.noticiasAlta(noticiasMostrar);}
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (Constantes.COD_ACT_BUSCADOR) : {//activity buscador
                if (resultCode == Constantes.COD_FILTRO) {
                    filtro=true;
                    Bundle bundle = data.getExtras();
                    String tit=   bundle.get("titular").toString();
                    String cue =(String) bundle.get("cuerpo").toString();
                    long fec =(long)bundle.get("fechaId");
                    List<NoticiaObtenida> noticiasObtenidas=  servicioBD.noticiasObtenerBuscador(tit,cue,fec);
                    noticiasMostrar=noticiasObtenidas;
                    formaTablaNoticias(noticiasMostrar);
                }
                else
                {
                    filtro=false;
                    noticiasMostrar.clear();
                    noticiasMostrar.addAll(noticiasRecuperadas);
                    formaTablaNoticias(noticiasMostrar);

                }
                break;

            }
        }


    }



}
