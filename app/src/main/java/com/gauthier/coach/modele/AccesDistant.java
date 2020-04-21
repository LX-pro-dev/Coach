package com.gauthier.coach.modele;

import android.util.Log;

import com.gauthier.coach.controleur.Controle;
import com.gauthier.coach.outils.AccesHTTP;
import com.gauthier.coach.outils.AsyncResponse;
import com.gauthier.coach.outils.MesOutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexandre GAUTHIER on 09/04/2020.
 */
public class AccesDistant implements AsyncResponse {
    //constante
    private static final String SERVERADDR = "http://192.168.1.6/coach/serveurcoach.php";//au lieu de /coach/serveurcoach.php"
    private Controle controle;
    /**
     * constructeur
     */
    public AccesDistant() {
        controle=Controle.getInstance(null);
    }

    /**
     * retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        Log.d("serveur", output + "************");
        //découper le message reçu avec un %
        String[] message = output.split("%");
        //dans message[0] : "enreg", "dernier", "erreur"
        //dans message[1] : le reste du message

        //s'il y a 2 cases
        if (message.length > 1) {
            if (message[0].equals("enreg")) {
                Log.d("enreg", message[1] + "******************");
            } else {
                if (message[0].equals("dernier")) {
                    Log.d("dernier", message[1] + "******************");
                    try {
                        JSONObject info= new JSONObject(message[1]);
                        Integer poids= info.getInt("poids");
                        Integer taille= info.getInt("taille");
                        Integer age= info.getInt("age");
                        Integer sexe= info.getInt("sexe");
                        String datemesure= info.getString("datemesure");
                        Date date= MesOutils.convertStringToDate(datemesure,"yyyy-MM-dd hh:mm:ss");
                        Log.d("date mysql", "retour mysql"+date+"****************");
                        Profil profil= new Profil(date,poids,taille,age, sexe);
                        controle.setProfil(profil);
                    } catch (JSONException e) {
                        Log.d("erreur", "converssion JSON impossible"+ e.toString()+ "******************");
                    }
                } else {
                    if (message[0].equals("tous")) {
                        Log.d("tous", message[1] + "******************");
                        try {
                            JSONArray jsonInfo = new JSONArray(message[1]);
                            ArrayList<Profil> lesProfils = new ArrayList<Profil>();
                            for(int i=0;i<jsonInfo.length();i++){
                                JSONObject info= new JSONObject(jsonInfo.get(i).toString());
                                Integer poids= info.getInt("poids");
                                Integer taille= info.getInt("taille");
                                Integer age= info.getInt("age");
                                Integer sexe= info.getInt("sexe");
                                String datemesure= info.getString("datemesure");
                                Date date= MesOutils.convertStringToDate(datemesure,"yyyy-MM-dd hh:mm:ss");
                                Log.d("date mysql", "retour mysql"+date+"****************");
                                Profil profil= new Profil(date,poids,taille,age, sexe);
                                lesProfils.add(profil);
                            }
                            controle.setLesProfils(lesProfils);
                        } catch (JSONException e) {
                            Log.d("erreur", "converssion JSON impossible"+ e.toString()+ "******************");
                        }
                    }else{
                        if (message[0].equals("erreur")) {
                        Log.d("erreur", message[1] + "******************");
                        }
                    }
                }
            }
        }
    }

    public void envoi(String operation, JSONArray lesDonneesJSON) {
        AccesHTTP accesDonnees= new AccesHTTP();

        //lien de ldélégation
        accesDonnees.delegate = this;

        //ajout paramètres
        accesDonnees.addParam("operation",operation);
        accesDonnees.addParam("lesdonnees",lesDonneesJSON.toString());

        accesDonnees.execute(SERVERADDR);
    }
}
