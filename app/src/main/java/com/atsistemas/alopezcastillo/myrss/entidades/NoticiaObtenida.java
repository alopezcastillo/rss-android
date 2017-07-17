package com.atsistemas.alopezcastillo.myrss.entidades;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alopez.castillo on 07/07/2017.
 */

public class NoticiaObtenida implements Serializable {

    private int id;
    private String titulo;
    private String desc;
    private String cuerpo;
    private String linkImagen;
    private String linkNoticia;
    private Bitmap imagen;
    private Date fecha;

    public NoticiaObtenida(int id, String titulo, String desc, String cuerpo, String linkImagen, String linkNoticia, Bitmap img, Date fecha) {
        this.id =id;
        this.titulo = titulo;
        this.desc = desc;
        this.cuerpo = cuerpo;
        this.linkImagen = linkImagen;
        this.linkNoticia =linkNoticia;
        this.imagen = img;
        this.fecha =fecha;
    }
    public NoticiaObtenida(){};

    public String getLinkNoticia() {
        return linkNoticia;
    }

    public void setLinkNoticia(String linkNoticia) {
        this.linkNoticia = linkNoticia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getLinkImagen() {
        return linkImagen;
    }

    public void setLinkImagen(String linkImagen) {
        this.linkImagen = linkImagen;
    }

    public Bitmap getImagen() {  return imagen;   }

    public void setImagen(Bitmap imagen) {   this.imagen = imagen;   }

    public int getId() {    return id;   }

    public void setId(int id) {       this.id = id;    }

    public Date getFecha() {   return fecha;   }

    public void setFecha(Date fecha) {    this.fecha = fecha;   }
}
