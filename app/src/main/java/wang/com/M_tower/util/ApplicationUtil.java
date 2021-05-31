package wang.com.M_tower.util;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import wang.com.M_tower.activity.MTBaseActivity;

public class ApplicationUtil {
    private static List<MTBaseActivity> stack = new LinkedList<>();
    private static Toast toast = null;

    public static void add(MTBaseActivity magicTowerActivity) {
        if (!stack.contains(magicTowerActivity))
            stack.add(magicTowerActivity);
    }

    public static void remove(MTBaseActivity magicTowerActivity) {
        stack.remove(magicTowerActivity);
    }

    public static void exit() {
        for (MTBaseActivity activity : stack) {
            activity.onExit();
            activity.finish();
        }
        stack.clear();
    }

    //跳转方法，参数（两个类，一个参数）
    public static void jump(MTBaseActivity context, Class<? extends MTBaseActivity> target,int code) {
        Intent intent = new Intent(context, target);
        intent.putExtra("code", code);
        context.startActivity(intent);
        context.finish();
    }

    //静态类 log 方法
    public static <T> void log(String tag,T msg)
    {
        //输出msg，用到了字符串的 转换方法
        Log.d(tag, String.valueOf(msg));
    }

    public static void toast( MTBaseActivity context, final String msg) {
        if(toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        else{
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
