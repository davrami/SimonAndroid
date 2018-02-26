package dam2.simon;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toolbar;

/** Servei que hereta de IntentService per a crear un fil cada vegada que es crida.
 * Si es criden diversos passen a la cua*
 * */

public class UtilityMusic extends IntentService {

    private MediaPlayer mp;
    public UtilityMusic() {
        super("serveiAudio");
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
            String operacio = intent.getStringExtra("operacio");
            switch (operacio){
                case "inici" : mp.start();
                    break;
                case "pausa" : mp.pause();
                    break;
                case "salta": mp.seekTo(10000);
                    break;
                default:
                    break;
            }
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