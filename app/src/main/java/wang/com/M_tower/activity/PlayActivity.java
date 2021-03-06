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
    //?????????????????????????????????????????????
    private Button menu;
    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;
    //?????????????????????
    private Boolean ispress;
    private int mutex;
    private int isgoing;
    private int touchmutex;
    private int signalup;
    private int signaldown;
    private int signalleft;
    private int signalright;
    //???????????????????????????
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
    //???????????????????????????
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
    //??????????????????
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
        //??????Dialog?????????????????????
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
        //??????????????????
        win = talkDialog.getWindow();
        Window win1 =  talkDialog1.getWindow();
        params1 = new WindowManager.LayoutParams();
        params = new WindowManager.LayoutParams();
        params1.x = -20;
        params1.y = 100;
        params.x = -20;//??????x??????
        params.y = -400;//??????y??????
        win1.setAttributes(params1);
        win.setAttributes(params);
        talkDialog.setCanceledOnTouchOutside(true);//????????????Dialog????????????????????????Dialog
        //??????????????????????????????????????????????????????
        fightDialog.setCanceledOnTouchOutside(false);
        shoppingDialog.setCanceledOnTouchOutside(true);
        trderDialog.setCanceledOnTouchOutside(true);
        saveDialog.setCanceledOnTouchOutside(true);
        readDialog.setCanceledOnTouchOutside(true);
        flyDialog.setCanceledOnTouchOutside(true);
        //??????????????????
        ispress = false; //??????????????????
        mutex = 1; //??????????????? ??????????????????????????????
        isgoing = 0;
        touchmutex = 1;//?????????????????????????????????????????????
        signalup = 0; //????????????
        signaldown = 0;//????????????
        signalleft = 0;//????????????
        signalright = 0;//????????????
        //?????????Activity?????????????????????

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        //????????????????????????
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
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        mapController = new MapController(this);
        person = new Person(this, mapController,GamePlayConstants.HeroStatusCode.HERO_NORMAL);
        manualController = new ManualController(person, mapController);
        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        mapView = new MapView(this, mapController, person, dm.widthPixels);
        toolsView = new ToolsView(this, person,dm.widthPixels);
        showView = new ShowView(this,manualController, mapView,dm.widthPixels);
        //????????????????????????????????????????????????????????????????????????????????????????????????GameManager???????????????????????????
        GameManager.getInstance().register(mapController);
        GameManager.getInstance().register(person);
        ActivityCompat.requestPermissions(this, new String[]{android
                .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
        //???????????????????????????????????????NEW_GAME
        int code = getIntent().getIntExtra("code",GamePlayConstants.GameStatusCode.NEW_GAME);
        if(code == GamePlayConstants.GameStatusCode.LOAD_GAME)
        {
            //???????????????????????????????????????????????????????????????????????? Mark  eorry.
            GameManager.getInstance().load(0);
        }else{
            GameManager.getInstance().save(4);
        }
        //????????????????????? ????????? layout??????gv_main
        mainGameView = findViewById(R.id.gv_main);
        //??????????????????????????????register????????????????????????????????????????????????????????????????????? IGameview?????????????????????
        mainGameView.register(mapView);
        mainGameView.register(toolsView);
        mainGameView.register(showView);

        //???????????? ??????????????????
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
                    ApplicationUtil.toast(PlayActivity.this, "????????????????????????");
                } else if (msg.what == -5) {
                    showIn();
                } else if (msg.what == 2) {
                    ApplicationUtil.toast(PlayActivity.this, "???????????????");
                } else if (msg.what == 3) {
                    talkDialog.show();
                    TextView talk = talkDialog.findViewById(R.id.talktext);
                    ImageView imageView = talkDialog.findViewById(R.id.npc_image);
                    imageView.setImageBitmap(monsterImage);
                    talk.setTextSize(20);
                    talk.setText(":??????????????????????????????????????????");
                    //isFighting = false;
                } else if (msg.what == 4) {
                    ApplicationUtil.toast(PlayActivity.this, "???????????????");
                } else if (msg.what == 5) {
                    ApplicationUtil.toast(PlayActivity.this, "???????????????");
                } else if (msg.what == 50) {
                    TextView fighthp = fightDialog.findViewById(R.id.fighthp);
                    fighthp.setText(String.valueOf(monsterhp));
                }//??????????????????
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
                        talk1.setText("????????? 50????????????1???????????????????????????");
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
                        talk1.setText("????????? 50????????????5???????????????????????????");
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
                        talk1.setText("????????? 800???????????????????????????????????????");
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
                    ApplicationUtil.log("???????????????","???????????????");
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

        //??????????????????
        mainGameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //?????????????????? ????????????????????? ?????????
                    v.performClick();
                    talkDialog.dismiss();
                    flyDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    //?????????????????? x,y??????????????????
                    float x = event.getX();
                    float y = event.getY();
                    //int ???????????? =  (int) ???x/????????????????????????????????????
                    int widthIndex = (int) (x / mapView.getSpriteWidth());
                    //int ???????????? =  (int) ???y/????????????????????????????????????
                    final int heightIndex = (int) ((y - 260) / mapView.getSpriteWidth() / 1.1);
                    ApplicationUtil.log("????????????", mapView.getSpriteWidth());
                    //???????????????
                    if(manualController.isShowing()){
                        showView.onClick(x,y);
                        return false;
                    }
                    if(touchmutex == 1) {
                        int code = toolsView.onClick(x, y);
                        if (code == GamePlayConstants.EquipmentCode.BOOK_CLICK) {
                            //????????????????????????????????????????????????start ?????????
                            ApplicationUtil.log("????????????", "?????????");
                            manualController.start();
                        } else if (code == GamePlayConstants.EquipmentCode.UP_CLICK) {
                            ApplicationUtil.log("??????", "?????????");
                            if(curLevel + 1 <= 36){
                                if(person.personAttribute.floor[curLevel + 1] == true){
                                    mapController.upStairs();
                                    showIn();
                                    person.findPersonLocation();
                                }else{
                                    ApplicationUtil.toast(PlayActivity.this,"??????????????????");
                                }
                            }
                        } else if (code == GamePlayConstants.EquipmentCode.DOWN_CLICK) {
                            ApplicationUtil.log("??????", "?????????");
                            mapController.downStairs();
                            showIn();
                            person.findPersonLocation();
                        } else if (code == GamePlayConstants.EquipmentCode.TRAN_CLICK) {
                            ApplicationUtil.log("???????????????", "?????????");
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
                            ApplicationUtil.log("??????", "?????????");
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
                            ApplicationUtil.log("????????????", "?????????");
                            for (int i = 0; i < mapController.getMap().getMap().length / GamePlayConstants.MAP_WIDTH; i++) {
                                for (int j = 0; j < GamePlayConstants.MAP_WIDTH; j++) {
                                    if (mapController.getMap().getMap()[i * GamePlayConstants.MAP_WIDTH + j] == 51) {
                                        mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.GROUND);
                                    }
                                }
                            }
                            person.personAttribute.tools[7] = false;
                        } else if (code == GamePlayConstants.EquipmentCode.BOOM_CLICK) {
                            ApplicationUtil.log("??????", "?????????");
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
                    //????????????????????? ??????????????????????????? ????????????????????????????????????return false
                    if(widthIndex >= GamePlayConstants.MAP_WIDTH || heightIndex >= GamePlayConstants.MAP_WIDTH)
                        return false;
                    //????????????????????? ??????????????????????????? ????????????????????????????????????return false
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
                                           // ApplicationUtil.log("??????", "??????");
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
                                                            //?????????????????????
                                                            mHandler.sendEmptyMessage(50);
                                                            //?????????????????????
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


                                        ApplicationUtil.log("??????", "??????");
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
                //??????????????? false
                return false;
            }
        });

        //????????? ?????????????????????
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(menu);
            }
        });

        showIn();

        //??????????????????
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
                                mutex = 0; //????????????
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
                                                              //  ApplicationUtil.log("????????????","UI");
                                                                //?????????
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
                                                                //???????????????
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
                                            Thread.sleep(100);//??????100???????????????100ms????????????
                                            isgoing = 0;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } } } } };
                        if(mutex == 1){  //????????????????????????
                            signalright = 1;
                            tright.start(); //????????????
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
                                                           //     ApplicationUtil.log("????????????","UI");
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
                                                                //???????????????
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
                                            Thread.sleep(100);//??????50??????????????????????????? ???????????????????????????
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
                                                              //  ApplicationUtil.log("????????????","UI");
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
                                                                //???????????????
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
                                            Thread.sleep(100);//??????50??????????????????????????? ???????????????????????????
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
                                                                //???????????????
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
                                            Thread.sleep(100);//??????50??????????????????????????? ???????????????????????????
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
       // ApplicationUtil.log("??????showIn??????", "??????");
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
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addHPbig();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 12:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setHp(person.personAttribute.getHp() + 3000);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 22:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setHp(person.personAttribute.getHp() + 25000);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.addAtt:
                switch (curLevel){
                    case 4:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addatk();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 12:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setAtk(person.personAttribute.getAtk() + 22);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 22:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setAtk(person.personAttribute.getAtk() + 170);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.addDef:
                switch (curLevel){
                    case 4:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addDef();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 12:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setDef(person.personAttribute.getDef() + 22);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 22:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setDef(person.personAttribute.getDef() + 170);
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.addAdd:
                switch (curLevel){
                    case 4:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 20){
                            person.personAttribute.addDamage();
                            person.personAttribute.subCoin();
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 12:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 100){
                            person.personAttribute.setAddDamage((float) (person.personAttribute.getAddDamage() + 0.06));
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 100);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                    case 22:
                        //???????????????
                        MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                        if(person.personAttribute.getCoin() >= 800){
                            person.personAttribute.setAddDamage((float) (person.personAttribute.getAddDamage() + 0.25));
                            person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                        }else {
                            ApplicationUtil.toast(this,"???????????????????????????");
                        }
                        showIn();
                        break;
                }
                break;
            case R.id.buy:
                //???????????????
                MainActivity.soundPool.play(MainActivity.soundMap.get(1), 1, 1, 0, 0, 1);
                switch (curLevel){
                    case 6:
                            if(person.personAttribute.getCoin() >= 50){
                                person.personAttribute.addBlueKey();
                                person.personAttribute.setCoin(person.personAttribute.getCoin() - 50);
                                person.personAttribute.talk[3] = true;
                                trderDialog.dismiss();
                            }else {
                                ApplicationUtil.toast(this,"?????????????????????????????????");
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
                                ApplicationUtil.toast(this,"???????????????????????????????????????");
                            }
                        break;
                    case 12:
                            if(person.personAttribute.getCoin() >= 800){
                                person.personAttribute.addRedKey();
                                person.personAttribute.setCoin(person.personAttribute.getCoin() - 800);
                                person.personAttribute.talk[5] = true;
                                trderDialog.dismiss();
                            }else {
                                ApplicationUtil.toast(this,"?????????800???????????????????????????????????????");
                            }
                        break;
                }
                showIn();
                break;
            case R.id.save0:
                GameManager.getInstance().save(0);
                ApplicationUtil.toast(this,"????????????????????? ?????????");
                break;
            case R.id.save1:
                GameManager.getInstance().save(1);
                ApplicationUtil.toast(this,"???????????????1 ?????????");
                break;
            case R.id.save2:
                GameManager.getInstance().save(2);
                ApplicationUtil.toast(this,"???????????????2 ?????????");
                break;
            case R.id.save3:
                GameManager.getInstance().save(3);
                ApplicationUtil.toast(this,"???????????????3 ?????????");
                break;
            case R.id.read0:
                int d = GameManager.getInstance().load(0);
                if(d == 0) {
                    ApplicationUtil.toast(this, "????????????0 ?????????");
                }else{
                    ApplicationUtil.toast(this, "????????????0 ????????????????????????");
                }
                showIn();
                break;
            case R.id.read1:
                int a = GameManager.getInstance().load(1);
                if(a == 0) {
                    ApplicationUtil.toast(this, "????????????1 ?????????");
                }else{
                    ApplicationUtil.toast(this, "????????????1 ????????????????????????");
                }
                showIn();
                break;
            case R.id.read2:
                int b = GameManager.getInstance().load(2);
                if(b == 0) {
                    ApplicationUtil.toast(this, "????????????2 ?????????");
                }else{
                    ApplicationUtil.toast(this, "????????????2 ????????????????????????");
                }
                showIn();
                break;
            case R.id.read3:
                int c = GameManager.getInstance().load(3);
                if(c == 0) {
                    ApplicationUtil.toast(this, "????????????3 ?????????");
                }else{
                    ApplicationUtil.toast(this, "????????????3 ????????????????????????");
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
                                    ApplicationUtil.toast(PlayActivity.this,"??????????????????");
                                }
                            } else {
                                ApplicationUtil.toast(this, "????????????????????????");
                            }
                        } else {
                            ApplicationUtil.toast(this, "???????????????");
                        }
                }
                break;
        }
    }
    //SendMessage??????
    public int sendMessage(int m){
        //??????????????????goToTarget??????????????????????????????????????????????????????toast???????????????
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

    //??????????????????????????????
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //??????home????????? ???????????????
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

    //??????????????????
    private void showPopupMenu(View view) {
        // View??????PopupMenu???????????????View?????????
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu??????
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        // menu???item????????????
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
        // PopupMenu????????????
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    //????????????button??????
    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if(viewchild instanceof Button){
                    allchildren.add(viewchild);
                    //??????????????????????????????
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


