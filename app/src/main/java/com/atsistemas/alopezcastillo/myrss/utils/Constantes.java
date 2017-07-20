package com.atsistemas.alopezcastillo.myrss.utils;

/**
 * Created by alopez.castillo on 12/07/2017.
 */

public  class Constantes {

    // "http://www.xatakandroid.com/tag/feeds/rss2.xml"; "dd-MM-yy HH:mm:SS";
   // https://www.xatakandroid.com/tag/feeds/rss2.xml
   // https://www.xatakandroid.com/tag/rss/rss2.xmlhttps://www.xataka.com/tag/android/rss2.xml
    public static final String URL ="https://www.xatakandroid.com/tag/feeds/rss2.xml";
    // FORMATOS COMUNES
    //     "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
    //     "EEE, MMM d, ''yy" 	            Wed, Jul 4, '01
    //     "h:mm a" 	                    12:08 PM
    //     "hh 'o''clock' a, zzzz" 	        12 o'clock PM, Pacific Daylight Time
    //     "K:mm a, z" 	                    0:08 PM, PDT
    //     "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
    //     "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
    //     "yyMMddHHmmssZ" 	                010704120856-0700
    //     "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
    //     "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" 	2001-07-04T12:08:56.235-07:00
    //     "YYYY-'W'ww-u" 	                2001-W27-3
    public static final String FORMATO_FECHA ="EEE, d MMM yyyy HH:mm:ss Z";

    public static final int CONNECTION_TIMEOUT =10000;

    public static final int READ_TIMEOUT =15000;

    public static final int LIMITE_NOTICIAS=10;

    public static final int COD_FILTRO = 101;

    public static final int COD_ACT_BUSCADOR =100;

}
