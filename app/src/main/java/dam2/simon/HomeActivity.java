package dam2.simon;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.icu.text.IDNA;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase per la vista de home activity
 * Conté animació per xml linia 64
 * Crida a Firebase per actualitzar la llista de dades de puntuacions
 */
public class HomeActivity extends AppCompatActivity{
    private boolean isReproduint= false;
    private Intent intent;
    protected static final String EXTRA_MISSATGE = "Home";
    Button btMusic;
    public static final String NOM = "dam2.fje.edu.nom";
    public static final String ID = "dam2.fje.edu.id";
    List<Info_Puntuacion> puntuaciones;
    ListView llistaPuntuacio;
    DatabaseReference DBPuntuaciones;
    EditText UserNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intent = new Intent(this, UtilityMusic.class);
        intent.putExtra("operacio", "inici");
        //startService(intent);
        this.btMusic  = findViewById(R.id.music);

        //Animación XML
        TextView texto = (TextView) findViewById(R.id.textView);
        Animation animacion = AnimationUtils.loadAnimation(this,
                R.anim.animacion);
        texto.startAnimation(animacion);

        UserNom = (EditText) findViewById(R.id.UserName_Ed);
        Button btJug = (Button) findViewById(R.id.Bot_Jug);
        btJug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Cuando hacemos clic sobre el botón y hay escrito un Nombre, iniciamos una nueva pantalla
                if(UserNom.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Escribe un nombre", Toast.LENGTH_LONG).show();
                }else{
                    UtilityGame.Username = UserNom.getText().toString();
                    obrirActivity("play");
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DBPuntuaciones = FirebaseDatabase.getInstance().getReference("puntuaciones"); //Leemos las puntucaiones guardadas en el FireBase
        llistaPuntuacio = (ListView) findViewById(R.id.llistatPuntuacions);
        puntuaciones = new ArrayList<>();
        DBPuntuaciones.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                puntuaciones.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Info_Puntuacion puntuacion_ac = postSnapshot.getValue(Info_Puntuacion.class);
                    puntuaciones.add(puntuacion_ac);
                }

                Lista_Puntuacion puntuacionAdapter = new Lista_Puntuacion(HomeActivity.this, puntuaciones);
                llistaPuntuacio.setAdapter(puntuacionAdapter); //Asignamos las puntuaciones al ListView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                obrirActivity("home");
                return true;
          
            case R.id.ayuda:
                obrirActivity("ayuda");
                return true;
            case R.id.music:
                String text;
                if (!isReproduint) {
                    text = "Pausant Audio";

                    //btMusic.setImageResource(android.R.drawable.ic_media_play);
                    this.intent.putExtra("operacio", "inici");
                    startService(intent);
                    System.out.println("inici");
                    isReproduint = !isReproduint;
                    item.setIcon(android.R.drawable.ic_lock_silent_mode_off);
                } else {
                    text = "Reproduint Audio";
                    // btMusic.setImageResource(android.R.drawable.ic_media_pause);
                    this.intent.putExtra("operacio", "pausa");
                    System.out.println("pausa");
                    startService(intent);
                    isReproduint = !isReproduint;
                    item.setIcon(android.R.drawable.ic_lock_silent_mode);

                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void obrirActivity(String view) {


        String classe = "";

        switch (view) {
            case "home":
                setContentView(R.layout.activity_home);
                Intent intentMain = new Intent(this, HomeActivity.class);
                intentMain.putExtra(EXTRA_MISSATGE, "home");
                startActivity(intentMain);
                break;
            case "play":
                setContentView(R.layout.activity_simon);
                Intent intentPlay = new Intent(this, Simon.class);
                intentPlay.putExtra(EXTRA_MISSATGE, "play");
                startActivity(intentPlay);
                break;
            case "ayuda":
                //TODO crear vista de ayuda??
                setContentView(R.layout.ajuda);
                Intent intentAjuda = new Intent(this, Ajuda.class);
                intentAjuda.putExtra(EXTRA_MISSATGE, "play");
                startActivity(intentAjuda);

                break;
        }

    }


}
