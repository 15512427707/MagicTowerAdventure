package wang.com.M_tower;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public static MediaPlayer mediaPlayer;
    @Override
    public void onStart(Intent intent,int startId){
        super.onStart(intent, startId);

        if(mediaPlayer==null){
     // R.raw.abc是资源文件，MP3格式的
            mediaPlayer = MediaPlayer.create(this, R.raw.sound6);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
    }

    @Override
    public void onDestroy() {
     // TODO Auto-generated method stub
        super.onDestroy();
        mediaPlayer.stop();
    }
}
