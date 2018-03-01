package dam2.simon;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;


/**
 * Clase principal amb animacions per codi
 */
public class MainActivity extends AppCompatActivity {


    protected static final String EXTRA_MISSATGE = "Simon";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String info = intent.getStringExtra(EXTRA_MISSATGE);
        System.out.println(info);

        ImageView rectTest = (ImageView) findViewById(R.id.rectTest);
        rectTest.setImageResource(R.drawable.rect_red);
        ImageView rectTest2 = (ImageView) findViewById(R.id.rectTest2);
        rectTest2.setImageResource(R.drawable.circle_green);
        ImageView rectTest3 = (ImageView) findViewById(R.id.rectTest3);
        rectTest3.setImageResource(R.drawable.triangle_yellow);
        ImageView rectTest4 = (ImageView) findViewById(R.id.rectTest4);
        rectTest4.setImageResource(R.drawable.circle_blue);
        Button btGo = (Button) findViewById(R.id.btGo);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        if(info == null){

            ObjectAnimator anim = ObjectAnimator.ofFloat(rectTest, "x", 0f, (size.x/2)-200);
            anim.setInterpolator(new BounceInterpolator());
            anim.setStartDelay(500);
            anim.setDuration(2500);
            anim.start();
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(rectTest2, "x", size.x, (size.x/2)+130);
            anim2.setStartDelay(800);
            anim2.setInterpolator(new BounceInterpolator());
            anim2.setDuration(2500);
            anim2.start();
            ObjectAnimator anim3 = ObjectAnimator.ofFloat(rectTest3, "y", size.y, (size.y/2)-30);
            anim3.setStartDelay(1000);
            anim3.setInterpolator(new BounceInterpolator());
            anim3.setDuration(2500);
            anim3.start();
            ObjectAnimator anim4 = ObjectAnimator.ofFloat(rectTest4, "y", 0, (size.y/2)-225);
            anim4.setStartDelay(1200);
            anim4.setInterpolator(new BounceInterpolator());
            anim4.setDuration(2500);
            anim4.start();

            ObjectAnimator anim5 = ObjectAnimator.ofFloat(btGo, "alpha", 0f, 1f);
            anim5.setStartDelay(1200);
            anim5.setDuration(3000);
            anim5.start();
        }else{
            ObjectAnimator anim5 = ObjectAnimator.ofFloat(btGo, "alpha", 0f, 1f);
            anim5.setDuration(0);
            anim5.start();
        }

        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obrirActivity("home");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.action_bar_menu, menu);
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
