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
    private String nomBase="bdCoach.sqLite";
    private Integer versionBase=1;
    private MySQLiteOpenHelper accesBD;// on accède à SQLite
    private SQLiteDatabase bd;//canaux pour lire ou écrei dans la BD

    /**
     * Constructeur
      */
    public AccesLocal(Context contexte){
        accesBD= new MySQLiteOpenHelper(contexte, nomBase,null,versionBase);
    }

    /**
     * ajout d'un profil dans la BD
     * @param profil
     */
    public void ajout(Profil profil){
        bd=accesBD.getWritableDatabase();
        String req = "insert into profil(datemesure,poids,taille,age,sexe)";
        req+="values (\""+profil.getDateMesure()+"\","+profil.getPoids()+","+profil.getTaille()+","+profil.getAge()+","+profil.getSexe()+")";
        bd.execSQL(req);
    }

    /**
     * récupération du dernier profil de la bd
     * @return
     */
    public Profil recupDernier(){
        bd= accesBD.getReadableDatabase();
        Profil profil=null;
        String req="select * from profil";
        Cursor cursor=bd.rawQuery(req,null);
        cursor.moveToLast();
        if(!cursor.isAfterLast()){
            Date date= MesOutils.convertStringToDate(cursor.getString(0));//on triche, on ne sait pas convertir une date au fromat TEXT  à une date au format Date
            Integer poids=cursor.getInt(1);//mettre en param le n° de la colonne
            Integer taille=cursor.getInt(2);
            Integer age=cursor.getInt(3);
            Integer sexe=cursor.getInt(4);
            profil= new Profil(date,poids,taille, age, sexe);
        }
        cursor.close();
        return profil;
    }
}
