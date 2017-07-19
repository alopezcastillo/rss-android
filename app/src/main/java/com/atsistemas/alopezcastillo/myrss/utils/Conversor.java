package com.atsistemas.alopezcastillo.myrss.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.DatePicker;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alopez.castillo on 17/07/2017.
 */

public class Conversor {
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Date convierteStringFecha(String str, String formato)
    {
        Date fecha =null;
        DateFormat df = new SimpleDateFormat(formato);
        try {
            fecha= df.parse(str);
        } catch (ParseException e) {
            System.out.println("LOG: Error en el parseo de la fecha "+str);
            e.printStackTrace();
        }
        return fecha;
    }

    /**
     *
     * @param datePicker
     * @return a java.util.Date
     */
    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


}
