package dam2.simon;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private boolean isReproduint= false;
    private Intent intent;
    protected static final String EXTRA_MISSATGE = "Home";
    Button btMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intent = new Intent(this, UtilityMusic.class);
        intent.putExtra("operacio", "inici");
        //startService(intent);
        this.btMusic  = findViewById(R.id.music);


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
