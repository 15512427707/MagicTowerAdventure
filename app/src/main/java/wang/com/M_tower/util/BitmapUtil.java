package wang.com.M_tower.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import wang.com.M_tower.activity.MTBaseActivity;

public class BitmapUtil {
    public static Bitmap decode(MTBaseActivity context, int id){
        //创建图片工厂类下 的 选项对象 opts（用于解码Bitmap时的各种参数控制）
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false; //表示不可以被缩放
        //返回 BitmapFactory类下的decodeResource方法，解码 基类调用getResources()方法
        return BitmapFactory.decodeResource(context.getResources(), id,opts);
    }

    //回收图片方法
    public static void recycle(Bitmap bitmap)
    {
        if(!bitmap.isRecycled())
            bitmap.recycle();
    }
}
