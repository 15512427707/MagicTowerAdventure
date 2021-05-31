package wang.com.M_tower.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import wang.com.M_tower.R;

public class ShoppingDialog2  extends AlertDialog {
    protected ShoppingDialog2(Context context) {
        super(context);
    }

    protected ShoppingDialog2(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ShoppingDialog2(Context context, int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping2);
    }
}