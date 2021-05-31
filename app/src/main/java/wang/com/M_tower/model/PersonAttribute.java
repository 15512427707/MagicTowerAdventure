package wang.com.M_tower.model;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.View.MapView;

public class PersonAttribute {
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
    public  Boolean[] tools;
    public  Boolean[] swords;
    public Boolean[] shields;
    public Boolean[] talk;
    public Boolean[] floor;
    public PersonAttribute(int hp, int atk, int def, int yellowKey, int blueKey, int redKey, int coin, int exp, float addDamage,
                           int curLevel, int lv, Boolean[] tools, Boolean[] swords, Boolean[] shields, Boolean[] talk,Boolean[] floor) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.yellowKey = yellowKey;
        this.blueKey = blueKey;
        this.redKey = redKey;
        this.coin = coin;
        this.exp = exp;
        this.addDamage = addDamage;
        this.curLevel = curLevel;
        this.lv = lv;
        this.tools = tools;
        this.swords = swords;
        this.shields = shields;
        this.talk = talk;
        this.floor = floor;
    }

    public void addYellowKey(){
        yellowKey++;
        MapView.ShowCode = GamePlayConstants.GameValueConstants.YELLOW_KEY;
        MapView.time = 0;
    }

    public void addRedKey()
    {
        redKey++;
        MapView.ShowCode = GamePlayConstants.GameValueConstants.RED_KEY;
        MapView.time = 0;
    }

    public void addBlueKey()
    {
        blueKey++;
        MapView.ShowCode = GamePlayConstants.GameValueConstants.BLUE_KEY;
        MapView.time = 0;
    }

    //捡血药方法
    public void addHPlittle()
    {
        hp += 200;
        MapView.ShowCode = GamePlayConstants.GameValueConstants.LITTLE_BLOOD;
        MapView.time = 0;
    }
    public void addHPbig()
    {
        hp += 500;
        MapView.ShowCode = GamePlayConstants.GameValueConstants.BIG_BLOOD;
        MapView.time = 0;
    }
    //捡宝石方法
    public void addatk()
    {
        atk += 4;
        MapView.ShowCode = GamePlayConstants.GameValueConstants.ATTACK_BUFF;
        MapView.time = 0;
    }
    public void addDef()
    {
        def += 4;
        MapView.ShowCode = GamePlayConstants.GameValueConstants.DEFENSE_BUFF;
        MapView.time = 0;
    }
    public void addDamage()
    {
        MapView.ShowCode = GamePlayConstants.GameValueConstants.CRIT_BUFF;
        MapView.time = 0;
        addDamage += 0.01;
    }

    public void addCoin()
    {
        MapView.ShowCode = GamePlayConstants.GameValueConstants.LUCK_GOLD_COIN;
        MapView.time = 0;
        coin += 100;
    }

    public void addBigCoin()
    {
        MapView.ShowCode = GamePlayConstants.GameValueConstants.SUPER_GOLD_COIN;
        MapView.time = 0;
        coin += 800;
    }

    public void subCoin()
    {
        coin -= 20;
    }
    public void addLevel()
    {
        lv += 1;
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getYellowKey() {
        return yellowKey;
    }

    public void setYellowKey(int yellowKey) {
        this.yellowKey = yellowKey;
    }

    public int getBlueKey() {
        return blueKey;
    }

    public void setBlueKey(int blueKey) {
        this.blueKey = blueKey;
    }

    public int getRedKey() {
        return redKey;
    }

    public void setRedKey(int redKey) {
        this.redKey = redKey;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public float getAddDamage() {
        return addDamage;
    }

    public void setAddDamage(float addDamage) {
        this.addDamage = addDamage;
    }

    public int getCurLevel() {
        return curLevel;
    }

    public void setCurLevel(int curLevel) {
        this.curLevel = curLevel;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }



}
