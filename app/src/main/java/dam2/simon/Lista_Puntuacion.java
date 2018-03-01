package dam2.simon;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Classe que controla la llista on es mostren els artistes
 */

public class Lista_Puntuacion extends ArrayAdapter<Info_Puntuacion> {
    private Activity context;
    List<Info_Puntuacion> puntuaciones;

    public Lista_Puntuacion(Activity context, List<Info_Puntuacion> artistes) {
        super(context, R.layout.lista, artistes);
        this.context = context;
        this.puntuaciones = artistes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lista, null, true);

        TextView nom = (TextView) listViewItem.findViewById(R.id.nom);
        TextView puntos = (TextView) listViewItem.findViewById(R.id.puntos);

        Info_Puntuacion artist = puntuaciones.get(position);
        nom.setText(artist.getNom());
        puntos.setText("Puntuación:" + artist.getId() + " puntos");

        return listViewItem;
    }
}
