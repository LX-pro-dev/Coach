package com.gauthier.coach.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Created by Alexandre GAUTHIER on 08/04/2020.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //propriétés
    private String creation = "create table profil("
            +"dateMesure TEXT PRIMARY KEY," +
            "poids INTEGER NOT NULL," +
            "taille INTEGER NOT NULL," +
            "age INTEGER NOT NULL," +
            "sexe INTEGER NOT NULL);";

    /**
     * consructeur
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * si changement de bdd
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creation);

    }

    /**
     * si changement de version de la bdd
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
