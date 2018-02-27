package dam2.simon;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Classe Entitat que representa un Artista
 * Created by sergi.grau on 25/2/18.
 */

@IgnoreExtraProperties
public class Info_Puntuacion {

    private int id;
    private String nom;

    public Info_Puntuacion(){
    }

    public Info_Puntuacion(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

}
