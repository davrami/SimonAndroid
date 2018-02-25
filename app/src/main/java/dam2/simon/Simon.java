package dam2.simon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Simon extends AppCompatActivity {

    private boolean isReproduint= false;
    public Intent intent;
    protected static final String EXTRA_MISSATGE = "Home";
    Button btMusic;
    GridView grid;
    ImageView imageRandom;
    int count;
    public Timer timer;
    Button btStop;
    Button btStart;

    String[] shape = UtilityGame.shape; //hacemos la lista estática para reutilizarla
    int[] imageId = UtilityGame.imageId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon);

        Intent intent = getIntent();
        String info = intent.getStringExtra(EXTRA_MISSATGE);
        System.out.println(info);



        CustomGrid adapter = new CustomGrid(Simon.this, shape, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Simon.this, "You Clicked at " +shape[+ position], Toast.LENGTH_SHORT).show();
                //TODO reproducir sonido corres pondiente

                UtilityGame.playSong(getBaseContext() , String.valueOf(position));

            }
        });


        btStart = (Button)findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                       }
                                   });

        btStop = (Button) findViewById(R.id.btStop);
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
            }
        });

        this.imageRandom = (ImageView)findViewById(R.id.imageRandom);
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
