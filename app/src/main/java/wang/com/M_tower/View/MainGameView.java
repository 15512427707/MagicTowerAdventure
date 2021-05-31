package wang.com.M_tower.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.util.ApplicationUtil;

public class MainGameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    //SurfaceHolder接口 它是一个用于控制surface的接口，它提供了控制surface 的大小，格式，上面的像素，即监视其改变的。
    //私有 SurfaceHolder 对象 holder
    private SurfaceHolder holder;
    //私有的 画笔类对象 画笔
    private Paint paint;
    //布尔型的 playing码
    private boolean playing =true;
    //IGameView类型的集合 gameViewList
    private List<IGameView> gameViewList;

    public MainGameView(Context context) {
        super(context);
        init();
    }

    public MainGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainGameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //初始化方法
    private void init()
    {
        //创建一个画笔对象
        paint = new Paint();
        //新建IGameView类型的动态数组 gameViewList
        gameViewList = new ArrayList<>();
        //获得holder
        holder = this.getHolder();
        //holder增加回调
        holder.addCallback(this);
    }

    //运行方法
    @Override
    public void run() {
        //当正在游戏中时
        while(playing)
        {
            //获得系统时间 赋值给 beforeRender
            long beforeRender = System.currentTimeMillis();
            //调用logic方法，并没有写
            //调用绘图方法
            try {
                draw();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获得系统时间 赋值给 afterRender
            long afterRender = System.currentTimeMillis();
            long sleepTime = GamePlayConstants.GAME_LOOP_TIME - (afterRender - beforeRender);
            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //绘制方法
    private void draw() throws InterruptedException {
        //新建画布对象 并锁住他
        Canvas canvas = holder.lockCanvas();
        //若画布对象为空
        if(canvas == null)
            //直接退出
            return;
        //先清除画布的内容
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //画一个白色背景  后改为黑色背景
        canvas.drawColor(Color.BLACK);
        //增强for循环，从游戏视图列表 中 一次调用他们所重写的onDraw方法
        for(IGameView view : gameViewList)
        {
            //每一个视图的onDraw方法都不同
            view.onDraw(canvas,paint);
        }
        //画布解锁并显示在屏幕上
        holder.unlockCanvasAndPost(canvas);
    }

    //注册方法
    public void register(IGameView view) {
        //如果游戏视图集合中 不包含这个视图
        if (!gameViewList.contains(view))
            //游戏视图集合 调用add方法，把它添加进来
            gameViewList.add(view);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //在这里由start方法启动线程，同时启动run方法
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
