package dam2.simon;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Classe Entitat que representa un Artista
 * Created by sergi.grau on 25/2/18.
 */

@IgnoreExtraProperties
public class Info_Puntuacion {


    private String id;
    private String nom;
    private String genere;

    public Info_Puntuacion(){
    }

    public Info_Puntuacion(String id, String nom, String genere) {
        this.id = id;
        this.nom = nom;
        this.genere = genere;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getGenere() {
        return genere;
    }
}
