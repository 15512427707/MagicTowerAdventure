package wang.com.M_tower.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import wang.com.M_tower.R;

public class ShoppingDialog1  extends AlertDialog {
    protected ShoppingDialog1(Context context) {
        super(context);
    }

    protected ShoppingDialog1(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ShoppingDialog1(Context context, int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping1);
    }
}