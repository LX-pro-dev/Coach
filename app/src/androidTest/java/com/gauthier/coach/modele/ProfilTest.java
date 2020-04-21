package com.gauthier.coach.modele;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandre GAUTHIER on ${date}.
 */
public class ProfilTest {//faire clic droit + run profilTest
        // crétation d'un objet profil
        private Profil profil=new Profil(,35,169,65,1);

        //resultat img
        private float img=(float)32.2;

        //message
        private String message= "Trop élevé";

        @Test
        public void getImg() throws Exception{
            assertEquals(img,profil.getImg(),(float)0.1);
            //3ème param c'est la précisions des comparaisons
            // + besoin de spécifier le type du résultat
        }

        @Test
        public void getMessage() throws Exception{
            assertEquals(message,profil.getMessage());
        }

    }


