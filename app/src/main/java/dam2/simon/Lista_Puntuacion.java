package dam2.simon;

import android.app.Activity;
import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Classe que controla la llista on es mostren els artistes
 * Created by sergi.grau on 25/2/18.
 */

public class Lista_Puntuacion extends ArrayAdapter<Info_Puntuacion> {
    private Activity context;
    List<Info_Puntuacion> artistes;

    public Lista_Puntuacion(Activity context, List<Info_Puntuacion> artistes) {
        super(context, R.layout.m18_llista, artistes);
        this.context = context;
        this.artistes = artistes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.m18_llista, null, true);

        TextView nom = (TextView) listViewItem.findViewById(R.id.nom);
        TextView genere = (TextView) listViewItem.findViewById(R.id.genere);

        Info_Puntuacion artist = artistes.get(position);
        nom.setText(artist.getNom());
        genere.setText("Puntuación:" + artist.getId() + " puntos");

        return listViewItem;
    }
}
