package wang.com.M_tower.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.R;
import wang.com.M_tower.activity.MTBaseActivity;
import wang.com.M_tower.controller.Person;
import wang.com.M_tower.util.BitmapUtil;

public class ToolsView implements IGameView  {

    //声明位图对象
    private Bitmap book,trans,note,cross,frozen,pick,holy_water,magic_key,boom,kill_dragon;
    private Bitmap iron_sword,silver_sword,knight_sword,holy_sword,god_sword;
    private Bitmap iron_shield,silver_shield,knight_shield,holy_shield,god_shield;
    private Bitmap up_floor,down_floor;
    //声明 私有英雄控制对象
    private Person person;
    private RectF bookRect,transRect,noteRect,frozenRect,pickRect;
    private RectF holy_water_Rect,magic_key_Rect,boom_Rect;
    private RectF upRect,downRect;
    private int width3;
    //英雄状态视图类 的 构造方法。参数：基类，英雄控制器
    public ToolsView(MTBaseActivity context, Person controller, int width1)
    {
        //英雄控制对象 赋值为传入的controller
        person = controller;
        //加载道具资源
        up_floor = loadBitmap(context, R.drawable.upfloor,4f);
        down_floor = loadBitmap(context, R.drawable.downfloor,4f);
        book = loadBitmap(context, R.mipmap.book,4f);
        trans = loadBitmap(context, R.mipmap.trans,4f);
        note = loadBitmap(context, R.mipmap.note,4f);
        frozen = loadBitmap(context, R.mipmap.frozen,4f);
        pick = loadBitmap(context, R.mipmap.pick,4f);
        cross = loadBitmap(context, R.mipmap.cross,4f);
        holy_water = loadBitmap(context, R.mipmap.holy_water,4f);
        magic_key = loadBitmap(context, R.mipmap.magic_key,4f);
        boom = loadBitmap(context, R.mipmap.boom,4f);
        kill_dragon = loadBitmap(context, R.mipmap.kill_the_dragon,4f);

        //创建RectF对象
        bookRect = new RectF();
        transRect = new RectF();
        upRect = new RectF();
        downRect = new RectF();
        noteRect = new RectF();
        frozenRect = new RectF();
        pickRect = new RectF();
        holy_water_Rect = new RectF();
        magic_key_Rect = new RectF();
        boom_Rect = new RectF();

        //加载五把宝剑
        iron_sword = loadBitmap(context, R.mipmap.iron_sword,4f);
        silver_sword = loadBitmap(context, R.mipmap.silver_sword,4f);
        knight_sword = loadBitmap(context, R.mipmap.knight_sword,4f);
        holy_sword = loadBitmap(context, R.mipmap.holy_sword,4f);
        god_sword = loadBitmap(context, R.mipmap.godholy_sword,4f);

        //加载五块宝盾
        iron_shield = loadBitmap(context, R.mipmap.iron_shield,4f);
        silver_shield = loadBitmap(context, R.mipmap.silver_shield,4f);
        knight_shield = loadBitmap(context, R.mipmap.knight_shield,4f);
        holy_shield = loadBitmap(context, R.mipmap.holy_shield,4f);
        god_shield = loadBitmap(context, R.mipmap.godholy_shield,4f);
        width3 = width1;
    }

    @Override
    public void onDraw(Canvas lockCanvas, Paint paint) {
        //调用画道具方法
        drawEquipment(lockCanvas, paint);
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

    //画道具方法。
    private void drawEquipment(Canvas lockCanvas, Paint paint) {

        //设置字体大小为30
        paint.setTextSize(30);
        int width = book.getWidth();
        int width2 = (int) (width3 - width*1.2);
        //画上楼器
        if (person.personAttribute.tools[0] == true) {
            lockCanvas.drawBitmap(up_floor, width2, 0, paint);
            upRect.set(width2,0, width2 + width, width);
        }
        //画下楼器
        if (person.personAttribute.tools[1] == true) {
            lockCanvas.drawBitmap(down_floor, width2, width, paint);
            downRect.set(width2,width, width2 + width, width*2);
        }
        //画怪物之书
        if (person.personAttribute.tools[2] == true) {
            lockCanvas.drawBitmap(book, width2 - 50 - width, 0, paint);
            bookRect.set(width2 - 50 - width ,0, width2 - 50, width);
        }
        //画楼层传送器
        if (person.personAttribute.tools[3] == true) {
            lockCanvas.drawBitmap(trans, width2 - 50 - width, width, paint);
            transRect.set(width2 - 50 - width ,width, width2 - 50, width*2);
        }
        //画铁镐
        if (person.personAttribute.tools[4] == true) {
            lockCanvas.drawBitmap(pick, width2 - 100 - width*2, 0, paint);
            pickRect.set(width2 - 100  - width*3 ,0, width2 - 100  - width, width);
        }
        //画十字架
        if (person.personAttribute.tools[5] == true) {
            lockCanvas.drawBitmap(cross, width2 - 100  - width*2, width, paint);
        }
        //画冰冻徽章
        if (person.personAttribute.tools[6] == true) {
            lockCanvas.drawBitmap(frozen, width2 - 200  - width*4, 0, paint);
            frozenRect.set(width2 - 200 - width*4 ,0, width2 - 200 - width*3, width);
        }
        //画魔法钥匙
        if (person.personAttribute.tools[7] == true) {
            lockCanvas.drawBitmap(magic_key, width2 - 200 - width*4, width, paint);
            magic_key_Rect.set(width2 - 200 - width*4 ,width, width2 - 200 - width*3, width*2);
        }
        //画炸药
        if (person.personAttribute.tools[8] == true) {
            lockCanvas.drawBitmap(boom, width2 - 250 - width*5, 0, paint);
            boom_Rect.set(width2 - 250  - width*5 ,0, width2 - 250  - width*4, width);
        }
        //画屠龙匕首
        if (person.personAttribute.tools[9] == true) {
            lockCanvas.drawBitmap(kill_dragon, width2 - 250  - width*5, width, paint);
        }
        //画武器
        for(int i = 4; i >= 0;--i){
            if(person.personAttribute.swords[i] == true){
                switch (i){
                    case 4:
                        lockCanvas.drawBitmap(god_sword, width2 - 150 - width*3, 0, paint);
                        break;
                    case 3:
                        lockCanvas.drawBitmap(holy_sword, width2 - 150 - width*3, 0, paint);
                        break;
                    case 2:
                        lockCanvas.drawBitmap(knight_sword, width2 - 150 - width*3, 0, paint);
                        break;
                    case 1:
                        lockCanvas.drawBitmap(silver_sword, width2 - 150 - width*3, 0, paint);
                        break;
                    case 0:
                        lockCanvas.drawBitmap(iron_sword, width2 - 150 - width*3, 0, paint);
                        break;
                }
                break;
            }
        }
        //画防具
        for(int i = 4; i >= 0;--i){
            if(person.personAttribute.shields[i] == true){
                switch (i){
                    case 4:
                        lockCanvas.drawBitmap(god_shield, width2 - 150 - width*3, width, paint);
                        break;
                    case 3:
                        lockCanvas.drawBitmap(holy_shield, width2 - 150 - width*3, width, paint);
                        break;
                    case 2:
                        lockCanvas.drawBitmap(knight_shield, width2 - 150 - width*3, width, paint);
                        break;
                    case 1:
                        lockCanvas.drawBitmap(silver_shield, width2 - 150 - width*3, width, paint);
                        break;
                    case 0:
                        lockCanvas.drawBitmap(iron_shield, width2 - 150 - width*3, width, paint);
                        break;
                }
                break;
            }
        }
    }

    //英雄状态视图里的点击方法
    public int onClick(float x,float y)
    {
        //如果英雄控制器获得装备（道具）列表的大于为0
        if(person.personAttribute.tools[0] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(upRect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.UP_CLICK;
            }
        }
        if(person.personAttribute.tools[1] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(downRect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.DOWN_CLICK;
            }
        }
        if(person.personAttribute.tools[2] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(bookRect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.BOOK_CLICK;
            }
        }
        if(person.personAttribute.tools[3] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(transRect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.TRAN_CLICK;
            }
        }
        if(person.personAttribute.tools[4] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(pickRect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.PICK_CLICK;
            }
        }
        if(person.personAttribute.tools[6] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(frozenRect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.FROZEN_CLICK;
            }
        }
        if(person.personAttribute.tools[7] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(magic_key_Rect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.MAGIC_KEY_CLICK;
            }
        }
        if(person.personAttribute.tools[8] == true)
        {
            //如果给定的x,y包含在矩阵内
            if(boom_Rect.contains(x,y))
            {
                return GamePlayConstants.EquipmentCode.BOOM_CLICK;
            }
        }
        return 0;
    }


    @Override
    public void onExit() {
        BitmapUtil.recycle(book);
        BitmapUtil.recycle(trans);
    }

}
