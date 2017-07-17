package com.atsistemas.alopezcastillo.myrss.servicio;

import android.content.Context;

import com.atsistemas.alopezcastillo.myrss.DAO.TablaNoticias;
import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;

import java.util.List;


/**
 * Created by Alberto PC on 30/04/2017.
 */

public class ServicioSQLite {
    TablaNoticias tablaNoticias;


    public ServicioSQLite(Context cont) {
        tablaNoticias = new TablaNoticias(cont,"noticias", null, 1);

    }

    public TablaNoticias getTablaCitas() {
        return tablaNoticias;
    }


    public void noticiasAlta(List<NoticiaObtenida> listNot)
    {
        for (NoticiaObtenida not:listNot ) {
            System.out.println("LOG: almacenando en BBDD la noticia : "+not.getTitulo());
            tablaNoticias.alta(not);
        }
    }
    public void noticiasBorraTodo()
    {
        System.out.println("LOG: eliminando todas las noticias ");
        tablaNoticias.eliminarTodo();
    }

    public List<NoticiaObtenida> noticiasObtenerUltimas()
    {
        System.out.println("LOG: obteniendo 10 últimas noticias");
        List<NoticiaObtenida> retorno = tablaNoticias.consultaNoticias(10);
        return  retorno;
    }
}

