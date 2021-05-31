package wang.com.M_tower.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import java.util.List;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.R;
import wang.com.M_tower.activity.MTBaseActivity;
import wang.com.M_tower.controller.Person;
import wang.com.M_tower.controller.ManualController;
import wang.com.M_tower.model.MonsterManual;
import wang.com.M_tower.util.ApplicationUtil;
import wang.com.M_tower.util.BitmapUtil;

public class ShowView implements IGameView {
    private ManualController manualController;
    private MapView mapView;
    private Person person;
    //声明位图对象
    private int screen_width;
    private Bitmap hp,att,def,crit,coin,exp;
    //TakingView类的构造方法 参数：基类，故事控制对象，购物控制对象，装备控制对象，楼层视图对象
    public ShowView(MTBaseActivity context,
                    ManualController manualController, MapView mapView, int width1) {
        this.manualController = manualController;
        this.mapView = mapView;
        this.screen_width = width1;
        hp = loadBitmap(context, R.drawable.hp,2.5f);
        att = loadBitmap(context, R.drawable.att,2.5f);
        def = loadBitmap(context, R.drawable.def,2.5f);
        crit = loadBitmap(context, R.drawable.crit,2.5f);
        coin = loadBitmap(context, R.drawable.gold,2.5f);
        exp = loadBitmap(context, R.drawable.exp,2.5f);
    }

    @Override
    public void onDraw(Canvas lockCanvas, Paint paint) throws InterruptedException {
        //如果装备控制器对象在屏幕上是可见的（画怪物手册）
        if(manualController.isShowing())
        {
            //获得 怪物手册 类型 的集合
            List<MonsterManual> monsterManualList = manualController.getMonsterManualList();
            ///清画布
            clear(lockCanvas);
            //画怪物手册
            drawMonsterManual(lockCanvas,monsterManualList,paint);
        }

    }

    private void clear(Canvas lockCanvas) {
        lockCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        lockCanvas.drawColor(Color.BLACK);
    }

    //画怪物手册的方法
    private void drawMonsterManual(Canvas canvas,List<MonsterManual> monsterManualList,Paint paint)
    {
        ApplicationUtil.log("屏幕高度",screen_width);
        int width = screen_width / 45;
        float left = GamePlayConstants.GameResConstants.MAP_META_WIDTH;
        float top = GamePlayConstants.GameResConstants.MAP_META_HEIGHT ;
        for(int i = 0;i < monsterManualList.size();i++) {
            if(i >= 1){
                top += width * 7f;
            }
            paint.setColor(Color.GREEN);

            paint.setTextSize(60);
            canvas.drawBitmap(mapView.getSprite(monsterManualList.get(i).getSpriteId()),
                    left,
                    top,
                    paint);
            canvas.drawText(String.valueOf(monsterManualList.get(i).getMonsterAttribute().getName()),
                    left + width * 4.5f,
                    top + width * 0.8f ,paint);

            paint.setColor(Color.YELLOW);
            canvas.drawBitmap(hp,left+width * 4.5f,
                    top + width,paint);
            canvas.drawText(String.valueOf(monsterManualList.get(i).getMonsterAttribute().getHp()),
                    left + width * 7.5f,
                    top + width * 2.9f ,paint);

            canvas.drawBitmap(att,left+width * 12.5f,
                    top + width,paint);
            canvas.drawText(String.valueOf(monsterManualList.get(i).getMonsterAttribute().getAtk()),
                    left + width * 15.5f,
                    top + width * 2.9f ,paint);

            canvas.drawBitmap(def,left+width * 20.5f,
                    top + width,paint);
            canvas.drawText(String.valueOf(monsterManualList.get(i).getMonsterAttribute().getDef()),
                    left + width * 23.5f,
                    top + width * 2.9f ,paint);

            canvas.drawBitmap(coin,left+width * 28.5f,
                    top + width,paint);
            canvas.drawText(String.valueOf(monsterManualList.get(i).getMonsterAttribute().getCoin()),
                    left + width * 31.5f,
                    top + width * 2.9f,paint);

            paint.setTextSize(65);
            paint.setColor(Color.RED);
            if(monsterManualList.get(i).getDamage() == -1){
                canvas.drawText("打不过",left + width * 36.5f,
                        top + width * 2.9f,paint);
            }else if(monsterManualList.get(i).getDamage() == 0){
                canvas.drawText("无损失",left + width * 36.5f,
                        top + width * 2.9f,paint);
            }else{
                canvas.drawText("-"+(monsterManualList.get(i).getDamage()),left + width * 36.5f,
                        top + width * 2.9f,paint);
            }
        }
    }

    //TakingView类的点击方法
    public int onClick(float x,float y) {
        int resultCode = 0;
        if(manualController.isShowing())
        {
            manualController.end();
        }
        return resultCode;
    }

    //加载图片的方法
    private Bitmap loadBitmap(MTBaseActivity context, int id, float scale)
    {
        //用图片工具下的解码方法把资源id解码后 赋值给图片对象。
        Bitmap bitmap = BitmapUtil.decode(context,id);
        //创建一个矩阵对象
        Matrix matrix = new Matrix();
        //矩阵对象调用setScale方法进行缩放变换
        matrix.setScale(GamePlayConstants.GameResConstants.MAP_META_WIDTH  / (float)bitmap.getWidth() * scale,GamePlayConstants.GameResConstants.MAP_META_HEIGHT / (float)bitmap.getHeight() * scale);

        //图片裁剪
        //bitmap：用来剪裁的图片源;x：剪裁x方向的起始位置;y：剪裁y方向的起始位置;
        //width：剪裁的宽度;height：剪裁的高度; matrix:缩放矩阵;filer：true 原图片也会被裁剪;
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    @Override
    public void onExit() {

    }
}
