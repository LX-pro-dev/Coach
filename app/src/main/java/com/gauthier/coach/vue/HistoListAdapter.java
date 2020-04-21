package com.gauthier.coach.vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gauthier.coach.R;
import com.gauthier.coach.controleur.Controle;
import com.gauthier.coach.modele.Profil;
import com.gauthier.coach.outils.MesOutils;

import java.util.ArrayList;

/**
 * Created by Alexandre GAUTHIER on 16/04/2020.
 * travaillle ligne par ligne
 */
public class HistoListAdapter extends BaseAdapter {

    private ArrayList<Profil> lesProfils;
    private LayoutInflater inflater;
    private Controle controle;
    private Context contexte;
    /**
     * Constructeur
     * @param lesProfils
     */
    public HistoListAdapter(Context contexte, ArrayList<Profil> lesProfils){
        this.lesProfils = lesProfils;
        this.inflater=LayoutInflater.from(contexte);
        this.controle=Controle.getInstance(null);
        this.contexte=contexte;
    }

    /**
     * retourne le nb de ligne de la liste
     * @return
     */
    @Override
    public int getCount() {
        return lesProfils.size();
    }

    /**
     * retourne l'item de la ligne actuelle
     * @param i = position
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesProfils.get(i);
    }

    /**
     * retourne un indice par rapport à la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * retourne la ligne (view) formaté avec gestion des événements
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //déclaration d'un holder
        ViewHolder holder;
        // si la ligne n'existe pas encore
        if(convertView==null) {
            holder = new ViewHolder();
            //la ligne est construite avec un formatage (inflater) relié à layout_list_histo
            convertView = inflater.inflate(R.layout.layout_list_histo, null);
            //chaque propriété du holder est relié à une propriété graphique
            holder.txtListDate=(TextView)convertView.findViewById(R.id.txtListeDate);
            holder.txtListIMG=(TextView)convertView.findViewById(R.id.txtListeIMG);
            holder.btnListSuppr=(ImageButton)convertView.findViewById(R.id.btnListSuppr);
            //affecter le holder à la vue
            convertView.setTag(holder);
        }else{
            //récupération du holder dans la ligne existante
            holder=(ViewHolder)convertView.getTag();
        }

        //valorisation du contenu du holder (donc de la ligne)
        holder.txtListDate.setText(MesOutils.convertDateToString(lesProfils.get(position).getDateMesure()));
        holder.txtListIMG.setText(MesOutils.format2decimal(lesProfils.get(position).getImg()));
        holder.btnListSuppr.setTag(position);
        //clic sur la croix pour supprimer le profil enregistré
        holder.btnListSuppr.setOnClickListener(new View.OnClickListener() {
            //pour gérer un événement sur on objet graphique
            // on recherche l'objet graphique ac R.id
            // et on applique setOnClickListener() qui redéfinie la méthode onClick(View v)

            public void onClick(View v) {
                int position = (int) v.getTag();
                //demande de suppression au controleur
                controle.delProfil(lesProfils.get(position));
                //rafraichir la liste
                notifyDataSetChanged();
            }

        } );
        holder.txtListDate.setTag(position);
        //clic sur le reste de la ligne pour afficher le profil enregistré
        holder.txtListDate.setOnClickListener(new View.OnClickListener() {
            //pour gérer un événement sur on objet graphique
            // on recherche l'objet graphique ac R.id
            // et on applique setOnClickListener() qui redéfinie la méthode onClick(View v)

            public void onClick(View v) {
                //récupère la position de la ligne
                int position = (int) v.getTag();
                //demande de l'affichage du profil dans CalculActivity
                ((HistoActivity)contexte).afficheProfil(lesProfils.get(position));
            }
        } );

        holder.txtListIMG.setTag(position);
        //clic sur le reste de la ligne pour afficher le profil enregistré
        holder.txtListIMG.setOnClickListener(new View.OnClickListener() {
            //pour gérer un événement sur on objet graphique
            // on recherche l'objet graphique ac R.id
            // et on applique setOnClickListener() qui redéfinie la méthode onClick(View v)

            public void onClick(View v) {
                //récupère la position de la ligne
                int position = (int) v.getTag();
                //demande de l'affichage du profil dans CalculActivity
                ((HistoActivity)contexte).afficheProfil(lesProfils.get(position));
            }
        } );
        return convertView;
    }


    private class ViewHolder{
        ImageButton btnListSuppr;
        TextView txtListDate;
        TextView txtListIMG;

    }
}
