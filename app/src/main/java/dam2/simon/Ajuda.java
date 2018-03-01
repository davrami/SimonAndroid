package dam2.simon;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gerard on 26/02/2018.
 */

public class Ajuda  extends AppCompatActivity implements fr1.OnFragmentInteractionListener, fr2.OnFragmentInteractionListener{
    private boolean isReproduint= false;
    private Intent intent;
    protected static final String EXTRA_MISSATGE = "Home";
    Button btMusic;
    ImageView imageHelp;
    Button afegir;
    int cont=0;
    TextView ajuda;
    MyView myView;
    ViewPager View_Fot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajuda);

        myView = new MyView(this); //Creamos la View, para hacer el Canvas

        intent = new Intent(this, UtilityMusic.class);
        intent.putExtra("operacio", "inici");
        //startService(intent);
        this.btMusic  = findViewById(R.id.music);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){//Detectamos la orientación del movil, y inicmoa el Fragment 1 o 2
            fr2 fragmento2 = new fr2();
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.contenedor, fragmento2, null)
                    .addToBackStack(null)
                    .commit();
        }else{
            fr1 fragmento1= new fr1();
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.contenedor, fragmento1, null)
                    .addToBackStack(null)
                    .commit();
        }

        View_Fot = (ViewPager) findViewById(R.id.myView);
        View_Fot.setAdapter(new PagerAdapter() {//Adaptador para el canvas

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LayoutInflater inflater = LayoutInflater.from(Ajuda.this);
                container.addView(myView);
                return myView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
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
                // setContentView(R.layout.activity_help);

                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
