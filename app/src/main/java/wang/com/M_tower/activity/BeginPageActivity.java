package wang.com.M_tower.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.MyService;
import wang.com.M_tower.R;
import wang.com.M_tower.util.ApplicationUtil;

//import android.support.annotation.RequiresApi;

public class BeginPageActivity extends MTBaseActivity {
    int height = 10;//高度为10
    private TextView tv_content; //开场白
    private int textHeight; //字的高度为2
    private Timer timer;
    private TimerTask timerTask;
    private int delayTime = 10;//  等待延迟
    private int priodTime = 7;//  滚动速度
    private Handler mhandler = new Handler();
    private Boolean isok = true;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_page);
        TextView fisrt = findViewById(R.id.tv_view);
        TextView f_title = findViewById(R.id.start_game);

        fisrt.setTypeface(Typeface.createFromAsset(BeginPageActivity.this.getAssets(), "font/STXINGKA.TTF"));
        f_title.setTypeface(Typeface.createFromAsset(BeginPageActivity.this.getAssets(), "font/SIMYOU.TTF"));
        //开始游戏按钮
        final Button start_game = (Button) findViewById(R.id.start_game);
        start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //按钮的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                startGame1();
                //System.exit(0);
                isok = false;
                finish();
            }
        });

        mhandler.postDelayed(new Runnable() {
            public void run() {
              //你要跳转或执行的操作
                if(isok) {
                    startGame1();
                    finish();
                }
            }
        }, 24000);
        fisrt.setEnabled(false);
        //stopTimer();
        startTimer(fisrt);
    }

    @SuppressLint("HandlerLeak")
            //处理线程
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (tv_content != null) {
                    tv_content.scrollTo(0, height + 2); //控件向上方移动
                    height = height + 2;
                } else {
                    //stopTimer();
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    //开始计时
    private void startTimer(final TextView textView) {
        tv_content = textView;  //故事文字
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());//可以支持故事文字的滑动
        textHeight = tv_content.getMeasuredHeight();//故事文字的高度

        /*textView.setOnScrollChangeListener(new View.OnScrollChangeListener() {//监听滑动状态
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (textHeight > oldScrollY) {
                    ViewGroup.LayoutParams params = tv_content.getLayoutParams();
                    params.height = textHeight - oldScrollY;
                   // tv_content.setLayoutParams(params); //设置布局
                }
            }
        });*/

        if (timerTask == null) {
            timerTask = new TimerTask() {//创建任务
                public void run() {
                    Message message = new Message();
                    message.what = 1;             //向Handler发送信息
                    handler.sendMessage(message);
                }
            };
        }

        if (timer == null) {
            timer = new Timer(true);   //设置一个新的Timer类
        }

        if (timer != null && timerTask != null) {
            timer.schedule(timerTask, delayTime, priodTime);    //启动Timer
        }
    }
   /* //停止计时
    private void stopTimer() {
        if (tv_content != null) {
            ViewGroup.LayoutParams params = tv_content.getLayoutParams();
            params.height = textHeight;
            tv_content.setLayoutParams(params);
            tv_content.scrollTo(0, 0);
        }
        height = 10;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }*/

    public void startGame1() {
        Intent intent = new Intent(BeginPageActivity.this, MyService.class);
        startService(intent);
        ApplicationUtil.jump(BeginPageActivity.this,PlayActivity.class, GamePlayConstants.GameStatusCode.NEW_GAME);
        /*Intent intent1=new Intent(BeginPageActivity.this,PlayActivity.class);
        startActivity(intent1);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    @Override
    public void onExit() {

    }
}