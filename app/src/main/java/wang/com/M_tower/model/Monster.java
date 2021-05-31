package wang.com.M_tower.model;

public class Monster {
    //怪物号
    private int id;
    //怪物属性值
    private MonsterAttribute monster;

    public Monster() {
    }

    public Monster(int id,MonsterAttribute monster){
        this.id = id;
        this.monster = monster;
    }

    //返回id号
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //返回怪物属性对象
    public MonsterAttribute getMonster() {
        return monster;
    }

    public void setMonster(MonsterAttribute monster) {
        this.monster = monster;
    }
}
