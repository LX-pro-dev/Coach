package com.gauthier.coach.modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gauthier.coach.outils.MesOutils;
import com.gauthier.coach.outils.MySQLiteOpenHelper;

import org.w3c.dom.ProcessingInstruction;

import java.util.Date;

/**
 * Created by Alexandre GAUTHIER on 08/04/2020.
 */
public class AccesLocal {

    //propriétés
    private String nomBase="bdCoach.sqLite";//nom du fichier
    private Integer versionBase=1;
    private MySQLiteOpenHelper accesBD;// on accède à SQLite
    private SQLiteDatabase bd;//canaux pour lire ou écrire dans la BD

    /**
     * Constructeur
     */
    public AccesLocal(Context contexte){//contexte en param car MySQLiteOpenHelper a besoin d'un contexte
        accesBD= new MySQLiteOpenHelper(contexte, nomBase,null,versionBase);
    }

    /**
     * ajout d'un profil dans la BD
     * @param profil
     */
    public void ajout(Profil profil){
        bd=accesBD.getWritableDatabase();//pour écrie ds db
        String req = "insert into profil(datemesure,poids,taille,age,sexe)";
        req+="values (\""+profil.getDateMesure()+"\","+profil.getPoids()+","+profil.getTaille()+","
                +profil.getAge()+","+profil.getSexe()+")";
        //dateMesure entre " car c'est du txt et en SQL le txt est entre "
        // or dans un string pour entrer un " il faut le précéder de \
        bd.execSQL(req);
    }

    /**
     * récupération du dernier profil de la bd
     * @return
     */
    public Profil recupDernier(){
        bd= accesBD.getReadableDatabase();//pr lire ds db
        Profil profil=null;//pr stocker les infos de la dernière ligne de la db
        String req="select * from profil";
        Cursor cursor=bd.rawQuery(req,null);//cursor pr lire ligne par ligne un bd
        cursor.moveToLast();//pr se positionner sur la dernière ligne (c'est ce que l'on veut ici)
        if(!cursor.isAfterLast()){//on vérifie s'il y a bien un profil d'enregistrer ds la db
            Date date= MesOutils.convertStringToDate(cursor.getString(0));
            //on avait triché, on ne savait pas convertir une date au format TEXT  à une date au format Date en faisant un new Date();
            Integer poids=cursor.getInt(1);//mettre en param le n° de la colonne
            Integer taille=cursor.getInt(2);
            Integer age=cursor.getInt(3);
            Integer sexe=cursor.getInt(4);
            profil= new Profil(date,poids,taille, age, sexe);//création de l'objet profil
        }
        cursor.close();//fermer le cursor
        return profil;
    }
}
