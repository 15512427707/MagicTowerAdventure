package wang.com.M_tower.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import wang.com.M_tower.R;

public class TrderDialog extends AlertDialog {

    protected TrderDialog(Context context) {
        super(context);
    }

    protected TrderDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public TrderDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    private LinearLayout cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trader);
        cnt = findViewById(R.id.trade);
        cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
