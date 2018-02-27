package dam2.simon;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.widget.AdapterView;

public abstract class UtilityGame {
    public static String Username ="Pepe2";
    public static String[] shape = {
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
    public static int[] imageId = {
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
    public static int[] songId = {
            R.raw.sfx,
            R.raw.sfx2,
            R.raw.sfx3,

            R.raw.sfx4,
            R.raw.sfx5,
            R.raw.sfx6,

            R.raw.sfx7,
            R.raw.sfx8,
            R.raw.sfx9,

            R.raw.sfx10,
            R.raw.sfx11,
            R.raw.sfx12
    };

    //Retorna un int random entre 12
    public static int getRandomInt(){
        return (int) (Math.random()*imageId.length);
    }

    public static String getRandomlevel(){
        return shape[getRandomInt()];
    }

    //Retorna el sonido de la forma
    public static void playSong(Context context, Integer idSong){
        MediaPlayer mp;

        mp = MediaPlayer.create(context, songId[idSong]);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp=null;
            }
        });
        mp.start();
    }

    //Reproduce sonido de la forma
    public static class PlaySong extends IntentService {
        private MediaPlayer mp;

        public PlaySong(AdapterView.OnItemClickListener onItemClickListener, long id) {
            super("serveiAudioFormes");
        }

        @Override
        public void onCreate() {
            super.onCreate();
            mp = MediaPlayer.create(this, R.raw.audio1);
        }

        @Override
        public void onStart(@Nullable Intent intent, int startId) {
            super.onStart(intent, startId);
        }

        @Override
        public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
            if (intent != null) {
                String idSonifo = intent.getStringExtra("forma");
                int index = java.util.Arrays.asList(shape).indexOf(idSonifo);
            }
            return START_NOT_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return super.onBind(intent);
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {

        }
    }

}
