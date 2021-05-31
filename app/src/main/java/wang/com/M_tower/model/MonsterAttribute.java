package wang.com.M_tower.model;

public class MonsterAttribute {
    //血量，攻击，防御，携带的金币，暴击率
    private String name;
    private int hp;
    private int atk;
    private int def;
    private int coin;
    private int exp;
    private float addDamage;

    public MonsterAttribute(String name,int hp, int atk, int def, int coin,int exp,float addDamage) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.coin = coin;
        this.exp = exp;
        this.addDamage = addDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
