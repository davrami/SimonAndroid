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

public class HomeActivity extends AppCompatActivity{
    private boolean isReproduint= false;
    private Intent intent;
    protected static final String EXTRA_MISSATGE = "Home";
    Button btMusic;
    public static final String NOM = "dam2.fje.edu.nom";
    public static final String ID = "dam2.fje.edu.id";
    List<Info_Puntuacion> artistes;
    ListView llistaArtistes;
    DatabaseReference DBArtistes;
    EditText UserNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intent = new Intent(this, UtilityMusic.class);
        intent.putExtra("operacio", "inici");
        //startService(intent);
        this.btMusic  = findViewById(R.id.music);

        TextView texto = (TextView) findViewById(R.id.textView);
        Animation animacion = AnimationUtils.loadAnimation(this,
                R.anim.animacion);
        texto.startAnimation(animacion);
        UserNom = (EditText) findViewById(R.id.UserName_Ed);
        Button btJug = (Button) findViewById(R.id.Bot_Jug);
        btJug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityGame.Username = UserNom.getText().toString();
                obrirActivity("play");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DBArtistes = FirebaseDatabase.getInstance().getReference("puntuaciones");
        llistaArtistes = (ListView) findViewById(R.id.llistatPuntuacions);
        artistes = new ArrayList<>();
        DBArtistes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistes.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Info_Puntuacion artista = postSnapshot.getValue(Info_Puntuacion.class);
                    artistes.add(artista);
                }

                Lista_Puntuacion artistaAdapter = new Lista_Puntuacion(HomeActivity.this, artistes);
                llistaArtistes.setAdapter(artistaAdapter);
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
            case R.id.play:
                obrirActivity("play");
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
