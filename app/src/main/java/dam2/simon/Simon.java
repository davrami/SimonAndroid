package dam2.simon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Simon extends AppCompatActivity {
    protected static final String EXTRA_MISSATGE = "Home";
    public Game game;
    GridView grid;
    ImageView imageRandom;
    public Timer timer;
    Button btStop;
    Button btStart;
    public TextView textGameOver;
    public TextView textPoints;
    DatabaseReference DBArtistes;

    String[] shape = UtilityGame.shape; //hacemos la lista estática para reutilizarla
    int[] imageId = UtilityGame.imageId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon);

        Intent intent = getIntent();
        String info = intent.getStringExtra(EXTRA_MISSATGE);
        System.out.println(info);
        this.imageRandom = (ImageView) findViewById(R.id.imageRandom);

        CustomGrid adapter = new CustomGrid(Simon.this, shape, imageId);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        this.textGameOver = (TextView) findViewById(R.id.textViewGameOver);
        this.textPoints = (TextView) findViewById(R.id.textPoints);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(game != null){
                    game.clickElement(position);
                    UtilityGame.playSong(getBaseContext(), position);
                }
            }
        });


        btStart = (Button) findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*New Game*/
                game = null;
                game = new Game(new Date());
                game.init(Simon.this);
                btStart.setVisibility(View.INVISIBLE);
                textGameOver.setVisibility(View.INVISIBLE);

            }
        });

/*
        this.count = 0;
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO get level
                        int numero = (int) (Math.random()*imageId.length);

                        if(count > 25){
                          timer.cancel();
                        }
                        imageRandom.setImageResource(imageId[numero]);
                        count++;
                    }
                });

            }
        }, 0, 2000);
*/


    }
    public void gameOver(){
        //TODO save points and reset game
        int puntuacio = this.game.getPoints();
        AfegirDades(puntuacio);
        this.textGameOver.setVisibility(View.VISIBLE);
        Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
        this.game =  new Game(new Date());
        btStart.setVisibility(View.VISIBLE);


    }

    public void AfegirDades(int puntuacio){
        String nom = UtilityGame.Username;
        DBArtistes = FirebaseDatabase.getInstance().getReference("puntuaciones");

        if (!TextUtils.isEmpty(nom)) {

            String id = DBArtistes.push().getKey();

            Info_Puntuacion artista = new Info_Puntuacion(puntuacio, nom);

            DBArtistes.child(id).setValue(artista);

            Toast.makeText(this, "Artista afegit", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Cal entrar un nom", Toast.LENGTH_LONG).show();
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
                // setContentView(R.layout.activity_help);
                break;
        }
    }
}
