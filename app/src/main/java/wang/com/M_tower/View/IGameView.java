package wang.com.M_tower.View;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface IGameView {
    //绘画方法
    void onDraw(Canvas lockCanvas, Paint paint) throws InterruptedException;
    //退出方法
    void onExit();
}
