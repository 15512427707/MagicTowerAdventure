package wang.com.M_tower.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import wang.com.M_tower.Dialog.ReadDialog;
import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.MyService;
import wang.com.M_tower.R;
import wang.com.M_tower.util.ApplicationUtil;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends MTBaseActivity {
    private Button start;
    private Button exit;
    private Button explain;
    private Button save;
    ReadDialog readDialog;
    public static SoundPool soundPool = new SoundPool(11, AudioManager.STREAM_MUSIC, 5);
    public static HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainmenu);
        readDialog = new ReadDialog(this,R.style.dialog);
        start = findViewById(R.id.start);
        exit = findViewById(R.id.exit);
        explain = findViewById(R.id.explain);
        save = findViewById(R.id.save);
        soundMap.put(0, soundPool.load(getApplicationContext(),R.raw.blood, 1));
        soundMap.put(1, soundPool.load(getApplicationContext(),R.raw.button_1, 1));
        soundMap.put(2, soundPool.load(getApplicationContext(), R.raw.collapse_1, 1));
        soundMap.put(3, soundPool.load(getApplicationContext(),R.raw.item_1, 1));
        soundMap.put(4, soundPool.load(getApplicationContext(),R.raw.item_2, 1));
        soundMap.put(5, soundPool.load(getApplicationContext(), R.raw.sword, 1));
        soundMap.put(6, soundPool.load(getApplicationContext(),R.raw.sword_heavy, 1));
        soundMap.put(7, soundPool.load(getApplicationContext(), R.raw.blood_heavy, 1));
        soundMap.put(8, soundPool.load(getApplicationContext(), R.raw.fight, 1));
        soundMap.put(9, soundPool.load(getApplicationContext(), R.raw.stair, 1));
        soundMap.put(10, soundPool.load(getApplicationContext(), R.raw.update, 1));

        //开始游戏
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //按钮的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BeginPageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //读取游戏
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //按钮的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);
                ApplicationUtil.jump(MainActivity.this,PlayActivity.class, GamePlayConstants.GameStatusCode.LOAD_GAME);
            }
        });
        //游戏说明
        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //按钮的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,explainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //退出游戏
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //按钮的声音
                //按钮的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                System.exit(0);
            }
        });
        TextView textView = findViewById(R.id.name);
        textView.setTypeface(Typeface.createFromAsset(MainActivity.this.getAssets(), "font/SIMYOU.TTF"));
    }


    @Override
    public void onExit() {
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }


}
