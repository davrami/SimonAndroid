package dam2.simon;

import android.content.Intent;
import android.os.AsyncTask;
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
    private TascaAsync task;


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
                if (game != null) {
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
    }

    public void gameOver(){

        int puntuacio = this.game.getPoints();
        AfegirDades(puntuacio);
        this.textGameOver.setVisibility(View.VISIBLE);
        Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
        this.game =  new Game(new Date());
        btStart.setVisibility(View.VISIBLE);
    }

    public void AfegirDades(int puntuacio){
        task = new TascaAsync();
        task.execute(puntuacio);
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


        class TascaAsync extends AsyncTask<Integer, Integer, String>
        {
            String TAG = getClass().getSimpleName();

            protected void onPreExecute (){
                super.onPreExecute();
                Log.d(TAG + " PreExceute","On pre Exceute......");
            }

            protected String doInBackground(Integer...punts) {
                Log.d(TAG + " DoINBackGround","On doInBackground...");
                int count = punts.length;
                for(int i=0; i<count; i++){
                    String nom = UtilityGame.Username;
                    DBArtistes = FirebaseDatabase.getInstance().getReference("puntuaciones");
                    if (!TextUtils.isEmpty(nom)) {

                        String id = DBArtistes.push().getKey();
                        int puntuacio = punts[i];
                                Info_Puntuacion puntuacion_act = new Info_Puntuacion(puntuacio, nom);

                        DBArtistes.child(id).setValue(puntuacion_act);

                    } else {
                        Toast.makeText(Simon.this, "Cal entrar un nom", Toast.LENGTH_LONG).show();
                    }
                    publishProgress(i);
                }

                return "You are at PostExecute";
            }

            protected void onProgressUpdate(Integer...a){
                super.onProgressUpdate(a);
                Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
            }

            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d(TAG + " onPostExecute", "" + result);
            }
        }
}
