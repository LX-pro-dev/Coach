package com.gauthier.coach.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gauthier.coach.R;
import com.gauthier.coach.controleur.Controle;
import com.gauthier.coach.outils.MesOutils;

public class CalculActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);
        init();
    }

    // propriétés pour manipuler les objets graphiques du xml
    private EditText txtPoids;
    private EditText txtAge;
    private EditText txtTaille;
    private RadioButton rbHomme;
    private RadioButton rbFemme;
    private ImageView imgSmiley;
    private TextView lblIMG;
    private Controle controle;

    /**
     * initialisation des liens entre les propriétés et les objets graphiques
     */
    private void init(){
        txtPoids=(EditText)findViewById(R.id.txtPoids);
        txtAge=(EditText)findViewById(R.id.txtAge);
        txtTaille=(EditText)findViewById(R.id.txtTaille);
        rbHomme=(RadioButton)findViewById(R.id.rbHomme);
        rbFemme=(RadioButton)findViewById(R.id.rbFemme);
        imgSmiley=(ImageView)findViewById(R.id.imgSmiley);
        lblIMG=(TextView)findViewById(R.id.lblIMG);
        this.controle=Controle.getInstance(this);//création de l'instance controle (singleton)
        ecouteCalcul();
        ecouteRetourMenu();
        recupProfil();
    }

    /**
     * écoute l'événement sur le bouton calcul
     */
    private void ecouteCalcul(){
        ((Button)findViewById(R.id.btnCalc)).setOnClickListener(new Button.OnClickListener(){
            //pour gérer un événement sur on objet graphique
            // on recherche l'objet graphique ac R.id
            // et on applique setOnClickListener() qui redéfinie la méthode onClick(View v)

            public void onClick(View v){
                //Toast.makeText(MainActivity.this,"test",Toast.LENGTH_LONG).show();
                Integer poids = 0;
                Integer taille= 0;
                Integer age= 0;
                Integer sexe= 0;

                //récupération des données saisies
                try {// pour éviter les pb de saisie : char au lieu d'un int
                    poids = Integer.parseInt(txtPoids.getText().toString());
                    taille = Integer.parseInt(txtTaille.getText().toString());
                    age = Integer.parseInt(txtAge.getText().toString());
                }catch(Exception e){ }//l'exception ne fera rien
                // comme on a initailisé les variables à 0 on va tester si elles sont restées à 0!

                if(rbHomme.isChecked()){//test pour un bouton radio
                    sexe=1;
                }

                //controle des données saisies
                if(poids==0 || taille==0 || age==0){
                    Toast.makeText(CalculActivity.this,"saisie incorrecte",Toast.LENGTH_LONG).show();
                }else{
                  afficheResult(poids, taille, age, sexe);
                }

            }
        });
    }

    /**
     * retour au menu
     *
     * */
    public void ecouteRetourMenu(){
        ((ImageButton)findViewById(R.id.btnRetourDeCalcul)).setOnClickListener(new ImageButton.OnClickListener() {
            //pour gérer un événement sur on objet graphique
            // on recherche l'objet graphique ac R.id
            // et on applique setOnClickListener() qui redéfinie la méthode onClick(View v)

            public void onClick(View v) {
                Intent intent = new Intent(CalculActivity.this, MainActivity.class);
                //permet de ne pas garder en mémoire l'activité courante lorsque l'on ouvre une nouvelle activity
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        } );
    }

    /**
     * affichage de l'IMG, du Message et de l'image
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    private void afficheResult(Integer poids, Integer taille, Integer age, Integer sexe){
        //création du profil et récupération des infos
        this.controle.creerProfil(poids,taille,age,sexe,this);
        float img=this.controle.getImg();
        String message=this.controle.getMessage();

        //gestion de l'affichage (image et couleur du message)
        if(message=="normal"){
            lblIMG.setTextColor(Color.GREEN);
            imgSmiley.setImageResource(R.drawable.m_heureux);
        }else {
            lblIMG.setTextColor(Color.RED);
            if(message == "trop faible"){
                imgSmiley.setImageResource(R.drawable.m_maigre);
            }else{
                imgSmiley.setImageResource(R.drawable.m_glouton);
            }
        }
        lblIMG.setText(MesOutils.format2decimal(img)+ " : IMG "+message);
    }

    /**
     * récupération du profil s'il a été sérialisé
     */
    public void recupProfil(){
        if(controle.getPoids()!=null){
            txtPoids.setText(controle.getPoids().toString());
            txtTaille.setText(controle.getTaille().toString());
            txtAge.setText(controle.getAge().toString());
            rbFemme.setChecked(true);
            if(controle.getSexe()==1){
                rbHomme.setChecked(true);
            }
            //remettre à vide le profil
            controle.setProfil(null);
            //simulation du click sur le bouton calcul// le pb c'est qu'avec SQLite on enregistre à nouveau le dernier profil enregistré
           // ((Button)findViewById(R.id.btnCalc)).performClick();
        }
    }
}
