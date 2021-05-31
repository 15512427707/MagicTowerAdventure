package wang.com.M_tower.model;

public class Goods {
    private int money,atk,def,hp,crit;

    public Goods(int money, int atk, int def, int hp,int crit) {
        this.money = money;
        this.atk = atk;
        this.def = def;
        this.hp = hp;
        this.crit = crit;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getCrit() {
        return crit;
    }

    public void setCrit(int crit) {
        this.crit = crit;
    }
}
