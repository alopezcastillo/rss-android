package com.atsistemas.alopezcastillo.myrss.ln;

import android.util.Xml;

import com.atsistemas.alopezcastillo.myrss.entidades.NoticiaObtenida;
import com.atsistemas.alopezcastillo.myrss.utils.Constantes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alopez.castillo on 11/07/2017.
 */

public class RssParser {


    public List<NoticiaObtenida> parse(InputStream in) throws XmlPullParserException, IOException {

        List<NoticiaObtenida> retorno = new LinkedList<NoticiaObtenida>();
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        parser.setInput(in, null);
        int eventType = parser.getEventType();
        NoticiaObtenida nt= new NoticiaObtenida();

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if(eventType == XmlPullParser.START_TAG) {
                /*System.out.println("*** NAME ***"+parser.getName());

                eventType = parser.next();
                System.out.println("***** TEXT *****"+parser.getText());*/



                if (parser.getName().equals("item"))
                {
                    nt = new NoticiaObtenida();
                    System.out.println("Start tag >"+parser.getName());

                }else if(parser.getName().equals("title"))
                {
                    eventType = parser.next();
                    nt.setTitulo(parser.getText());
                    System.out.println("<title>"+parser.getText()+"</title>");
                }
                else if(parser.getName().equals("pubDate"))
                {
                    eventType = parser.next();
                    String fecha = parser.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
                    Date c= null;
                    try {
                        c = sdf.parse(fecha);
                    } catch (ParseException e) {
                        System.out.println("LOG: Error parseo fecha "+fecha);
                        c= new Date();
                        e.printStackTrace();
                    }
                    nt.setFecha(c);
                    System.out.println("<pubDate>"+parser.getText()+"</pubDate>");
                }
                else if(parser.getName().equals("link"))
                {
                    eventType = parser.next();
                    nt.setLinkNoticia(parser.getText());
                    System.out.println("<link>"+parser.getText()+"</link>");
                }else if(parser.getName().equals("description"))
                {
                    eventType = parser.next();
                    String[] p= parser.getText().split("<p>|</p>|\n");
                    for(String str :p)
                    {
                        if(!str.isEmpty())
                        {
                            if(null== nt.getLinkImagen())
                            {
                                String[] img = str.replace("\" />","").split("src=\"");
                                if(img.length>1)
                                {nt.setLinkImagen("http:".concat(img[1]));}}
                            else if(null==nt.getDesc())
                            {nt.setDesc(str);
                            nt.setCuerpo(str);}
                            else {

                                    nt.setCuerpo(nt.getCuerpo().concat(str).concat(" "));

                            }
                        }
                    }

                    System.out.println("<description>"+parser.getText()+"</description>");

                    if(nt.getCuerpo()!=null) retorno.add(nt);
                }

            }
            eventType = parser.next();
        }
        System.out.println("End document");

        return retorno;
    }
}
