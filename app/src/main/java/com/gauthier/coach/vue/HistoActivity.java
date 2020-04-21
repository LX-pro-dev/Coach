package com.gauthier.coach.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.gauthier.coach.R;
import com.gauthier.coach.controleur.Controle;
import com.gauthier.coach.modele.Profil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoActivity extends AppCompatActivity {

    private Controle controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);
        this.controle=Controle.getInstance(this);
        ecouteRetourMenu();
        creerList();
    }

    /**
     * retour au menu
     *
     * */
    public void ecouteRetourMenu(){
        ((ImageButton)findViewById(R.id.btnRetourDeHisto)).setOnClickListener(new ImageButton.OnClickListener() {
            //pour gérer un événement sur on objet graphique
            // on recherche l'objet graphique ac R.id
            // et on applique setOnClickListener() qui redéfinie la méthode onClick(View v)

            public void onClick(View v) {
                Intent intent = new Intent(HistoActivity.this, MainActivity.class);
                //permet de ne pas garder en mémoire l'activité courante lorsque l'on ouvre une nouvelle activity
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        } );
    }

    /**
     * créer une liste adapter
     */
    private void creerList(){
        ArrayList<Profil> lesProfils= controle.getLesProfils();
        Collections.sort(lesProfils, Collections.<Profil>reverseOrder());
        if(lesProfils!=null){
            ListView lstHisto=(ListView)findViewById(R.id.lstHisto);
            HistoListAdapter adapter = new HistoListAdapter(this,lesProfils);
            lstHisto.setAdapter(adapter);
        }
    }

    /**
     * demande d'afficher le profil dans CalculActivity
     * @param profil
     */
    public void afficheProfil(Profil profil){
        controle.setProfil(profil);
        Intent intent = new Intent(HistoActivity.this, CalculActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
