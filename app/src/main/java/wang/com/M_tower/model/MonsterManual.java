package wang.com.M_tower.model;

import wang.com.M_tower.GamePlayConstants;

public class MonsterManual {
    //int型 怪物id
    private int spriteId;
    //怪物属性类型的 monsterAttribute
    private MonsterAttribute monsterAttribute;
    //int型 伤害值
    private int damage;

    public MonsterManual() {
    }

    public MonsterManual(int spriteId, MonsterAttribute monsterAttribute) {
        this.spriteId = spriteId;
        this.monsterAttribute = monsterAttribute;
    }
    //构造方法
    public MonsterManual(int spriteId, MonsterAttribute monsterAttribute, int damage) {
        this.spriteId = spriteId;
        this.monsterAttribute = monsterAttribute;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    //获取怪物号
    public int getSpriteId() {
        return spriteId;
    }

    public void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
    }
    //获取怪物属性
    public MonsterAttribute getMonsterAttribute() {
        return monsterAttribute;
    }

    public void setMonsterAttribute(MonsterAttribute monsterAttribute) {
        this.monsterAttribute = monsterAttribute;
    }

    @Override
    //重写toString方法，返回一个字符串。
    public String toString() {

        if(damage != GamePlayConstants.BattleCode.CANT_ATK){
            return "    " + monsterAttribute.getHp() +
                    "     " +
                    monsterAttribute.getAtk() +
                    "     :" +
                    monsterAttribute.getDef() +
                    "     :" +
                    monsterAttribute.getCoin() +
                    "     " +
                    damage;
        }else{
            return "血量 :" + monsterAttribute.getHp() +
                    " 攻击 :" +
                    monsterAttribute.getAtk() +
                    " 防御 :" +
                    monsterAttribute.getDef() +
                    " 金币 :" +
                    monsterAttribute.getCoin() +
                    "打不过";
        }
    }
}
