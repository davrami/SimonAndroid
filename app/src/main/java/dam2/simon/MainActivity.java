package dam2.simon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    protected static final String EXTRA_MISSATGE = "Simon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void obrirActivity(String view) {


        String classe = "";

        switch (view) {
            case "home":
                setContentView(R.layout.activity_main);
                Intent intentMain = new Intent(this, MainActivity.class);
                intentMain.putExtra(EXTRA_MISSATGE, "go to main activity");
                startActivity(intentMain);

                break;
            case "play":
                setContentView(R.layout.activity_simon);
                break;

            case "ayuda":
                //TODO crear vista de ayuda??
               // setContentView(R.layout.activity_help);

                break;
        }

    }
}
