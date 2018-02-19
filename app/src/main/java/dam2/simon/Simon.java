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
    private Intent intent;
    protected static final String EXTRA_MISSATGE = "Home";
    Button btMusic;
    GridView grid;
    ImageView imageRandom;
    int count;
    Timer timer;
    Button btStop;
    Button btStart;

    String[] shape = {
            "rect_blue",
            "triangle_red",
            "circle_green",

            "circle_red",
            "rect_yellow",
            "triangle_green",

            "triangle_yellow",
            "circle_blue",
            "rect_red",

            "rect_green",
            "triangle_blue",
            "circle_yellow",
    } ;
    int[] imageId = {
            R.drawable.rect_blue,
            R.drawable.triangle_red,
            R.drawable.circle_green,

            R.drawable.circle_red,
            R.drawable.rect_yellow,
            R.drawable.triangle_green,

            R.drawable.triangle_yellow,
            R.drawable.circle_blue,
            R.drawable.rect_red,

            R.drawable.rect_green,
            R.drawable.triangle_blue,
            R.drawable.circle_yellow,

    };

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
                // setContentView(R.layout.activity_help);
                break;
        }

    }
}
