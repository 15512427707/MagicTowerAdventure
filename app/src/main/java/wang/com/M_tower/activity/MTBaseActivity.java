package wang.com.M_tower.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import wang.com.M_tower.util.ApplicationUtil;

public abstract class MTBaseActivity extends Activity implements IGameProcess {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用ApplicatonUtil类中的add方法。
        ApplicationUtil.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //使用ApplicatonUtil类中的remove方法。
        ApplicationUtil.remove(this);
    }
}
