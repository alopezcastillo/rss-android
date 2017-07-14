/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.atsistemas.alopezcastillo.myrss.ln;

import android.os.AsyncTask;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;
import com.atsistemas.alopezcastillo.myrss.utils.Constantes;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Main Activity for the sample application.
 *
 * This activity does the following:
 *
 * o Presents a WebView screen to users. This WebView has a list of HTML links to the latest
 *   questions tagged 'android' on stackoverflow.com.
 *
 * o Parses the StackOverflow XML feed using XMLPullParser.
 *
 * o Uses AsyncTask to download and process the XML feed.
 *
 * o Monitors preferences and the device's network connection to determine whether
 *   to refresh the WebView content.
 */
public class Conexiones  extends AsyncTask  {


    public AsyncResponse delegate = null;



    @Override
    protected void onPostExecute(Object result) {
        delegate.processFinish((List<NoticiaObtenida>) result);
    }

    public InputStream urlToInputStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(Constantes.READ_TIMEOUT /* milliseconds */);
        conn.setConnectTimeout(Constantes.CONNECTION_TIMEOUT /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }



       /* @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                System.out.println(e.getCause());
                return (null);
                //   return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                System.out.println(e.getCause());
                return null;
                //    return getResources().getString(R.string.xml_error);
            }
        }
    @Override
    protected void onPostExecute(String result) {
        MainActivity
        myWebView.loadData(result, "text/html", null);
    }*/

    public List<NoticiaObtenida> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
       // Conexiones conexiones = new Conexiones();
        //  new DownloadXmlTask().execute(urlString);
        InputStream stream = null;
        stream = urlToInputStream(urlString);
        List<NoticiaObtenida> noticias = null;
        RssParser rssParser = new RssParser();
        noticias = rssParser.parse(stream);
        return noticias;
    }


    @Override
    protected Object doInBackground(Object[] params) {
        List<NoticiaObtenida>  listado = null;
        try {
             listado= loadXmlFromNetwork((String)params[0]);


        } catch (IOException e) {
            System.out.println(e.getCause());

            //   return getResources().getString(R.string.connection_error);
        } catch (XmlPullParserException e) {
            System.out.println(e.getCause());

            //    return getResources().getString(R.string.xml_error);
        }catch (Exception e) {

        System.out.println(e.getCause());

        }
        finally {
            if(listado!=null)
                System.out.println();
            return listado;
        }

    }
}
