package com.gauthier.coach.outils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by Alexandre GAUTHIER on 10/04/2020.
 */
public abstract class MesOutils {
    /**
     * Conversion d'une chaine de caractères sous format Tue Feb 06 09:16:17 GMT 2018 (EEE MMM dd hh:mm:ss 'GMT' yyy) vers Date
     * @param uneDate
     * @return
     */
    public static Date convertStringToDate(String uneDate) {
        return convertStringToDate(uneDate, "EEE MMM dd hh:mm:ss 'GMT' yyy");
    }

    /**
     * conversion d'une date en chaines sous la forme yyyy-MM-dd hh:mm:ss
     * @param uneDate
     * @return
     */
    public static String convertDateToString(Date uneDate){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return date.format(uneDate);
    }
    /**
     * Conversion d'une chaine de caractères sous format reçu en paramètre vers Date
     * @param uneDate
     * @return
     */
    public static Date convertStringToDate(String uneDate, String formatAttendu) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatAttendu);
        try{
            Date date= formatter.parse(uneDate);
            return date;
        }catch(ParseException e){
            Log.d("erreur","parse de la date impossible"+e.toString());
        }
        return null;
    }

    /**
     * retourne un float au format string avec 2 chiffres après la virgule
     * @param valeur
     * @return
     */
    public static String format2decimal(float valeur){
        return String.format("%.01f",valeur);
    }
}
