package wang.com.M_tower.controller;

import java.util.ArrayList;
import java.util.List;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.model.PersonAttribute;
import wang.com.M_tower.model.MonsterAttribute;
import wang.com.M_tower.model.MonsterManual;

public class ManualController extends ShowController{
    // 声明 私有的 英雄控制器，楼层控制器，
    private Person person;
    private MapController mapController;
    //声明怪物手册类型的集合。
    private List<MonsterManual> monsterManualList;

    //装备控制器类的构造方法 参数：英雄控制对象 楼层控制对象
    public ManualController(Person person, MapController mapController)
    {
        this.person = person;
        this.mapController = mapController;
        //monsterManualList为动态数组。
        monsterManualList = new ArrayList<>();
    }

    @Override
    //怪物之书的开始方法
    public void start() {
        super.start();
        //调用加载怪物之书方法
        loadMonsterManual();
    }

    //加载怪物之书
    private void loadMonsterManual() {
        //清空monsterManualList数组
        monsterManualList.clear();
        //for循环 i 从 0 到 调用floorController的获取地图方法获得level层的FloorMap对象，再调用FloorMap对象的获取地图方法获得map数组  再调获取数组长度方法）
        for (int i = 0; i < mapController.getMap().getMap().length; i++) {
            //如果地图中含有怪物
            if (mapController.getMap().getMap()[i] >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN &&
                    mapController.getMap().getMap()[i] <= GamePlayConstants.GameValueConstants.MONSTER_ID_END) {
                //如果不包含这个怪物号，
                if(mapController.getMap().getMap()[i] == 124 && person.personAttribute.tools[9] == true){
                    person.getMonster(mapController.getMap().getMap()[i]).setDef(0);
                    person.getMonster(mapController.getMap().getMap()[i]).setAtk(10000);
                    person.getMonster(mapController.getMap().getMap()[i]).setHp(10000);
                }
                if (!hasContainId(mapController.getMap().getMap()[i]))
                {
                    if(mapController.getMap().getMap()[i] != 122 && mapController.getMap().getMap()[i] != 123){
                        monsterManualList.add(
                                new MonsterManual(
                                        mapController.getMap().getMap()[i],
                                        person.getMonster(mapController.getMap().getMap()[i]),
                                        calculateLoseBlood(person.getMonster(mapController.getMap().getMap()[i]))));
                    }
                }
            }
        }
    }

    //私有布尔类型的方法  是否包含这个怪物号
    private boolean hasContainId(int id)
    {
        for(int i = 0;i < monsterManualList.size();i++)
        {
            if(monsterManualList.get(i).getSpriteId() == id)
                return true;
        }
        return false;
    }

    //计算失去的血量。参数：怪物属性
    private int calculateLoseBlood(MonsterAttribute monsterAttribute)
    {
        //英雄控制器下 的 获得英雄属性方法的返回值 赋值给英雄属性类的对象 heroAttribute
        PersonAttribute personAttribute = person.getPersonAttribute();
        //给出怪物的血量值 攻击力 防御力
        int monsterHp = monsterAttribute.getHp(), monsterAtk = monsterAttribute.getAtk(),
                monsterDef = monsterAttribute.getDef();
        //给出英雄的攻击力 防御力
        int heroAtk = personAttribute.getAtk(),
                heroDef = personAttribute.getDef();
        //英雄造成的伤害等于 英雄的攻击力 减去 怪物的防御力
        int heroDamage = heroAtk - monsterDef;
        //如果英雄造成的伤害  <= 0 ，则不能攻击。
        if(heroDamage <= 0)
            return GamePlayConstants.BattleCode.CANT_ATK;

        //怪物造成的伤害等于 怪物的攻击力 减去 英雄的防御力
        int monsterDamage = monsterAtk - heroDef;
        //如果怪物造成的伤害  <= 0 ，则怪物造成的伤害赋值为0
        if(monsterDamage < 0)
            monsterDamage = 0;
        //英雄损失的生命为0
        int heroHp = 0;
        //当怪物的血量 >= 0 进入循环
        while(monsterHp >= 0)
        {
            //怪物的血量等于怪物的血量减去英雄造成的伤害
            monsterHp -= heroDamage;
            //英雄损失的生命等于英雄已经损失的生命加上怪物造成的伤害
            heroHp += monsterDamage;
        }
        //如果英雄这次打怪损失的生命 >= 英雄当前的生命
        if(heroHp >= personAttribute.getHp())
            //返回不能攻击的常数值
            return GamePlayConstants.BattleCode.CANT_ATK;
        //返回英雄损失的生命
        return heroHp;
    }

    //获得怪物手册类型的集合
    public List<MonsterManual> getMonsterManualList()
    {
        return monsterManualList;
    }
}
