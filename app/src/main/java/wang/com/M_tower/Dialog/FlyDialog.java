package wang.com.M_tower.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import wang.com.M_tower.R;

public class FlyDialog  extends AlertDialog {
    private EditText editText;

    protected FlyDialog(Context context) {
        super(context);
    }

    protected FlyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public FlyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fly_floor);
        editText = findViewById(R.id.edit);
    }
}
