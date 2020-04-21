package com.gauthier.coach.modele;

import androidx.annotation.NonNull;

import com.gauthier.coach.outils.MesOutils;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexandre GAUTHIER on 02/04/2020.
 */
public class Profil extends Date implements Serializable, Comparable{

    //constantes
    private static final Integer minFemme= 15; //maigre en dessous
    private static final Integer maxFemme= 30; //gros au dessus
    private static final Integer minHomme= 10; //maigre en dessous
    private static final Integer maxHomme= 25; //gros au dessus



    //propriétés
    private Date dateMesure;
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;
    private float img;
    private String message;



    //constructeur
    public Profil(Date dateMesure,Integer poids, Integer taille, Integer age, Integer sexe) {
        this.dateMesure=dateMesure;
        this.poids = poids;
        this.taille = taille;// taille en centimètres
        this.age = age;
        this.sexe = sexe;
        this.calculIMG();
        this.resultIMG();
    }

    //getters

    public Date getDateMesure() {return dateMesure;}

    public Integer getPoids() {
        return poids;
    }

    public Integer getTaille() {
        return taille;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSexe() {
        return sexe;
    }

    public float getImg() {
        return img;
    }

    public String getMessage() {
        return message;
    }

    //calcul img
    private void calculIMG() {
        float tailleM=(float)taille/100;//conversion de la taille en mètre

        this.img = (float)((1.2 * poids / (tailleM*tailleM))+(0.23 * age)-(10.83*sexe)-5.4);
    }

    //génération du msg
    private void resultIMG(){
        Integer min;
        Integer max;
        if(sexe==0){// femme
            min = minFemme;
            max=maxFemme;
        }else{//homme
            min=minHomme;
            max=maxHomme;
        }
        // message correspondant
    message="normal";
    if(img<min){
        message="trop faible";
    }
    else{
        if(img>max){
            message="trop élevé";
        }
    }

    }

    /**
     * conversion de profil au format JSONArray
     * @return
     */
    public JSONArray convertToJSONArray(){
        List laListe= new ArrayList();
        laListe.add(MesOutils.convertDateToString(dateMesure));
        laListe.add(poids);
        laListe.add(taille);
        laListe.add(age);
        laListe.add(sexe);
        return new JSONArray(laListe);
    }


    @Override
    public int compareTo(Object o) {
        return dateMesure.compareTo(((Profil)o).getDateMesure());
    }
}
