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
    private AudioManager am;
    private String LOG = "SIMON";

    public UtilityMusic() {
        super("serveiAudio");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.audio1);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int requestResult = am.requestAudioFocus(
                mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();

            Log.d(LOG, "audioFocus listener aconseguit amb èxit");

        } else if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            mp.stop();
        } else {
            Log.d(LOG, "error en la petició del listener de focus ");
        }
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
    /**
     * Classe interna que gestiona tots els canvis d'estat del AudioFocus.
     * Es tracta d'un listener per a determinats canvis d'audio
     */
    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            switch (focusChange) {
                //perdem el focus per exemple, una altre reproductor de música
                case AudioManager.AUDIOFOCUS_LOSS:
                    mp.stop();
                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS");
                    mp.release();
                    mp = null;
                    break;
                //perdem el focus temporalement, per exemple, trucada
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mp.isPlaying())
                        mp.pause();

                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT");

                    break;
                //baixem el volum temporalment
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mp.setVolume(0.5f, 0.5f);
                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;

                //es recupera el focus d'audio
                case AudioManager.AUDIOFOCUS_GAIN:
                    mp.start();
                    mp.setVolume(1.0f, 1.0f);
                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_GAIN");
                    break;

                default:
                    Log.e(LOG, "codi desconegut");
            }
        }
    };
}