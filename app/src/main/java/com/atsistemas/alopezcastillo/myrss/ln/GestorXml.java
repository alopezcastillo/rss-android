package com.atsistemas.alopezcastillo.myrss.ln;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by alopez.castillo on 07/07/2017.
 */

public class GestorXml {

    public InputStream obtieneUrl(String urlString) throws IOException {

     //   new DownloadXmlTask().execute(urlString);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    /*public List<NoticiaObtenida> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
         //   parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
         //   parser.setInput(in, null);
         //   parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
*/
    /*private List<NoticiaObtenida> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<NoticiaObtenida> entries = new ArrayList<NoticiaObtenida>();

        // parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
             //   skip(parser);
            }
        }
        return entries;
    }*/


   /* public String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        Conexiones conexiones = new Conexiones();
      //  new DownloadXmlTask().execute(urlString);
        InputStream stream = null;
        stream = conexiones.urlToInputStream(urlString);
        List<NoticiaObtenida> noticias = null;
        RssParser rssParser = new RssParser();
        noticias = rssParser.parse(stream);
        return null;
    }*/

//


}
