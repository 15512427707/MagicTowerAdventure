package wang.com.M_tower.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import wang.com.M_tower.R;

//import android.support.v7.app.AppCompatActivity;

public class explainActivity extends MTBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain_page);
        TextView title= findViewById(R.id.title);
        TextView content= findViewById(R.id.content);
        TextView back = findViewById(R.id.back_main);
        Button bt = findViewById(R.id.back_main);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //按钮的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(explainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        title.setTypeface(Typeface.createFromAsset(explainActivity.this.getAssets(),"font/SIMYOU.TTF"));
        back.setTypeface(Typeface.createFromAsset(explainActivity.this.getAssets(),"font/SIMYOU.TTF"));
        content.setTypeface(Typeface.createFromAsset(explainActivity.this.getAssets(),"font/STXINGKA.TTF"));
    }

    @Override
    public void onExit() {

    }
}
