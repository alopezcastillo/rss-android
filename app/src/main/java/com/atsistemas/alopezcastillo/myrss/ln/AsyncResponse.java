package com.atsistemas.alopezcastillo.myrss.ln;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;

import java.util.List;

/**
 * Created by alopez.castillo on 12/07/2017.
 */

public interface AsyncResponse {
    void processFinish(List<NoticiaObtenida> output);
}
