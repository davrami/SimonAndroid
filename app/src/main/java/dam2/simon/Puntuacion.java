package dam2.simon;

/**
 * Created by Gerard on 26/02/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity que accedeix a una base de dades de FireBase sense autenticació
 * @author  sergi.grau@fje.edu
 * @version 1.0 25.02.2018
 */
public class Puntuacion extends AppCompatActivity {

    public static final String NOM = "dam2.fje.edu.nom";
    public static final String ID = "dam2.fje.edu.id";

    EditText nom;
    Spinner genere;
    Button afegirArtista;
    ListView llistaArtistes;

    List<Info_Puntuacion> artistes;

    DatabaseReference DBArtistes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m18_firebase);

        DBArtistes = FirebaseDatabase.getInstance().getReference("artistes");

        nom = (EditText) findViewById(R.id.editNom);
        genere = (Spinner) findViewById(R.id.generes);
        llistaArtistes = (ListView) findViewById(R.id.llistaArtistes);

        afegirArtista = (Button) findViewById(R.id.afegirArtistes);

        artistes = new ArrayList<>();

        afegirArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //afegirArtista();
            }
        });


        llistaArtistes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Info_Puntuacion artist = artistes.get(i);
                Intent intent = new Intent(getApplicationContext(), Puntuacion.class);
                intent.putExtra(ID, artist.getId());
                intent.putExtra(NOM, artist.getNom());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBArtistes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistes.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Info_Puntuacion artista = postSnapshot.getValue(Info_Puntuacion.class);
                    artistes.add(artista);
                }

                Lista_Puntuacion artistaAdapter = new Lista_Puntuacion(Puntuacion.this, artistes);
                llistaArtistes.setAdapter(artistaAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
/*
    private void afegirArtista() {
        String nom = this.nom.getText().toString().trim();
        String genere = this.genere.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nom)) {

            String id = DBArtistes.push().getKey();

            Info_Puntuacion artista = new Info_Puntuacion(id, nom);

            DBArtistes.child(id).setValue(artista);

            this.nom.setText("");

            Toast.makeText(this, "Artista afegit", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Cal entrar un nom", Toast.LENGTH_LONG).show();
        }
    }
    */
}