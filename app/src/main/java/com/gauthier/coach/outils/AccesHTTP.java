package com.gauthier.coach.outils;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Alexandre GAUTHIER on 09/04/2020.
 */
/**
 * Classe technique de connexion distante HTTP
 */
public class AccesHTTP extends AsyncTask<String, Integer, Long> {

    // propriétés
    public String ret=""; // information retournée par le serveur
    public AsyncResponse delegate=null; // gestion du retour asynchrone pour pouvoir appeler une méthode en dehors du thread
    private String parametres = ""; // paramètres à envoyer en POST au serveur  //modif / vidéo

    /**
     * Constructeur : ne fait rien
     */
    public AccesHTTP() {
        super();
    }//modif / vidéo on instanticeait un Arraylist parametres

    /**
     * Construction de la chaîne de paramètres à envoyer en POST au serveur
     * @param nom
     * @param valeur
     */
    public void addParam(String nom, String valeur) {//modif / vidéo 10')
        try {
            if (parametres.equals("")) {
                // premier paramètre
                parametres = URLEncoder.encode(nom, "UTF-8") + "=" + URLEncoder.encode(valeur, "UTF-8");
            }else{
                // paramètres suivants (séparés par &)
                parametres += "&" + URLEncoder.encode(nom, "UTF-8") + "=" + URLEncoder.encode(valeur, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * connexion en tache de fond en thread sépraé
     * Méthode appelée par la méthode execute
     * permet d'envoyer au serveur une liste de paramètres en GET
     * @param urls contient l'adresse du serveur dans la case 0 de urls
     * @return null
     */
    @Override
    protected Long doInBackground(String... urls) {//modifié / vidéo

        // pour éliminer certaines erreurs
        System.setProperty("http.keepAlive", "false");
        // objets pour gérer la connexion, la lecture et l'écriture
        PrintWriter writer = null;
        BufferedReader reader = null;
        HttpURLConnection connexion;

        try {
            // création de l'url à partir de l'adresse reçu en paramètre, dans urls[0]
            URL url = new URL(urls[0]);
            // ouverture de la connexion
            connexion = (HttpURLConnection) url.openConnection();
            connexion.setDoOutput(true);
            // choix de la méthode POST pour l'envoi des paramètres
            connexion.setRequestMethod("POST");
            connexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connexion.setFixedLengthStreamingMode(parametres.getBytes().length);
            // création de la requete d'envoi sur la connexion, avec les paramètres
            writer = new PrintWriter(connexion.getOutputStream());
            writer.print(parametres);
            // Une fois l'envoi réalisé, vide le canal d'envoi
            writer.flush();
            // Récupération du retour du serveur
            reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
            ret = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // fermeture des canaux d'envoi et de réception
            try{
                writer.close();
            }catch(Exception e){}
            try{
                reader.close();
            }catch(Exception e){}
        }
        return null;
    }

    /**
     * Sur le retour du serveur, envoi l'information retournée à processFinish
     * @param result
     */
    @Override
    protected void onPostExecute(Long result) {
        // ret contient l'information récupérée
        delegate.processFinish(this.ret.toString());
    }

}