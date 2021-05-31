package wang.com.M_tower.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.R;
import wang.com.M_tower.activity.MTBaseActivity;
import wang.com.M_tower.activity.PlayActivity;
import wang.com.M_tower.controller.MapController;
import wang.com.M_tower.controller.Person;
import wang.com.M_tower.model.FloorMap;
import wang.com.M_tower.util.ApplicationUtil;
import wang.com.M_tower.util.BitmapUtil;

public class MapView implements IGameView {
    //声明私有型怪物图片数组 sprite
    public static Bitmap[] sprite;
    //声明私有型英雄图片数组 hero
    private Bitmap[] hero;
    //声明私有型矩阵对象 matrix
    private Matrix matrix;
    //声明楼层控制器对象
    private MapController mapController;
    //声明英雄控制器对象
    private Person person;
    public static Boolean[] array = new Boolean[500];
    public static int ShowCode;
    public static int exp;
    public static int coin;
    public static int symbol = 0;
    public static int time = 0;
    private int left;
    private float top;
    public MapView(MTBaseActivity context, MapController mapController, Person person, int width) {
        //图片工具类下的解码方法decode，把大图片解码 赋值给 Bitmap map
        Bitmap map = BitmapUtil.decode(context, R.drawable.mota3);
        //图片工具类下的解码方法decode，把大图片解码 赋值给 Bitmap player
        Bitmap player = BitmapUtil.decode(context, R.drawable.hero2);
        Arrays.fill(array, Boolean.FALSE);
        //创建一个矩阵，并设置比例尺
        matrix = new Matrix();
        matrix.setScale(//sx:屏幕宽度 / 地图宽度 / 元素块的宽度
                ((float)width / GamePlayConstants.MAP_WIDTH) / GamePlayConstants.GameResConstants.MAP_META_WIDTH,
                //sy:屏幕宽度 / 地图宽度 / 元素块的高度
                (float) (((float)width / GamePlayConstants.MAP_WIDTH) / GamePlayConstants.GameResConstants.MAP_META_WIDTH * 1.1));

        //新建怪物图片数组（大小等于 资源图 宽*高）
        sprite = new Bitmap[GamePlayConstants.GameResConstants.MAP_SPRITE_HEIGHT_COUNT *
                GamePlayConstants.GameResConstants.MAP_SPRITE_WIDTH_COUNT];

        //新建hero图片数组（大小等于 英雄图 宽*高）
        hero = new Bitmap[GamePlayConstants.GameResConstants.HERO_SPRITE_HEIGHT_COUNT*
                GamePlayConstants.GameResConstants.HERO_SPRITE_WIDTH_COUNT];

        //两个for循环，按行列，把怪物图片分解成小块赋值给sprite数组
        for(int i = 0; i < GamePlayConstants.GameResConstants.MAP_SPRITE_HEIGHT_COUNT; i++)
        {
            for(int j = 0; j < GamePlayConstants.GameResConstants.MAP_SPRITE_WIDTH_COUNT; j++)
            {
                sprite[i * GamePlayConstants.GameResConstants.MAP_SPRITE_WIDTH_COUNT + j] =
                        Bitmap.createBitmap(map,
                                j * GamePlayConstants.GameResConstants.MAP_META_WIDTH,
                                i * GamePlayConstants.GameResConstants.MAP_META_HEIGHT,
                                GamePlayConstants.GameResConstants.MAP_META_WIDTH,
                                GamePlayConstants.GameResConstants.MAP_META_HEIGHT,
                                matrix,true);
            }
        }

        //for循环，把player英雄图片  给分解成一小块一小块赋值给hero数组
        for(int i = 0;i < GamePlayConstants.GameResConstants.HERO_SPRITE_HEIGHT_COUNT;i++)
        {
            for(int j = 0;j < GamePlayConstants.GameResConstants.HERO_SPRITE_WIDTH_COUNT;j++) {
                hero[i * GamePlayConstants.GameResConstants.HERO_SPRITE_WIDTH_COUNT + j] =
                        Bitmap.createBitmap(player,
                                j * GamePlayConstants.GameResConstants.MAP_META_WIDTH,
                                i * GamePlayConstants.GameResConstants.MAP_META_HEIGHT,
                                GamePlayConstants.GameResConstants.MAP_META_WIDTH,
                                GamePlayConstants.GameResConstants.MAP_META_HEIGHT,
                                matrix, true);
            }
        }
        //回收这两张图片
        map.recycle();
        player.recycle();
        //小怪物图片的宽度赋值给 MAP_SPRITE_FINAL_WIDTH
        GamePlayConstants.GameResConstants.MAP_SPRITE_FINAL_WIDTH = sprite[0].getWidth();
        this.mapController = mapController;
        this.person = person;
    }

    //获得怪物图片方法
    public Bitmap getSprite(int value)
    {   //返回第value个图片
        return sprite[value];
    }

    //获得怪物宽度
    public int getSpriteWidth()
    {
        //返回每一张怪物图片的宽度
        return sprite[0].getWidth();
    }
    @Override
    public void onDraw(Canvas lockCanvas, Paint paint) throws InterruptedException {
        //首先 获取楼层地图 并赋值给 map
        FloorMap map = mapController.getMap();

        //行列双层for循环
        for (int i = 0; i < map.getMap().length / GamePlayConstants.MAP_WIDTH; i++) {
            for (int j = 0; j < GamePlayConstants.MAP_WIDTH; j++) {
                //定义int型的宽度 等于 画布的宽度/一行中所含元素快块的个数
                int width = lockCanvas.getWidth() / GamePlayConstants.MAP_WIDTH;

                //？？？设置x,y轴方向上的平移大小
                matrix.setTranslate(
                        ((float) lockCanvas.getWidth() / GamePlayConstants.MAP_WIDTH) / GamePlayConstants.GameResConstants.MAP_META_WIDTH,
                        (float) (((float) lockCanvas.getWidth() / GamePlayConstants.MAP_WIDTH) / GamePlayConstants.GameResConstants.MAP_META_HEIGHT  + 260));
                matrix.postTranslate(j * width, (float) (i * width * 1.1));
                if(time == 0) {
                    left = j * width;
                    if (j == 10 || j == 9) {
                        left = (j - 3) * width;
                    }
                    top = (float) (i * width * 1.1) + 400;
                }
                if(map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] >= GamePlayConstants.GameValueConstants.HERO_BEGIN
                        && map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] <= GamePlayConstants.GameValueConstants.HERO_END){
                    if (ShowCode != -1) {

                        paint.setColor(Color.YELLOW);
                        paint.setTextSize(80);
                        time++;
                        if(time == 20){
                            ShowCode = -1;
                            time = 0;
                        }
                        switch (ShowCode) {
                            case GamePlayConstants.GameValueConstants.YELLOW_KEY:
                                lockCanvas.drawText("黄钥匙 + 1", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.BLUE_KEY:
                                lockCanvas.drawText("蓝钥匙 + 1", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.RED_KEY:
                                lockCanvas.drawText("红钥匙 + 1", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.LITTLE_BLOOD:
                                paint.setColor(Color.GREEN);
                                lockCanvas.drawText("hp + 200", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.BIG_BLOOD:
                                paint.setColor(Color.GREEN);
                                lockCanvas.drawText("hp + 500", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.ATTACK_BUFF:
                                lockCanvas.drawText("攻击力 + 4", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.DEFENSE_BUFF:
                                lockCanvas.drawText("防御力 + 4", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.CRIT_BUFF:
                                lockCanvas.drawText("增伤 + 0.1", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.LITTLE_UPDATE_WING:
                                lockCanvas.drawText("等级 + 1", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.MIDDLE_UPDATE_WING:
                                lockCanvas.drawText("等级 + 3!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.SUPER_UPDATE_WING:
                                lockCanvas.drawText("等级 + 10!!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.LUCK_GOLD_COIN:
                                lockCanvas.drawText("金币 + 100", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.SUPER_GOLD_COIN:
                                lockCanvas.drawText("金币 + 800!!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.IRON_SWORD:
                                lockCanvas.drawText("攻击力 + 20", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.SILVER_SWORD:
                                lockCanvas.drawText("攻击力 + 80", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.KNIGHT_SWORD:
                                lockCanvas.drawText("攻击力 + 300!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.GRM_SWORD:
                                paint.setTextSize(80);
                                lockCanvas.drawText("攻击力 + 1000!!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.HOLY_SWORD:
                                paint.setTextSize(80);
                                lockCanvas.drawText("攻击力 + 3000!!!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.IRON_SHIELD:
                                lockCanvas.drawText("防御力 + 20", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.SILVER_SHIELD:
                                lockCanvas.drawText("防御力 + 80", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.KNIGHT_SHIELD:
                                lockCanvas.drawText("防御力 + 300", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.GRM_SHIELD:
                                paint.setTextSize(80);
                                lockCanvas.drawText("防御力 + 1000!!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.HOLY_SHIELD:
                                paint.setTextSize(80);
                                lockCanvas.drawText("防御力 + 3000!!!", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.FIRE_GROUND:
                                lockCanvas.drawText("生命-100", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.MoveStatusCode.FIGHT_SUCCESS:
                                paint.setTextSize(80);
                                paint.setColor(Color.YELLOW);
                                lockCanvas.drawText("战斗胜利！", left, top - time*20, paint);
                                paint.setColor(Color.WHITE);
                                paint.setTextSize(80);
                                lockCanvas.drawText("经验+" + exp + " !金币+" + coin+"!", left - 100, top + 100 - time*20, paint);
                                break;
                            case GamePlayConstants.MoneyCode.ONE:
                                lockCanvas.drawText("你被天神附体了！", left, top - time*20, paint);
                                lockCanvas.drawText("攻击力+500！", left, top + 100- time*20, paint);
                                lockCanvas.drawText("防御力+500！", left, top + 200 - time*20, paint);
                                break;
                            case GamePlayConstants.GameValueConstants.NPC_MAIDEN:
                                lockCanvas.drawText("你得到了仙子的提升！", left, top - time*20, paint);
                                lockCanvas.drawText("攻击力+1000！", left, top + 100- time*20, paint);
                                lockCanvas.drawText("防御力+1000！", left, top + 200 - time*20, paint);
                                lockCanvas.drawText("增伤 + 0.5！", left, top + 300 - time*20, paint);
                                break;
                            case GamePlayConstants.MoneyCode.TWO:
                                lockCanvas.drawText("你的运气太棒了！", left, top - time*20, paint);
                                lockCanvas.drawText("金币+1000！", left, top + 100 - time*20, paint);
                                break;
                            case GamePlayConstants.MoneyCode.THREE:
                                lockCanvas.drawText("恭喜你，等级 + 3！", left, top - time*20, paint);
                                break;
                            case GamePlayConstants.MoneyCode.FOUR:
                                lockCanvas.drawText("攻击力+6", left, top - time*20, paint);
                                lockCanvas.drawText("防御力+6", left, top + 100 - time*20, paint);
                                break;
                            case GamePlayConstants.MoneyCode.FIVE:
                                lockCanvas.drawText("攻击力+3", left, top - time*20, paint);
                                lockCanvas.drawText("防御力+3", left, top + 100 - time*20, paint);
                                break;
                            case GamePlayConstants.MoneyCode.SIX:
                                lockCanvas.drawText("等级 + 1", left, top - time*20, paint);
                                break;
                        }
                        //Thread.sleep(1000);
                        if(frame == 0) {
                            ShowCode = -1;
                        }
                    }
                }


                //如果是怪物，就调用 绘制怪物动画 方法
                if (map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN
                        && map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] <= GamePlayConstants.GameValueConstants.MONSTER_ID_END)
                {
                    if(PlayActivity.isFighting != true || MapView.array[i * GamePlayConstants.MAP_WIDTH + j] == Boolean.FALSE) {
                        drawSpriteAnimation(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j], paint);
                    }else if(MapView.array[i * GamePlayConstants.MAP_WIDTH + j] == Boolean.TRUE){
                        ApplicationUtil.log("号", MapView.array[i * GamePlayConstants.MAP_WIDTH + j]);
                        drawfightSpriteAnimation(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j], paint);
                    }

                }//如果接下来的图片号是 动态图画，那么就调用 绘制场景动画 方法
                else if ((map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] >= GamePlayConstants.GameValueConstants.DYNAMIC_BEGIN &&
                            map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] <= GamePlayConstants.GameValueConstants.DYNAMIC_END)
                        || map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] == 277) {
                    drawSceneAnimation(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j], paint);
                }//如果接下来的图片是npc的图片 就画NPC
                else if (map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] >= GamePlayConstants.GameValueConstants.NPC_BEGIN &&
                        map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] <= GamePlayConstants.GameValueConstants.NPC_END) {
                    drawSceneAnimation(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j], paint);
                }//如果都不是，调用 绘制元素 方法
                else if (map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] >= GamePlayConstants.GameValueConstants.STATIC_BEGIN &&
                        map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] <= GamePlayConstants.GameValueConstants.STATIC_END) {
                    drawElement(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j], paint);
                }
                else if(map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] >= GamePlayConstants.GameValueConstants.HERO_BEGIN
                        && map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] <= GamePlayConstants.GameValueConstants.HERO_END){
                    //都不是，先画一个地板，再画英雄
                    if(PlayActivity.isFighting != true) {
                        try {
                            if(symbol == 0){
                                drawElement(lockCanvas, GamePlayConstants.GameValueConstants.GROUND, paint);
                                drawPerson(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] - 225, paint);
                            }else if(symbol == 1){
                                drawSceneAnimation(lockCanvas, GamePlayConstants.GameValueConstants.FIRE_GROUND, paint);
                                drawPerson(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] - 225, paint);
                            }
                        }catch (ArrayIndexOutOfBoundsException e){
                            ApplicationUtil.log("刷新异常","xx");
                        }
                    }else {
                        try {
                            drawElement(lockCanvas, GamePlayConstants.GameValueConstants.GROUND, paint);
                            drawfightPerson(lockCanvas, map.getMap()[i * GamePlayConstants.MAP_WIDTH + j] - 225, paint);
                        }catch (ArrayIndexOutOfBoundsException e){
                            ApplicationUtil.log("刷新异常","xx");
                        }
                    }
                }
            }
        }
    }

    private int frame = 0;
    //画怪物场景
    private void drawSpriteAnimation(Canvas canvas,int id,Paint paint) {
        if (frame / 50 == 0) {
            canvas.drawBitmap(sprite[id], matrix, paint);
        } else {
            canvas.drawBitmap(sprite[id + 45], matrix, paint);
            if (frame / 50 == 2)
                frame = 0;
        }
        frame++;
    }
    //画怪物战斗时的场景
    private void drawfightSpriteAnimation(Canvas canvas,int id,Paint paint) {
        try{
            if (frame / 50 == 0) {
                canvas.drawBitmap(sprite[id], matrix, paint);
            } else if(frame / 50 == 1) {
                canvas.drawBitmap(sprite[id + 100], matrix, paint);
            } else if(frame / 50 == 2) {
                canvas.drawBitmap(sprite[id + 45], matrix, paint);
            } else if(frame / 50 == 3) {
                canvas.drawBitmap(sprite[id + 100 + 45], matrix, paint);
            }
            else if (frame / 50 == 4){
                frame = 0;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            ApplicationUtil.log("出现越界","异常");
        }

        frame++;
    }

    //画静态场景
    public void drawElement(Canvas canvas,int id,Paint paint) {
        //canvas中封装的画图方法。参数（怪物图片数组，定义好的矩阵，画笔）
        canvas.drawBitmap(sprite[id], matrix, paint);
    }

    //画英雄
    public void drawPerson(Canvas canvas, int id, Paint paint) {
        canvas.drawBitmap(hero[id], matrix, paint);
    }

    //画英雄战斗
    public void drawfightPerson(Canvas canvas, int id, Paint paint) {
        if (frame / 50 == 0) {
            canvas.drawBitmap(hero[id], matrix, paint);
        } else {
            canvas.drawBitmap(hero[id + 16], matrix, paint);
            if (frame / 50 == 2)
                frame = 0;
        }
        frame++;
    }

    //画动态场景
    public void drawSceneAnimation(Canvas canvas,int id,Paint paint)
    {
        if (frame / 50 == 0) {
            canvas.drawBitmap(sprite[id], matrix, paint);
        } else {
            canvas.drawBitmap(sprite[id + 1], matrix, paint);
            if (frame / 50 == 2)
                frame = 0;
        }
        frame++;
    }

    @Override
    public void onExit() {
        for(Bitmap bitmap : sprite){
            recycle(bitmap);
        }
    }

    //回收图片的方法
    private void recycle(@NotNull Bitmap bitmap)
    {
        //如果图片还没有被回收
        if(!bitmap.isRecycled())
            //回收它
            bitmap.recycle();
    }
}
