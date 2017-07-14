package com.atsistemas.alopezcastillo.myrss.entidades;

import java.io.Serializable;

/**
 * Created by alopez.castillo on 07/07/2017.
 */

public class NoticiaObtenida implements Serializable {


    private String titulo;
    private String desc;
    private String cuerpo;
    private String linkImagen;
    private String linkNoticia;

    public NoticiaObtenida(String titulo, String desc, String cuerpo, String linkImagen, String linkNoticia) {
        this.titulo = titulo;
        this.desc = desc;
        this.cuerpo = cuerpo;
        this.linkImagen = linkImagen;
        this.linkNoticia =linkNoticia;
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

}
