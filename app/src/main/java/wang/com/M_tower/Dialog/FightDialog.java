package wang.com.M_tower.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import wang.com.M_tower.R;

public class FightDialog extends AlertDialog {
    protected FightDialog(Context context) {
        super(context);
    }

    protected FightDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public FightDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_scene);
    }

}
