package com.gauthier.coach.controleur;

import android.content.Context;
import android.util.Log;

import com.gauthier.coach.modele.AccesDistant;
import com.gauthier.coach.modele.Profil;
import com.gauthier.coach.outils.Serializer;
import com.gauthier.coach.vue.CalculActivity;
import com.gauthier.coach.vue.HistoActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexandre GAUTHIER on 03/03/2020.
 */


public final class Controle {//on ne veut pas créer plus de une instance
    //singleton pattern
    private static Controle instance=null;//accessible par la classe
    private static Profil profil;
    private static String nomFichier="saveprofil";
    //private static AccesLocal accesLocal;//pr accèder à l'objet accesLocal
    private static AccesDistant accesDistant;
    private static Context contexte;
    private ArrayList<Profil> lesProfils=new ArrayList<Profil>();
    /**
     * contructeur privé
     */
    private Controle(){
        super();}//ne déclare rien, on ne pourra faire new car déclaré en private


    /**
     * création de l'instance
     * @return instance
     */
    public static final Controle getInstance(Context contexte){//au lieu de faire new pour créer le contrôleur
        if(contexte!=null){
            Controle.contexte=contexte;
        }
        if(Controle.instance==null){//si l'instance n'est pas déjà créée, on la créée
            Controle.instance= new Controle();
           // accesLocal=new AccesLocal(contexte);
            accesDistant= new AccesDistant();
            //accesDistant.envoi("dernier",new JSONArray());//demande de récupérer le dernier profil
            accesDistant.envoi("tous",new JSONArray());//demande de récupérer tous les profils
            //profil=accesLocal.recupDernier();//pour récupérer la dernière ligne de la table en SQLite
          //  recupSerialize(contexte);
        }
        return Controle.instance;
    }

    public ArrayList<Profil> getLesProfils() {
        return lesProfils;
    }

    public void setLesProfils(ArrayList<Profil> lesProfils) {
        this.lesProfils = lesProfils;
    }

    /**
     * Création du profil
     * @param poids
     * @param taille en cm
     * @param age
     * @param sexe 1 pour homme et 0 pour femme
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe, Context contexte ){// création du profil
        // Appelle de cette méthode dans Main pour obtenir les infos de profil
        Profil unProfil=new Profil(new Date(),poids,taille, age, sexe);
        lesProfils.add(unProfil);
        Log.d("date",new Date()+"*************");
        //accesLocal.ajout(profil);
        accesDistant.envoi("enreg",unProfil.convertToJSONArray());
       // Serializer.serialize(nomFichier,profil,contexte);
    }

    /**
     * pour supprimer un profil dans la bas distante et la collection
     * @param profil
     */
    public void delProfil(Profil profil){
        accesDistant.envoi("del",profil.convertToJSONArray());
        lesProfils.remove(profil);
    }

    /**
     *
     * @param profil
     */
    public void setProfil(Profil profil){
        Controle.profil=profil;
        if(contexte instanceof CalculActivity){//crash si on ne le met pas !!!
            ((CalculActivity)contexte).recupProfil();
        }
    }

    /**
     * récupération img de profil
     * @return img
     */
    public float getImg(){

        //return profil.getImg();
        return lesProfils.get(lesProfils.size()-1).getImg();
    }

    /**
     * récupération message de profil
     * @return message
     */
    public String getMessage(){
       // return profil.getMessage();
        return lesProfils.get(lesProfils.size()-1).getMessage();
    }

    /**
     * récupération de l'objet sérialisé : le profil
     * @param contexte
     */
    private static void recupSerialize(Context contexte){
        profil = (Profil)Serializer.deSerialize(nomFichier,contexte);
    }

    /**
     * récupérer le poids de l'objet sérailisé
     * @return
     */
    public Integer getPoids(){
        if(profil==null){
            return null;
        }
        return profil.getPoids();
    }

    /**
     * récupérer la taille de l'objet sérialisé
     * @return
     */
    public Integer getTaille(){
        if(profil==null){
            return null;
        }
        return profil.getTaille();
    }

    /**
     * récupérer l'age de l'objet sérialisé
     * @return
     */
    public Integer getAge(){
        if(profil==null){
            return null;
        }
        return profil.getAge();
    }

    /**
     * récupérer le sexe de l'objet sérialisé
     * @return
     */
    public Integer getSexe(){
        if(profil==null){
            return null;
        }
        return profil.getSexe();
    }


}
