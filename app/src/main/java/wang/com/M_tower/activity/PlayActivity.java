package wang.com.M_tower.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wang.com.M_tower.Dialog.FightDialog;
import wang.com.M_tower.Dialog.FlyDialog;
import wang.com.M_tower.Dialog.ReadDialog;
import wang.com.M_tower.Dialog.SaveDialog;
import wang.com.M_tower.Dialog.ShoppingDialog;
import wang.com.M_tower.Dialog.ShoppingDialog1;
import wang.com.M_tower.Dialog.ShoppingDialog2;
import wang.com.M_tower.Dialog.TrderDialog;
import wang.com.M_tower.GameManager;
import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.MyService;
import wang.com.M_tower.R;
import wang.com.M_tower.Dialog.TalkDialog;
import wang.com.M_tower.View.MapView;
import wang.com.M_tower.View.MainGameView;
import wang.com.M_tower.View.ShowView;
import wang.com.M_tower.View.ToolsView;
import wang.com.M_tower.controller.MapController;
import wang.com.M_tower.controller.Person;
import wang.com.M_tower.controller.ManualController;
import wang.com.M_tower.model.FloorMap;
import wang.com.M_tower.model.PersonAttribute;
import wang.com.M_tower.util.ApplicationUtil;

public class PlayActivity extends MTBaseActivity{
    private MainGameView mainGameView;
    private MapController mapController;
    private Person person;
    private ManualController manualController;
    private MapView mapView;
    private ToolsView toolsView;
    private ShowView showView;
    //声明菜单按钮和上下左右四个按钮
    private Button menu;
    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;
    //声明各种信号量
    private Boolean ispress;
    private int mutex;
    private int isgoing;
    private int touchmutex;
    private int signalup;
    private int signaldown;
    private int signalleft;
    private int signalright;
    //声明人物的基本信息
    private int hp;
    private int atk;
    private int def;
    private int yellowKey;
    private int blueKey;
    private int redKey;
    private int coin;
    private int exp;
    private float addDamage;
    private int curLevel;
    private int lv;
    //声明主界面布局要素
    private TextView thp;
    private TextView tatk;
    private TextView tdef;
    private TextView taddDamage;
    private TextView tyellowkey;
    private TextView tbluekey;
    private TextView tredkey;
    private TextView tcoin;
    private TextView texp;
    private TextView tcurlevel;
    private TextView tlv;
    private EditText editText;
    //定义传参变量
    private Boolean isBehind = true;
    public static Boolean isFighting = false;
    public static Boolean doing = true;
    public static int monsterhp;
    public static int monsteratt;
    public static int monsterdef;
    public static float monsteradd;
    public static Bitmap monsterImage = null;
    public static Boolean[] trader1 = new Boolean[10];
    public Boolean IsOver;
    TrderDialog trderDialog;
    TalkDialog talkDialog;
    TalkDialog talkDialog1;
    SaveDialog saveDialog;
    ReadDialog readDialog;
    FightDialog fightDialog;
    ShoppingDialog shoppingDialog;
    ShoppingDialog1 shoppingDialog1;
    ShoppingDialog2 shoppingDialog2;
    FlyDialog flyDialog;
    private int x1,y1;
    public WindowManager.LayoutParams params;
    public WindowManager.LayoutParams params1;
    public Window win;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playactivity);
        Arrays.fill(trader1, Boolean.FALSE);
        //创建Dialog并设置样式主题
        trderDialog  = new TrderDialog(this,R.style.dialog);
        talkDialog = new TalkDialog(this,R.style.dialog);
        talkDialog1 = new TalkDialog(this,R.style.dialog);
        saveDialog = new SaveDialog(this,R.style.dialog);
        readDialog = new ReadDialog(this,R.style.dialog);
        fightDialog= new FightDialog(this,R.style.dialog);
        shoppingDialog = new ShoppingDialog(this,R.style.dialog);
        shoppingDialog1 = new ShoppingDialog1(this,R.style.dialog);
        shoppingDialog2 = new ShoppingDialog2(this,R.style.dialog);
        flyDialog = new FlyDialog(this,R.style.dialog1);
        //对话框的位置
        win = talkDialog.getWindow();
        Window win1 =  talkDialog1.getWindow();
        params1 = new WindowManager.LayoutParams();
        params = new WindowManager.LayoutParams();
        params1.x = -20;
        params1.y = 100;
        params.x = -20;//设置x坐标
        params.y = -400;//设置y坐标
        win1.setAttributes(params1);
        win.setAttributes(params);
        talkDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
        //设置对话框是否点击外面任意位置可关闭
        fightDialog.setCanceledOnTouchOutside(false);
        shoppingDialog.setCanceledOnTouchOutside(true);
        trderDialog.setCanceledOnTouchOutside(true);
        saveDialog.setCanceledOnTouchOutside(true);
        readDialog.setCanceledOnTouchOutside(true);
        flyDialog.setCanceledOnTouchOutside(true);
        //商店框的位置
        ispress = false; //判断是否被按
        mutex = 1; //按钮信号量 一次只能一个按钮被按
        isgoing = 0;
        touchmutex = 1;//点击触摸事件一次只能完成一个。
        signalup = 0; //释放上键
        signaldown = 0;//释放下键
        signalleft = 0;//释放左键
        signalright = 0;//释放右键
        //获取到Activity的实际屏幕信息

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        //显示框的布局要素
        thp = findViewById(R.id.hp);
        tatk = findViewById(R.id.atk);
        tdef = findViewById(R.id.def);
        taddDamage = findViewById(R.id.addDamage);
        tyellowkey = findViewById(R.id.yellow_key);
        tbluekey = findViewById(R.id.blue_key);
        tredkey = findViewById(R.id.red_key);
        tcoin = findViewById(R.id.gold);
        texp = findViewById(R.id.exp);
        tcurlevel = findViewById(R.id.floor);
        tlv = findViewById(R.id.lv);
        //创建楼层控制类、英雄控制类、商店控制类、故事控制类、装备控制类的对象（参数因构造方法的不同而不同）
        mapController = new MapController(this);
        person = new Person(this, mapController,GamePlayConstants.HeroStatusCode.HERO_NORMAL);
        manualController = new ManualController(person, mapController);
        //创建楼层视图类、英雄状态视图类、对话视图类的对象（参数因构造方法的不同而不同）
        mapView = new MapView(this, mapController, person, dm.widthPixels);
        toolsView = new ToolsView(this, person,dm.widthPixels);
        showView = new ShowView(this,manualController, mapView,dm.widthPixels);
        //先获得游戏管理类单例模式的对象，再调用注册方法，把五个控制类放入GameManager中的控制器集合中。
        GameManager.getInstance().register(mapController);
        GameManager.getInstance().register(person);
        ActivityCompat.requestPermissions(this, new String[]{android
                .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
        //如果传入失败，默认传入的是NEW_GAME
        int code = getIntent().getIntExtra("code",GamePlayConstants.GameStatusCode.NEW_GAME);
        if(code == GamePlayConstants.GameStatusCode.LOAD_GAME)
        {
            //先获得游戏管理类单例模式的对象，再调用加载方法。 Mark  eorry.
            GameManager.getInstance().load(0);
        }else{
            GameManager.getInstance().save(4);
        }
        //主游戏视图对象 赋值为 layout里的gv_main
        mainGameView = findViewById(R.id.gv_main);
        //调用主游戏视图对象的register方法，把楼层视图，英雄状态视图，对话视图添加到 IGameview类型的集合里。
        mainGameView.register(mapView);
        mainGameView.register(toolsView);
        mainGameView.register(showView);

        //重要线程 处理一些任务
        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 10) {
                    fightDialog.show();
                    ImageView imageView = fightDialog.findViewById(R.id.image);
                    TextView fightatt = fightDialog.findViewById(R.id.fightatt);
                    TextView fightdef = fightDialog.findViewById(R.id.fightdef);
                    TextView fightadd = fightDialog.findViewById(R.id.fightadd);
                    imageView.setImageBitmap(monsterImage);
                    fightatt.setText(String.valueOf(monsteratt));
                    fightdef.setText(String.valueOf(monsterdef));
                    fightadd.setText(String.valueOf(monsteradd));
                } else if (msg.what == 11) {
                    fightDialog.dismiss();
                } else if (msg.what == 1) {
                    ApplicationUtil.toast(PlayActivity.this, "此乃无法到达之地");
                } else if (msg.what == -5) {
                    showIn();
                } else if (msg.what == 2) {
                    ApplicationUtil.toast(PlayActivity.this, "黄钥匙不足");
                } else if (msg.what == 3) {
                    talkDialog.show();
                    TextView talk = talkDialog.findViewById(R.id.talktext);
                    ImageView imageView = talkDialog.findViewById(R.id.npc_image);
                    imageView.setImageBitmap(monsterImage);
                    talk.setTextSize(20);
                    talk.setText(":就凭你这点实力，也想跟我打？");
                    //isFighting = false;
                } else if (msg.what == 4) {
                    ApplicationUtil.toast(PlayActivity.this, "蓝钥匙不足");
                } else if (msg.what == 5) {
                    ApplicationUtil.toast(PlayActivity.this, "红钥匙不足");
                } else if (msg.what == 50) {
                    TextView fighthp = fightDialog.findViewById(R.id.fighthp);
                    fighthp.setText(String.valueOf(monsterhp));
                }//刷新商店页面
                else if (msg.what == 101) {
                    switch (curLevel) {
                        case 4:
                            shoppingDialog.show();
                            ViewGroup view = (ViewGroup) shoppingDialog.getWindow().getDecorView();
                            List<View> viewList = getAllChildViews(view);
                            for (int i = 0; i < viewList.size(); ++i) {
                                viewList.get(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Test(view);
                                    }
                                });
                            }
                            break;
                        case 12:
                            shoppingDialog1.show();
                            ViewGroup view2 = (ViewGroup) shoppingDialog1.getWindow().getDecorView();
                            List<View> viewList2 = getAllChildViews(view2);
                            for (int i = 0; i < viewList2.size(); ++i) {
                                viewList2.get(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Test(view);
                                    }
                                });
                            }
                            break;
                        case 22:
                            shoppingDialog2.show();
                            ViewGroup view3 = (ViewGroup) shoppingDialog2.getWindow().getDecorView();
                            List<View> viewList3 = getAllChildViews(view3);
                            for (int i = 0; i < viewList3.size(); ++i) {
                                viewList3.get(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Test(view);
                                    }
                                });
                            }
                            break;
                    }
                } else if (msg.what == 102) {
                    talkDialog.show();
                    talkDialog.talkWithElder(3);
                } else if (msg.what == 103) {
                    talkDialog.show();
                    talkDialog.talkWithElder(4);
                } else if (msg.what == -50) {
                    talkDialog.show();
                    talkDialog.talkWithElder(5);
                } else if (msg.what == -60) {
                    talkDialog.show();
                    talkDialog.talkWithElder(7);
                } else if (msg.what == 104) {
                    talkDialog.show();
                    talkDialog.talkWithElder(6);
                } else if (msg.what == 105) {
                    if(person.personAttribute.talk[3] == false) {
                        trderDialog.show();
                        ViewGroup view = (ViewGroup) trderDialog.getWindow().getDecorView();
                        List<View> viewList = getAllChildViews(view);
                        for (int i = 0; i < viewList.size(); ++i) {
                            viewList.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Test(view);
                                }
                            });
                        }
                        TextView talk1 = trderDialog.findViewById(R.id.talktext1);
                        talk1.setTextSize(18);
                        talk1.setText("商人： 50金币卖你1个蓝钥匙，你买吗？");
                    }else{
                        trderDialog.dismiss();
                        talkDialog.show();
                        talkDialog.talkWithTrader(6);
                    }
                } else if (msg.what == 106) {
                    if(person.personAttribute.talk[4] == false) {
                        trderDialog.show();
                        ViewGroup view = (ViewGroup) trderDialog.getWindow().getDecorView();
                        List<View> viewList = getAllChildViews(view);
                        for (int i = 0; i < viewList.size(); ++i) {
                            viewList.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Test(view);
                                }
                            });
                        }
                        TextView talk1 = trderDialog.findViewById(R.id.talktext1);
                        talk1.setTextSize(18);
                        talk1.setText("商人： 50金币卖你5个黄钥匙，你买吗？");
                    }else{
                        trderDialog.dismiss();
                        talkDialog.show();
                        talkDialog.talkWithTrader(7);
                    }
                } else if (msg.what == 107) {
                    if(person.personAttribute.talk[5] == false) {
                        trderDialog.show();
                        ViewGroup view = (ViewGroup) trderDialog.getWindow().getDecorView();
                        List<View> viewList = getAllChildViews(view);
                        for (int i = 0; i < viewList.size(); ++i) {
                            viewList.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Test(view);
                                }
                            });
                        }
                        TextView talk1 = trderDialog.findViewById(R.id.talktext1);
                        talk1.setTextSize(18);
                        talk1.setText("商人： 800金币一个红钥匙，买吗勇士？");
                    }else{
                        trderDialog.dismiss();
                        talkDialog.show();
                        talkDialog.talkWithTrader(12);
                    }
                } else if (msg.what == 108) {
                    talkDialog.show();
                    if(person.personAttribute.talk[0] == false){
                        talkDialog.talkWithSpirit(1);
                    }else if(person.personAttribute.tools[5] == true){
                        talkDialog.talkWithSpirit(8);
                        MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                        person.personAttribute.setAtk(person.personAttribute.getAtk() + 1000);
                        person.personAttribute.setDef(person.personAttribute.getDef() + 1000);
                        person.personAttribute.setAddDamage((float) (person.personAttribute.getAddDamage() + 0.5));
                        person.personAttribute.tools[5] = false;
                    }else{
                        talkDialog.talkWithSpirit(7);
                    }
                } else if (msg.what == 109) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(1);
                } else if (msg.what == 110) {
                    talkDialog.show();
                    talkDialog.talkWithSpirit(2);
                } else if (msg.what == 111) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(2);
                } else if (msg.what == 112) {
                    talkDialog.show();
                    talkDialog.talkWithSpirit(3);
                } else if (msg.what == 113) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(3);
                } else if (msg.what == 114) {
                    talkDialog.show();
                    talkDialog.talkWithSpirit(4);
                } else if (msg.what == 115) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(4);
                } else if (msg.what == 116) {
                    talkDialog.show();
                    talkDialog.talkWithSpirit(5);
                } else if (msg.what == 117) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(5);
                } else if (msg.what == 118) {
                    talkDialog.show();
                    talkDialog.talkWithSpirit(6);
                } else if (msg.what == -51) {
                    talkDialog.show();
                    talkDialog.talkWithThief(4);
                } else if (msg.what == 119) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(6);
                    mapController.setValueInMap(6,5,0);
                    mapController.setValueInMap(6,4,181);
                } else if (msg.what == 120) {
                    talkDialog.show();
                    talkDialog.talkWithSpirit(7);
                } else if (msg.what == 200) {
                    talkDialog.show();
                    talkDialog.talkWithThief(1);
                } else if (msg.what == 201) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(7);
                    mapController.setValueInMap(4,0,0);
                    mapController.setValueInMap(2,0,185);
                } else if (msg.what == 202) {
                    talkDialog.show();
                    talkDialog.talkWithThief(2);
                    ApplicationUtil.log("恭喜你获得","上下楼器！");
                    MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                    person.personAttribute.tools[0] = true;
                    person.personAttribute.tools[1] = true;
                } else if (msg.what == 203) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(8);
                } else if (msg.what == 204) {
                    talkDialog.show();
                    talkDialog.talkWithThief(3);
                } else if (msg.what == -62) {
                    talkDialog.show();
                    talkDialog.talkWithMowang();
                    person.personAttribute.talk[6] = true;
                } else if (msg.what == -63) {
                    talkDialog.show();
                    talkDialog.talkWithMowang1(1);
                }else if (msg.what == 300) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(9);
                }else if (msg.what == 301) {
                    talkDialog.show();
                    talkDialog.talkWithMowang1(2);
                }else if (msg.what == 302) {
                    talkDialog1.show();
                    talkDialog1.talkWithHero(10);
                }else if (msg.what == 303) {
                    talkDialog.show();
                    talkDialog.talkWithMowang1(3);
                    person.personAttribute.talk[7] = true;
                } else if (msg.what == 999) {
                    talkDialog.show();
                    talkDialog.talkWithMowang1(4);
                } else if (msg.what == -999) {
                    talkDialog.show();
                    talkDialog.talkWithGirl();
                } else if (msg.what == 1000) {
                    talkDialog.show();
                    talkDialog.talkWithSpirit(10);
                }
            }
        };

        //设置监听事件
        mainGameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //模拟点击事件 自动点一下屏幕 ？？？
                    v.performClick();
                    talkDialog.dismiss();
                    flyDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    //获取点击到的 x,y位置。浮点型
                    float x = event.getX();
                    float y = event.getY();
                    //int 宽度索引 =  (int) （x/每一小张怪物图片的宽度）
                    int widthIndex = (int) (x / mapView.getSpriteWidth());
                    //int 高度索引 =  (int) （y/每一小张怪物图片的宽度）
                    final int heightIndex = (int) ((y - 260) / mapView.getSpriteWidth() / 1.1);
                    ApplicationUtil.log("图片宽度", mapView.getSpriteWidth());
                    //打印块坐标
                    if(manualController.isShowing()){
                        showView.onClick(x,y);
                        return false;
                    }
                    if(touchmutex == 1) {
                        int code = toolsView.onClick(x, y);
                        if (code == GamePlayConstants.EquipmentCode.BOOK_CLICK) {
                            //点到了怪物之书，调用装备控制器的start 方法。
                            ApplicationUtil.log("怪物手册", "被点击");
                            manualController.start();
                        } else if (code == GamePlayConstants.EquipmentCode.UP_CLICK) {
                            ApplicationUtil.log("上了", "一层楼");
                            if(curLevel + 1 <= 36){
                                if(person.personAttribute.floor[curLevel + 1] == true){
                                    mapController.upStairs();
                                    showIn();
                                    person.findPersonLocation();
                                }else{
                                    ApplicationUtil.toast(PlayActivity.this,"此楼层未去过");
                                }
                            }
                        } else if (code == GamePlayConstants.EquipmentCode.DOWN_CLICK) {
                            ApplicationUtil.log("下了", "一层楼");
                            mapController.downStairs();
                            showIn();
                            person.findPersonLocation();
                        } else if (code == GamePlayConstants.EquipmentCode.TRAN_CLICK) {
                            ApplicationUtil.log("楼层传送器", "被点击");
                            flyDialog.show();
                            flyDialog.getWindow().clearFlags(
                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                            flyDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            editText = flyDialog.findViewById(R.id.edit);
                            InputMethodManager inputManager = (InputMethodManager) editText
                                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.showSoftInput(editText, 0);
                            Button button = flyDialog.findViewById(R.id.confirm);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Test(view);
                                }
                            });
                        } else if (code == GamePlayConstants.EquipmentCode.PICK_CLICK) {
                            ApplicationUtil.log("铁镐", "被点击");
                            int k = person.findPersonLocation();
                            if(mapController.getValueInMap(k / GamePlayConstants.MAP_WIDTH,k % GamePlayConstants.MAP_WIDTH)
                                    == GamePlayConstants.GameValueConstants.HERO_back2){
                                if((k /  GamePlayConstants.MAP_WIDTH) >= 1) {
                                    if (mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH) - 1, k % GamePlayConstants.MAP_WIDTH)
                                            == 1 || mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH) - 1, k % GamePlayConstants.MAP_WIDTH)
                                            == 2) {
                                        MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                                        mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH) - 1, k % GamePlayConstants.MAP_WIDTH, 0);
                                        person.personAttribute.tools[4] = false;
                                    }
                                }
                            } else if(mapController.getValueInMap(k / GamePlayConstants.MAP_WIDTH,k % GamePlayConstants.MAP_WIDTH)
                                    == GamePlayConstants.GameValueConstants.HERO_forward2){
                                if((k /  GamePlayConstants.MAP_WIDTH) <= 9) {
                                    if (mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH) + 1, k % GamePlayConstants.MAP_WIDTH)
                                            == 1 || mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH) + 1, k % GamePlayConstants.MAP_WIDTH)
                                            == 2) {
                                        MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                                        mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH) + 1, k % GamePlayConstants.MAP_WIDTH, 0);
                                        person.personAttribute.tools[4] = false;
                                    }
                                }
                            } else if(mapController.getValueInMap(k / GamePlayConstants.MAP_WIDTH,k % GamePlayConstants.MAP_WIDTH)
                                    == GamePlayConstants.GameValueConstants.HERO_left2){
                                if((k % GamePlayConstants.MAP_WIDTH) >= 1) {
                                    if (mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) - 1)
                                            == 1 || mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) - 1)
                                            == 2) {
                                        MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                                        mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) - 1, 0);
                                        person.personAttribute.tools[4] = false;
                                    }
                                }
                            } else if(mapController.getValueInMap(k / GamePlayConstants.MAP_WIDTH,k % GamePlayConstants.MAP_WIDTH)
                                    == GamePlayConstants.GameValueConstants.HERO_right2){
                                if((k % GamePlayConstants.MAP_WIDTH) <= 9) {
                                    if (mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) + 1)
                                            == 1 || mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) + 1)
                                            == 2) {
                                        MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                                        mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) + 1, 0);
                                        person.personAttribute.tools[4] = false;
                                    }
                                }
                            }
                        } else if (code == GamePlayConstants.EquipmentCode.FROZEN_CLICK) {
                            int k = person.findPersonLocation();
                            if(k / GamePlayConstants.MAP_WIDTH >= 1){
                                if(mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH) - 1,
                                        k % GamePlayConstants.MAP_WIDTH) == GamePlayConstants.GameValueConstants.FIRE){
                                    mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH) - 1, k % GamePlayConstants.MAP_WIDTH,0);
                                    if(k - 11 == 30){
                                        mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH) - 1, k % GamePlayConstants.MAP_WIDTH,13);
                                    }
                                }
                            }
                            if(k % GamePlayConstants.MAP_WIDTH >= 1){
                                if(mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH),
                                        (k % GamePlayConstants.MAP_WIDTH) - 1) == GamePlayConstants.GameValueConstants.FIRE){
                                    mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) - 1,0);
                                }
                            }
                            if(k % GamePlayConstants.MAP_WIDTH <= 9){
                                if(mapController.getValueInMap((k / GamePlayConstants.MAP_WIDTH),
                                        (k % GamePlayConstants.MAP_WIDTH) + 1) == GamePlayConstants.GameValueConstants.FIRE){
                                    mapController.setValueInMap((k / GamePlayConstants.MAP_WIDTH), (k % GamePlayConstants.MAP_WIDTH) + 1,0);
                                }
                            }
                        } else if (code == GamePlayConstants.EquipmentCode.MAGIC_KEY_CLICK) {
                            ApplicationUtil.log("魔法钥匙", "被点击");
                            for (int i = 0; i < mapController.getMap().getMap().length / GamePlayConstants.MAP_WIDTH; i++) {
                                for (int j = 0; j < GamePlayConstants.MAP_WIDTH; j++) {
                                    if (mapController.getMap().getMap()[i * GamePlayConstants.MAP_WIDTH + j] == 51) {
                                        mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.GROUND);
                                    }
                                }
                            }
                            person.personAttribute.tools[7] = false;
                        } else if (code == GamePlayConstants.EquipmentCode.BOOM_CLICK) {
                            ApplicationUtil.log("炸药", "被点击");
                            for (int i = 0; i < mapController.getMap().getMap().length / GamePlayConstants.MAP_WIDTH; i++) {
                                for (int j = 0; j < GamePlayConstants.MAP_WIDTH; j++) {
                                    if (mapController.getMap().getMap()[i * GamePlayConstants.MAP_WIDTH + j] == 1) {
                                        mapController.setValueInMap(i, j, 0);
                                    }
                                }
                            }
                            person.personAttribute.tools[8] = false;
                        }
                    }
                    //如果点击位置的 宽度超过了地图范围 或者高度超过了地图范围，return false
                    if(widthIndex >= GamePlayConstants.MAP_WIDTH || heightIndex >= GamePlayConstants.MAP_WIDTH)
                        return false;
                    //如果点击位置的 宽度超过了地图范围 或者高度超过了地图范围，return false
                    if(widthIndex < 0 || (y - 260) < 0)
                        return false;
                    final int finalheightIndex = heightIndex;
                    final int finalwidthIndex = widthIndex;
                    Thread show = new Thread(){
                        @Override
                        public void run(){
                            super.run();
                            if(mapController.getValueInMap(finalheightIndex,finalwidthIndex) >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN &&
                                    mapController.getValueInMap(finalheightIndex,finalwidthIndex)
                                            <= GamePlayConstants.GameValueConstants.MONSTER_ID_END) {
                                doing = true;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while(doing) {
                                           // ApplicationUtil.log("进入", "循环");
                                            if(isFighting == true){
                                                mHandler.sendEmptyMessage(10);
                                                try {
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                Thread.sleep(300);
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                            //刷新怪物的血量
                                                            mHandler.sendEmptyMessage(50);
                                                            //显示人物的状态
                                                            mHandler.sendEmptyMessage(100);
                                                        }
                                                    }).start();
                                                    Thread.sleep(150);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        mHandler.sendEmptyMessage(11);


                                        ApplicationUtil.log("全部", "结束");
                                    }
                                }).start();

                            }
                            try {
                                if(touchmutex == 1) {
                                    touchmutex = 0;
                                    int m = person.goToTarget(finalheightIndex, finalwidthIndex);
                                    x1 = finalheightIndex;
                                    y1 = finalwidthIndex;
                                    int t = sendMessage(m);
                                    mHandler.sendEmptyMessage(t);
                                    if(t == -999){
                                        Thread.sleep(200);
                                        while (true) {
                                            if (talkDialog.isShowing() == false) {
                                                mHandler.sendEmptyMessage(1000);
                                                t = 1000;
                                                break;
                                            }
                                        }
                                    }
                                    if(t == 1000){
                                        Thread.sleep(200);
                                        while (true) {
                                            if (talkDialog.isShowing() == false) {
                                                Intent intent = new Intent();
                                                intent.setClass(PlayActivity.this, explainActivity.class);
                                                startActivity(intent);
                                                finish();
                                                break;
                                            }
                                        }

                                    }
                                    if(t == -63){
                                        Thread.sleep(200);
                                        while (true) {
                                            if (talkDialog.isShowing() == false) {
                                                mHandler.sendEmptyMessage(300);
                                                t = 300;
                                                break;
                                            }
                                        }
                                    }
                                    if(t == 300){
                                        Thread.sleep(200);
                                        while (true) {
                                            if (talkDialog1.isShowing() == false) {
                                                mHandler.sendEmptyMessage(301);
                                                t = 301;
                                                break;
                                            }
                                        }
                                    }
                                    if(t == 301){
                                        Thread.sleep(200);
                                        while (true) {
                                            if (talkDialog.isShowing() == false) {
                                                mHandler.sendEmptyMessage(302);
                                                t = 302;
                                                break;
                                            }
                                        }
                                    }
                                    if(t == 302){
                                        Thread.sleep(200);
                                        while (true) {
                                            if (talkDialog1.isShowing() == false) {
                                                mHandler.sendEmptyMessage(303);
                                                break;
                                            }
                                        }
                                    }
                                    if(mapController.getValueInMap(7,0) == 0
                                            && mapController.getValueInMap(8,0) == 0 ){
                                        person.personAttribute.talk[1] = true;
                                    }
                                    if( t == -51){
                                        Thread.sleep(200);
                                        while(true){
                                            if(talkDialog.isShowing() == false){
                                                mapController.setValueInMap(0,8,0);
                                                break;
                                            }
                                        }
                                    }

                                    if(person.personAttribute.talk[1] == false) {
                                        if (t == 200) {
                                            Thread.sleep(200);
                                            while (true) {
                                                if (talkDialog.isShowing() == false) {
                                                    mHandler.sendEmptyMessage(201);
                                                    t = 201;
                                                    break;
                                                }
                                            }
                                        }
                                    }else{
                                        if (t == 200) {
                                            Thread.sleep(200);
                                            mHandler.sendEmptyMessage(202);
                                            t = 202;
                                        }
                                        if (t == 202) {
                                            Thread.sleep(200);
                                            while (true) {
                                                if (talkDialog.isShowing() == false) {
                                                    mHandler.sendEmptyMessage(203);
                                                    t = 203;
                                                    break;
                                                }
                                            }
                                        }
                                        if (t == 203) {
                                            Thread.sleep(200);
                                            while (true) {
                                                if (talkDialog1.isShowing() == false) {
                                                    mHandler.sendEmptyMessage(204);
                                                    t = 204;
                                                    break;
                                                }
                                            }
                                        }
                                        if (t == 204) {
                                            Thread.sleep(200);
                                            while (true) {
                                                if (talkDialog.isShowing() == false) {
                                                    mapController.setValueInMap(2,0,0);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if(person.personAttribute.talk[0] == false){
                                        if(t == 108){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog.isShowing() == false){
                                                    mHandler.sendEmptyMessage(109);
                                                    t = 109;
                                                    break;
                                                }
                                            }
                                        }
                                        if(t == 109){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog1.isShowing() == false){
                                                    mHandler.sendEmptyMessage(110);
                                                    t = 110;
                                                    break; } } }
                                        if(t == 110){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog.isShowing() == false){
                                                    mHandler.sendEmptyMessage(111);
                                                    t = 111;
                                                    break; } } }
                                        if(t == 111){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog1.isShowing() == false){
                                                    mHandler.sendEmptyMessage(112);
                                                    t = 112;
                                                    break; } } }
                                        if(t == 112){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog.isShowing() == false){
                                                    mHandler.sendEmptyMessage(113);
                                                    t = 113;
                                                    break; } } }
                                        if(t == 113){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog1.isShowing() == false){
                                                    mHandler.sendEmptyMessage(114);
                                                    t = 114;
                                                    break; } } }
                                        if(t == 114){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog.isShowing() == false){
                                                    mHandler.sendEmptyMessage(115);
                                                    t = 115;
                                                    break; } } }
                                        if(t == 115){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog1.isShowing() == false){
                                                    mHandler.sendEmptyMessage(116);
                                                    t = 116;
                                                    break; } } }
                                        if(t == 116){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog.isShowing() == false){
                                                    mHandler.sendEmptyMessage(117);
                                                    t = 117;
                                                    break; } } }
                                        if(t == 117){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog1.isShowing() == false){
                                                    mHandler.sendEmptyMessage(118);
                                                    t = 118;
                                                    break; } } }
                                        if(t == 118){
                                            Thread.sleep(200);
                                            while(true){
                                                if( talkDialog.isShowing() == false){
                                                    mHandler.sendEmptyMessage(119);
                                                    person.personAttribute.talk[0] = true;
                                                    break; } } }
                                    }
                                    touchmutex = 1;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }};
                    show.start();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                showIn();
                //都不是返回 false
                return false;
            }
        });

        //游戏中 唤出菜单的方式
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(menu);
            }
        });

        showIn();

        //设计按钮功能
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ispress = true;
                        Thread tright = new Thread(){
                            @Override
                            public void run(){
                                super.run();
                                mutex = 0; //锁住右键
                                if(isgoing == 0) {
                                while(ispress == true) {
                                        try {
                                            isgoing = 1;
                                            FloorMap map = mapController.getMap();
                                            int j = person.findPersonLocation();
                                            if (j + 1 < 121) {
                                                if (map.getMap()[j + 1] != 1 && (j + 1) % 11 != 0) {
                                                    if(map.getMap()[j + 1] >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN &&
                                                            map.getMap()[j + 1] <= GamePlayConstants.GameValueConstants.MONSTER_ID_END) {
                                                        doing = true;
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                              //  ApplicationUtil.log("正在刷新","UI");
                                                                //在战斗
                                                                while(doing) {
                                                                    if(isFighting == true){
                                                                        mHandler.sendEmptyMessage(10);
                                                                        try {
                                                                            Thread.sleep(150);
                                                                            mHandler.sendEmptyMessage(100);
                                                                            mHandler.sendEmptyMessage(50);
                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }
                                                                //关闭战斗框
                                                                mHandler.sendEmptyMessage(11);
                                                            }
                                                        }).start();
                                                    }
                                                    int m = person.move((j + 1) / 11, ((j + 1) % 11));
                                                    x1 = (j + 1) / 11;
                                                    y1 = (j + 1) % 11;
                                                    int t = sendMessage(m);
                                                    mHandler.sendEmptyMessage(t);
                                                } }
                                            Thread.sleep(100);//间隔100毫秒，每隔100ms走一次。
                                            isgoing = 0;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } } } } };
                        if(mutex == 1){  //刚开始信号量为一
                            signalright = 1;
                            tright.start(); //开启线程
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(signalright == 1){
                            bottonUp();
                            signalright = 0;
                        }
                        break;
                }
                return true;
            }});

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ispress = true;
                        Thread tdown = new Thread(){
                            @Override
                            public void run(){
                                super.run();
                                mutex = 0;
                                if (isgoing == 0) {
                                while(ispress == true) {
                                        try {
                                            isgoing = 1;
                                            FloorMap map = mapController.getMap();
                                            int j = person.findPersonLocation();
                                            if (j + 11 < 121) {
                                                if (map.getMap()[j + 11] != 1) {
                                                    if(map.getMap()[j + 11] >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN &&
                                                            map.getMap()[j + 11] <= GamePlayConstants.GameValueConstants.MONSTER_ID_END)
                                                    {
                                                        doing = true;
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                           //     ApplicationUtil.log("正在刷新","UI");
                                                                while(doing) {
                                                                    if(isFighting == true){
                                                                        mHandler.sendEmptyMessage(10);
                                                                        try {
                                                                            Thread.sleep(150);
                                                                            mHandler.sendEmptyMessage(100);
                                                                            mHandler.sendEmptyMessage(50);
                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }
                                                                //关闭战斗框
                                                                mHandler.sendEmptyMessage(11);
                                                            }
                                                        }).start();
                                                    }
                                                    int m = person.move((j + 11) / 11, ((j + 11) % 11));
                                                    x1 = (j + 11) / 11;
                                                    y1 = (j + 11) % 11;
                                                    int t = sendMessage(m);
                                                    mHandler.sendEmptyMessage(t);
                                                    if(mapController.getValueInMap(7,0) == 0
                                                            && mapController.getValueInMap(8,0) == 0 ){
                                                        person.personAttribute.talk[1] = true;
                                                    }
                                                    if(t == 108){
                                                        if(person.personAttribute.talk[0] == true && person.personAttribute.tools[5] == true){

                                                        }
                                                    }

                                                    if(person.personAttribute.talk[1] == false) {
                                                        if (t == 200) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog.isShowing() == false) {
                                                                    mHandler.sendEmptyMessage(201);
                                                                    t = 201;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }else{
                                                        if (t == 200) {
                                                            Thread.sleep(200);
                                                            mHandler.sendEmptyMessage(202);
                                                            t = 202;
                                                        }
                                                        if (t == 202) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog.isShowing() == false) {
                                                                    mHandler.sendEmptyMessage(203);
                                                                    t = 203;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (t == 203) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog1.isShowing() == false) {
                                                                    mHandler.sendEmptyMessage(204);
                                                                    t = 204;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (t == 204) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog.isShowing() == false) {
                                                                    mapController.setValueInMap(2,0,0);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            Thread.sleep(100);//间隔50毫秒这里决定长按时 执行点击方法的频率
                                            isgoing = 0;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        };
                        if(mutex == 1){
                            signaldown = 1;
                            tdown.start();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(signaldown == 1){
                            bottonUp();
                            signaldown = 0;
                        }
                        break;
                }
                return true;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ispress = true;
                        Thread tleft = new Thread(){
                            @Override
                            public void run(){
                                super.run();
                                mutex = 0;
                                if (isgoing == 0) {
                                while(ispress == true) {
                                        try {
                                            isgoing = 1;
                                            FloorMap map = mapController.getMap();
                                            int j = person.findPersonLocation();
                                            if (j - 1 >= 0) {
                                                if (map.getMap()[j - 1] != 1 && (j - 1) % 11 != 10) {
                                                    if(map.getMap()[j - 1] >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN &&
                                                            map.getMap()[j - 1] <= GamePlayConstants.GameValueConstants.MONSTER_ID_END)
                                                    {
                                                        doing = true;
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                              //  ApplicationUtil.log("正在刷新","UI");
                                                                while(doing) {
                                                                    if(isFighting == true){
                                                                        mHandler.sendEmptyMessage(10);
                                                                        try {
                                                                            Thread.sleep(150);
                                                                            mHandler.sendEmptyMessage(100);
                                                                            mHandler.sendEmptyMessage(50);
                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }
                                                                //关闭战斗框
                                                                mHandler.sendEmptyMessage(11);
                                                            }
                                                        }).start();
                                                    }
                                                    int m = person.move((j - 1) / 11, ((j - 1) % 11));
                                                    x1 = (j - 1) / 11;
                                                    y1 = (j - 1) % 11;
                                                    int t = sendMessage(m);
                                                    mHandler.sendEmptyMessage(t);
                                                    if( t == -51){
                                                        Thread.sleep(200);
                                                        while(true){
                                                            if(talkDialog.isShowing() == false){
                                                                mapController.setValueInMap(0,8,0);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            Thread.sleep(100);//间隔50毫秒这里决定长按时 执行点击方法的频率
                                            isgoing = 0;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        };
                        if(mutex == 1){
                            signalleft = 1;
                            tleft.start();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(signalleft == 1){
                            bottonUp();
                            signalleft = 0;
                        }
                        break;
                }
                return true;
            }
        });

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ispress = true;
                        Thread tup = new Thread(){
                            @Override
                            public void run(){
                                super.run();
                                mutex = 0;
                                if (isgoing == 0) {
                                while(ispress == true) {
                                        try {
                                            isgoing = 1;
                                            FloorMap map = mapController.getMap();
                                            int j = person.findPersonLocation();
                                            if (j - 11 >= 0) {
                                                if (map.getMap()[j - 11] != 1) {
                                                    if(map.getMap()[j - 11] >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN &&
                                                            map.getMap()[j - 11] < GamePlayConstants.GameValueConstants.MONSTER_ID_END)
                                                    {
                                                        doing = true;
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                while(doing) {
                                                                    if(isFighting == true){
                                                                        mHandler.sendEmptyMessage(10);
                                                                        try {
                                                                            Thread.sleep(150);
                                                                            mHandler.sendEmptyMessage(100);
                                                                            mHandler.sendEmptyMessage(50);
                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }
                                                                //关闭战斗框
                                                                mHandler.sendEmptyMessage(11);
                                                            }
                                                        }).start();
                                                    }
                                                    int m = person.move((j - 11) / 11, ((j - 11) % 11));
                                                    x1 = (j - 11) / 11;
                                                    y1 = (j - 11) % 11;
                                                    int t = sendMessage(m);
                                                    mHandler.sendEmptyMessage(t);
                                                    if(t == -999){
                                                        Thread.sleep(200);
                                                        while (true) {
                                                            if (talkDialog.isShowing() == false) {
                                                                mHandler.sendEmptyMessage(1000);
                                                                t = 1000;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if(t == 1000){
                                                        Thread.sleep(200);
                                                        while (true) {
                                                            if (talkDialog.isShowing() == false) {
                                                                Intent intent = new Intent();
                                                                intent.setClass(PlayActivity.this, explainActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                                break;
                                                            }
                                                        }

                                                    }
                                                    if(t == -63){
                                                        Thread.sleep(200);
                                                        while (true) {
                                                            if (talkDialog.isShowing() == false) {
                                                                mHandler.sendEmptyMessage(300);
                                                                t = 300;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if(t == 300){
                                                        Thread.sleep(200);
                                                        while (true) {
                                                            if (talkDialog1.isShowing() == false) {
                                                                mHandler.sendEmptyMessage(301);
                                                                t = 301;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if(t == 301){
                                                        Thread.sleep(200);
                                                        while (true) {
                                                            if (talkDialog.isShowing() == false) {
                                                                mHandler.sendEmptyMessage(302);
                                                                t = 302;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if(t == 302){
                                                        Thread.sleep(200);
                                                        while (true) {
                                                            if (talkDialog1.isShowing() == false) {
                                                                mHandler.sendEmptyMessage(303);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if(mapController.getValueInMap(7,0) == 0
                                                            && mapController.getValueInMap(8,0) == 0 ){
                                                        person.personAttribute.talk[1] = true;
                                                    }
                                                    if(person.personAttribute.talk[1] == false) {
                                                        if (t == 200) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog.isShowing() == false) {
                                                                    mHandler.sendEmptyMessage(201);
                                                                    t = 201;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }else{
                                                        if (t == 200) {
                                                            Thread.sleep(200);
                                                            mHandler.sendEmptyMessage(202);
                                                            t = 202;
                                                        }
                                                        if (t == 202) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog.isShowing() == false) {
                                                                    mHandler.sendEmptyMessage(203);
                                                                    t = 203;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (t == 203) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog1.isShowing() == false) {
                                                                    mHandler.sendEmptyMessage(204);
                                                                    t = 204;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (t == 204) {
                                                            Thread.sleep(200);
                                                            while (true) {
                                                                if (talkDialog.isShowing() == false) {
                                                                    mapController.setValueInMap(2,0,0);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    if(person.personAttribute.talk[0] == false){
                                                        if(t == 108){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(109);
                                                                    t = 109;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if(t == 109){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog1.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(110);
                                                                    t = 110;
                                                                    break; } } }
                                                        if(t == 110){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(111);
                                                                    t = 111;
                                                                    break; } } }
                                                        if(t == 111){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog1.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(112);
                                                                    t = 112;
                                                                    break; } } }
                                                        if(t == 112){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(113);
                                                                    t = 113;
                                                                    break; } } }
                                                        if(t == 113){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog1.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(114);
                                                                    t = 114;
                                                                    break; } } }
                                                        if(t == 114){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(115);
                                                                    t = 115;
                                                                    break; } } }
                                                        if(t == 115){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog1.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(116);
                                                                    t = 116;
                                                                    break; } } }
                                                        if(t == 116){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(117);
                                                                    t = 117;
                                                                    break; } } }
                                                        if(t == 117){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog1.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(118);
                                                                    t = 118;
                                                                    break; } } }
                                                        if(t == 118){
                                                            Thread.sleep(200);
                                                            while(true){
                                                                if( talkDialog.isShowing() == false){
                                                                    mHandler.sendEmptyMessage(119);
                                                                    person.personAttribute.talk[0] = true;
                                                                    break; } } }
                                                    }
                                                }
                                            }
                                            Thread.sleep(100);//间隔50毫秒这里决定长按时 执行点击方法的频率
                                            isgoing = 0;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        };
                        if(mutex == 1){
                            signalup = 1;
                            tup.start();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(signalup == 1){
                            bottonUp();
                            signalup = 0;
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void bottonUp(){
        ispress = false;
        showIn();
        mutex = 1;
    }
    public void showIn(){
       // ApplicationUtil.log("调用showIn方法", "一次");
        hp = person.personAttribute.getHp();
        atk = person.personAttribute.getAtk();
        def = person.personAttribute.getDef();
        addDamage = person.personAttribute.getAddDamage();
        coin = person.personAttribute.getCoin();
        exp = person.personAttribute.getExp();
        curLevel = person.personAttribute.getCurLevel();
        yellowKey = person.personAttribute.getYellowKey();
        blueKey = person.personAttribute.getBlueKey();
        redKey = person.personAttribute.getRedKey();
        lv = person.personAttribute.getLv();
        thp.setText(String.valueOf(hp));
        tatk.setText(String.valueOf(atk));
        tdef.setText(String.valueOf(def));
        taddDamage.setText(String.format("%.2f", addDamage));
        tyellowkey.setText(String.valueOf(yellowKey));
        tbluekey.setText(String.valueOf(blueKey));
        tredkey.setText(String.valueOf(redKey));
        tcoin.setText(String.valueOf(coin));
        texp.setText(String.valueOf(exp));
        tcurlevel.setText(String.valueOf(curLevel));
        tlv.setText(String.valueOf(lv));

    }

    public void Test(View view)
    {
        switch (view.getId())
        {
            case R.id.addHp:
                switch (curLevel){
                    case 4:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addHPbig();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 12:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setHp(person.personAttribute.getHp() + 3000);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 22:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setHp(person.personAttribute.getHp() + 25000);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.addAtt:
                switch (curLevel){
                    case 4:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addatk();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 12:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setAtk(person.personAttribute.getAtk() + 22);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 22:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setAtk(person.personAttribute.getAtk() + 170);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.addDef:
                switch (curLevel){
                    case 4:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addDef();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 12:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setDef(person.personAttribute.getDef() + 22);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 22:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setDef(person.personAttribute.getDef() + 170);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.addAdd:
                switch (curLevel){
                    case 4:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addDamage();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 12:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setAddDamage((float) (person.personAttribute.getAddDamage() + 0.06));
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                    case 22:
                        //按钮的声音
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setAddDamage((float) (person.personAttribute.getAddDamage() + 0.25));
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"金币不足，购买失败");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.buy:
                //按钮的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                switch (curLevel){
                    case 6:
                            if(person.personAttribute.getCoin() >= 50){
                                person.personAttribute.addBlueKey();
                                person.personAttribute.setCoin(person.personAttribute.getCoin() - 50);
                                person.personAttribute.talk[3] = true;
                                trderDialog.dismiss();
                            }else {
                                ApplicationUtil.toast(this,"商人：你是来逗我的吗？");
                            }
                        break;
                    case 7:
                            if(person.personAttribute.getCoin() >= 50){
                            /*for(int i = 0;i < 5; ++i)
                                heroController.heroAttribute.addYellowKey();*/
                                person.personAttribute.setCoin(person.personAttribute.getCoin() - 50);
                                person.personAttribute.talk[4] = true;
                                trderDialog.dismiss();
                            }else {
                                ApplicationUtil.toast(this,"商人：小本生意，概不降价！");
                            }
                        break;
                    case 12:
                            if(person.personAttribute.getCoin() >= 800){
                                person.personAttribute.addRedKey();
                                person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                                person.personAttribute.talk[5] = true;
                                trderDialog.dismiss();
                            }else {
                                ApplicationUtil.toast(this,"红钥匙800一个贵吗？你敢说个贵试试！");
                            }
                        break;
                }
                showIn();
                break;
            case R.id.save0:
                GameManager.getInstance().save(0);
                ApplicationUtil.toast(this,"保存在自动存档 成功！");
                break;
            case R.id.save1:
                GameManager.getInstance().save(1);
                ApplicationUtil.toast(this,"保存在存档1 成功！");
                break;
            case R.id.save2:
                GameManager.getInstance().save(2);
                ApplicationUtil.toast(this,"保存在存档2 成功！");
                break;
            case R.id.save3:
                GameManager.getInstance().save(3);
                ApplicationUtil.toast(this,"保存在存档3 成功！");
                break;
            case R.id.read0:
                int d = GameManager.getInstance().load(0);
                if(d == 0) {
                    ApplicationUtil.toast(this, "读取存档0 成功！");
                }else{
                    ApplicationUtil.toast(this, "读取存档0 失败，空白存档！");
                }
                showIn();
                break;
            case R.id.read1:
                int a = GameManager.getInstance().load(1);
                if(a == 0) {
                    ApplicationUtil.toast(this, "读取存档1 成功！");
                }else{
                    ApplicationUtil.toast(this, "读取存档1 失败，空白存档！");
                }
                showIn();
                break;
            case R.id.read2:
                int b = GameManager.getInstance().load(2);
                if(b == 0) {
                    ApplicationUtil.toast(this, "读取存档2 成功！");
                }else{
                    ApplicationUtil.toast(this, "读取存档2 失败，空白存档！");
                }
                showIn();
                break;
            case R.id.read3:
                int c = GameManager.getInstance().load(3);
                if(c == 0) {
                    ApplicationUtil.toast(this, "读取存档3 成功！");
                }else{
                    ApplicationUtil.toast(this, "读取存档3 失败，空白存档！");
                }
                showIn();
                break;
            case R.id.no:
                trderDialog.dismiss();
                break;
            case R.id.confirm:
                editText = flyDialog.findViewById(R.id.edit);
                String txt = editText.getText().toString();
                if(txt.length() != 0)
                {
                        Pattern p = Pattern.compile("[0-9]*");
                        Matcher m = p.matcher(txt);
                        if (m.matches()) {
                            int floor = Integer.parseInt(txt);
                            if (floor >= 0 && floor <= 36) {
                                if(person.personAttribute.floor[floor] == true){
                                    mapController.trans(floor);
                                    ApplicationUtil.log("floor", floor);
                                    person.personAttribute.setCurLevel(mapController.getCurrentFloor());
                                    //floorController.setLevel(heroController.heroAttribute.getCurLevel());
                                    ApplicationUtil.log("Curfloor", person.personAttribute.getCurLevel());
                                    person.findPersonLocation();
                                    showIn();
                                }else{
                                    ApplicationUtil.toast(PlayActivity.this,"此楼层未去过");
                                }
                            } else {
                                ApplicationUtil.toast(this, "请输入正确的楼层");
                            }
                        } else {
                            ApplicationUtil.toast(this, "请输入数字");
                        }
                }
                break;
        }
    }
    //SendMessage方法
    public int sendMessage(int m){
        //英雄控制器的goToTarget方法，返回的各种参数，由应用工具类的toast方法显示。
        if (m == GamePlayConstants.MoveStatusCode.CANT_REACH) {
            return 1;
        } else if (m == GamePlayConstants.MoveStatusCode.NO_YELLOW_KEY) {
            doing = false;
            return 2;
        } else if (m == GamePlayConstants.MoveStatusCode.FIGHT_CANT) {
            doing = false;
            return 3;
        } else if (m == GamePlayConstants.MoveStatusCode.NO_BLUE_KEY) {
            doing = false;
            return 4;
        } else if (m == GamePlayConstants.MoveStatusCode.NO_RED_KEY) {
            doing = false;
            return 5;
        } else if (m == GamePlayConstants.MoveStatusCode.MOVE_SUCCESS_CODE || m == GamePlayConstants.MoveStatusCode.MOVE_FLOOR) {
            return -5;
        } else if (m == GamePlayConstants.MoveStatusCode.SHOPPING) {
            return 101;
        } else if (m == GamePlayConstants.MoveStatusCode.SHOPPING2) {
            return 101;
        }else if (m == GamePlayConstants.MoveStatusCode.TALKING_WITH_ELDER) {
            switch(curLevel){
                case 3:
                    return 102;
                case 4:
                    return 103;
                case 6:
                    return 104;
                case 12:
                    return -60;
                case 18:
                    return -50;
            }
        } else if (m == GamePlayConstants.MoveStatusCode.TALKING_WITH_TRADER) {
            switch(curLevel){
                case 6:
                    return 105;
                case 7:
                    return 106;
                case 12:
                    return 107;
            }
        } else if (m == GamePlayConstants.MoveStatusCode.TALKING_WITH_SPIRIT_ONE){
            return 108;
        } else if (m == GamePlayConstants.MoveStatusCode.TALKING_WITH_SPIRIT_TWO){
            touchmutex = 1;
        }  else if (m == GamePlayConstants.MoveStatusCode.TALKING_WITH_THIEF){
            switch(curLevel){
                case 2:
                    return 200;
                case 15:
                    return -51;

            }
        } else if( m == -80){
            return  -62;
        } else if( m == -81){
            return  -63;
        } else if( m == 999){
            return  999;
        } else if( m == -999){
            return  -999;
        }
        return  -99;
    }

    //使得程序按返回键失效
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //实现home键暂停 返回时开启
    protected void onPause(){
        isBehind=true;
        super.onPause();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!isBehind)
        {
            MyService.mediaPlayer.start();
        }
    }
    protected void onStop(){
        if(isBehind != false)
        {
            //Intent intent = new Intent(this, MyService.class);
            //stopService(intent);
            MyService.mediaPlayer.pause();
            isBehind = false;
        }
        super.onStop();
    }

    //显示下拉菜单
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.save_game:
                        saveDialog.show();
                        ViewGroup view1 = (ViewGroup) saveDialog.getWindow().getDecorView();
                        List<View> viewList1 = getAllChildViews(view1);
                        for (int i = 0; i < viewList1.size(); ++i) {
                            viewList1.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Test(view);
                                }
                            });
                        }
                        break;
                    case R.id.read_game:
                        readDialog.show();
                        ViewGroup view = (ViewGroup) readDialog.getWindow().getDecorView();
                        List<View> viewList = getAllChildViews(view);
                        for (int i = 0; i < viewList.size(); ++i) {
                            viewList.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Test(view);
                                }
                            });
                        }
                        break;
                    case R.id.chongxin:
                        GameManager.getInstance().load(4);
                        showIn();
                        break;
                    case R.id.exit_game:
                        System.exit(0);
                        break;
                }
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    //循环获得button组件
    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if(viewchild instanceof Button){
                    allchildren.add(viewchild);
                    //再次调用本身（递归）
                }
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    @Override
    public void onExit() {

    }
}


