package wang.com.M_tower.controller;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.View.MapView;
import wang.com.M_tower.activity.MTBaseActivity;
import wang.com.M_tower.activity.MainActivity;
import wang.com.M_tower.activity.PlayActivity;
import wang.com.M_tower.model.AStarNode;
import wang.com.M_tower.model.FloorMap;
import wang.com.M_tower.model.PersonAttribute;
import wang.com.M_tower.model.Monster;
import wang.com.M_tower.model.MonsterAttribute;
import wang.com.M_tower.util.ApplicationUtil;
import wang.com.M_tower.util.IOUtil;
import wang.com.M_tower.util.JsonUtil;

public class Person implements IController{
    public PersonAttribute personAttribute;
    private MapController mapController;
    //声明两个私有 整数类型的变量 heroI,heroJ
    private int PersonI, PersonJ;
    //英雄控制器的构造方法
    //声明私有 哈希表 monsterMap<整数，怪物属性对象>
    private Map<Integer, MonsterAttribute> monsterMap;
    public int personStatus;

    public Person(MTBaseActivity context, MapController mapController, int personStatus) {
        this.mapController = mapController;
        this.personStatus = personStatus;

        //调用 找到英雄位置 方法
        findPersonLocation();
        //调用 新游戏方法
        newGame(context);
        //调用 初始化怪物属性 方法
        initMonsterAttribute(context);
    }

    //私有的新游戏方法 参数：基类
    private void newGame(MTBaseActivity context) {
        //Json工具类 调用 获得英雄属性方法，参数（Json工具类调用loadJsonFromAsset方法，参数（类，"hero.json"文件））赋值给英雄属性对象
        personAttribute = JsonUtil.getPersonAttribute(JsonUtil.loadJsonFromAsset(context, "hero.json"));
    }

    //初始化怪物属性
    private void initMonsterAttribute(MTBaseActivity context) {
        //Json工具类 调用 获得怪物方法，参数：（Json工具调用loadJsonFromAsset方法，参数（类，"monster.json"文件））赋值给怪物数组对象）
        Monster[] monster = JsonUtil.getMonster(JsonUtil.loadJsonFromAsset(context, "monster.json"));
        //创建哈希表————怪物表
        monsterMap = new HashMap<>();
        //增强for循环，把怪物id 和 怪物属性 一一对应起来
        for (Monster monster1 : monster) {
            monsterMap.put(monster1.getId(), monster1.getMonster());
        }

    }

    public MonsterAttribute getMonster(int id) {
        return monsterMap.get(id);
    }

    //战斗
    public int fight(int monster,int value1) {

        MonsterAttribute enemy = monsterMap.get(monster);
        assert enemy != null;
        int enemyHp = enemy.getHp();
        PlayActivity.monsterhp = enemyHp;
        PlayActivity.monsterImage = MapView.sprite[monster];

        if(personAttribute.getAtk() <= enemy.getDef()){
            PlayActivity.isFighting = false;
            return GamePlayConstants.MoveStatusCode.FIGHT_CANT;
        }
        PlayActivity.monsteratt = enemy.getAtk();
        PlayActivity.monsterdef = enemy.getDef();
        PlayActivity.monsteradd = enemy.getAddDamage();
        int heroDamage = (int)((personAttribute.getAtk() - enemy.getDef())* (1 + personAttribute.getAddDamage()));
        int enemyDamage = (int)((enemy.getAtk() - personAttribute.getDef()) * (1 + enemy.getAddDamage()));
        //英雄损失的生命为0
        int heroHpLose = 0;
        //当怪物的血量 >= 0 进入循环
        while(enemyHp > 0)
        {
            //怪物的血量等于怪物的血量减去英雄造成的伤害
            enemyHp -= heroDamage;
            //英雄损失的生命等于英雄已经损失的生命加上怪物造成的伤害
            heroHpLose += enemyDamage;
        }
        //如果英雄这次打怪损失的生命 >= 英雄当前的生命
        if(heroHpLose >= personAttribute.getHp()) {
            //返回不能攻击的常数值
            return GamePlayConstants.MoveStatusCode.FIGHT_CANT;
        }


        PlayActivity.isFighting = true;
        MapView.array[value1] = Boolean.TRUE;
        int enemyHp1 = enemy.getHp();

        while(enemyHp1 > 0)
        {
            int b = (int) (Math.random() * 100);
            if(b % 7 == 0){
                MainActivity.soundPool.play(MainActivity.soundMap.get(8), 1, 1, 0, 0, 1);
            }else {
                //战斗的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(5), 1, 1, 0, 0, 1);
                MainActivity.soundPool.play(MainActivity.soundMap.get(0), 1, 1, 0, 0, 1);
            }

            //MainActivity.soundPool.play(MainActivity.soundMap.get(0), 1, 1, 0, 0, 1);

            personStatus = GamePlayConstants.HeroStatusCode.HERO_FIGHTING;
            //英雄打怪物
            enemyHp1 -= heroDamage;
            if(enemyHp1 > 0){
                PlayActivity.monsterhp = enemyHp1;
            }

            //怪物打英雄
            ApplicationUtil.log("提示","正在战斗。。");
            if(enemyDamage > 0) {
                personAttribute.setHp(personAttribute.getHp() - enemyDamage);
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        personStatus = GamePlayConstants.HeroStatusCode.HERO_NORMAL;
        personAttribute.setCoin(personAttribute.getCoin() + enemy.getCoin());
        personAttribute.setExp(personAttribute.getExp() + enemy.getExp());
        PlayActivity.isFighting = false;
        PlayActivity.doing = false;

        MapView.array[value1] = Boolean.FALSE;
        MapView.exp = enemy.getExp();
        MapView.coin = enemy.getCoin();
        MapView.ShowCode =  GamePlayConstants.MoveStatusCode.FIGHT_SUCCESS;
        ApplicationUtil.log("英雄等级", personAttribute.getLv());
        ApplicationUtil.log("英雄经验", personAttribute.getExp());
        if(monster == 124){
            mapController.setValueInMap((value1 / 11) - 1,value1%11,39);
            mapController.setValueInMap((value1 / 11) - 2,value1%11,39);
            mapController.setValueInMap(value1 / 11,(value1%11) + 1,39);
            mapController.setValueInMap(value1 / 11,(value1%11) - 1,40);
            mapController.setValueInMap((value1 / 11) - 1,(value1%11) + 1,39);
            mapController.setValueInMap((value1 / 11) - 1,(value1%11) - 1,39);
            mapController.setValueInMap((value1 / 11) - 2,(value1%11) + 1,39);
            mapController.setValueInMap((value1 / 11) - 2,(value1%11) - 1,39);
        }
        if(personAttribute.getExp() >= 10 * personAttribute.getLv() + personAttribute.getLv()* personAttribute.getLv()/2){
            MainActivity.soundPool.play(MainActivity.soundMap.get(10), 1, 1, 1, 0, 1);
            MapView.ShowCode = GamePlayConstants.MoneyCode.SIX;
            personAttribute.setExp(personAttribute.getExp() - 10 * personAttribute.getLv() - personAttribute.getLv()* personAttribute.getLv()/2);
            personAttribute.setLv(personAttribute.getLv() + 1);
            personAttribute.setAtk(personAttribute.getAtk() + 1* personAttribute.getLv());
            personAttribute.setDef(personAttribute.getDef() + 1* personAttribute.getLv());
            personAttribute.setHp(personAttribute.getHp() + 100* personAttribute.getLv());
        }

        return GamePlayConstants.MoveStatusCode.FIGHT_SUCCESS;
    }

    //找到英雄的位置
    public int findPersonLocation() {
        int k = 0;
        //楼层控制器 调用 getMap() 方法 ，赋值给 楼层地图类 的对象 map。
        FloorMap map = mapController.getMap();
        //楼层地图对象 map 调用 getMap 方法 获得一个地图数组，通过for循环
        for (int i = 0; i < map.getMap().length; i++) {
            //如果地图数组中的图片 等于 99
            switch (map.getMap()[i]) {
                case GamePlayConstants.GameValueConstants.HERO_forward:
                case GamePlayConstants.GameValueConstants.HERO_forward1:
                case GamePlayConstants.GameValueConstants.HERO_forward2:
                case GamePlayConstants.GameValueConstants.HERO_left:
                case GamePlayConstants.GameValueConstants.HERO_left1:
                case GamePlayConstants.GameValueConstants.HERO_left2:
                case GamePlayConstants.GameValueConstants.HERO_right:
                case GamePlayConstants.GameValueConstants.HERO_right1:
                case GamePlayConstants.GameValueConstants.HERO_right2:
                case GamePlayConstants.GameValueConstants.HERO_back:
                case GamePlayConstants.GameValueConstants.HERO_back1:
                case GamePlayConstants.GameValueConstants.HERO_back2:
                    //英雄行 = i /  地图宽度
                    PersonI = i / GamePlayConstants.MAP_WIDTH;
                    //英雄列 = i - 地图宽度 * 行数
                    PersonJ = i % GamePlayConstants.MAP_WIDTH;
                    k = i;
                    //应用工具类 调用 log方法 输出 行和列
                    ApplicationUtil.log("heroI", PersonI);
                    ApplicationUtil.log("heroJ", PersonJ);
                    break;
            }
        }
        return k;
    }

    //走到目标的方法
    public int goToTarget(int i,int j) throws InterruptedException {
        //boolean canMoveTo = canBeTarget(new AStarNode(i,j,false));
        //if(!canMoveTo)
        //   return GamePlayConstants.MoveStatusCode.CANT_REACH;
        //开始点为英雄的位置
        AStarNode start = new AStarNode(PersonI, PersonJ, true);
        //终点为点击的位置
        AStarNode end = new AStarNode(i, j, true);
        //调用找到路径的方法，返回一个路径集合
        List<AStarNode> road = findPath(start, end);
        if (road == null) {
            return GamePlayConstants.MoveStatusCode.CANT_REACH;
        }
        //for循环，每个循环走一步。
        for(AStarNode node : road)
        {
            int code = move(node.getI(),node.getJ());
            if(code != GamePlayConstants.MoveStatusCode.MOVE_SUCCESS_CODE)
                return code;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return GamePlayConstants.MoveStatusCode.MOVE_SUCCESS_CODE;
    }

    //判断可不可以走，如果是墙则不可以走
    private boolean canBeTarget(AStarNode node) {
        int value = mapController.getValueInMap(node.getI(), node.getJ());
        if(value == GamePlayConstants.GameValueConstants.WALL || value == GamePlayConstants.GameValueConstants.CONDITION_DOOR
                || value == GamePlayConstants.GameValueConstants.UP_STAIR || value == GamePlayConstants.GameValueConstants.DOWN_STAIR
        || value == GamePlayConstants.GameValueConstants.STONEWALL){
            return false;
        }else if(value == GamePlayConstants.GameValueConstants.YELLOW_DOOR || value == GamePlayConstants.GameValueConstants.BLUE_DOOR
                ||  value == GamePlayConstants.GameValueConstants.RED_DOOR || value == GamePlayConstants.GameValueConstants.DOWN_STAIR)
        {
            return false;
        }else if(value >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN && value <= GamePlayConstants.GameValueConstants.MONSTER_ID_END)
        {
            return false;
        }else if(value == GamePlayConstants.GameValueConstants.STAR || value == GamePlayConstants.GameValueConstants.FIRE
        || value == 122 || value == 123){
            return false;
        }
        return true;
    }

    //找到路径方法
    private List<AStarNode> findPath(AStarNode start, AStarNode end)
    {

        List<AStarNode> openSet = new ArrayList<>();

        List<AStarNode> closeSet = new ArrayList<>();

        openSet.add(start);
        //主循环
        while (!openSet.isEmpty())
        {
            int index = 0;
            //当前结点为openset中的第一个
            AStarNode currentNode = openSet.get(index);

            //for循环 每次循环选出一个 它到终点的距离 + 父到终点 + 爷到终点的距离 + 。。。最小的点。
            for(int i = 1;i < openSet.size();i++)
            {

                //如果当前结点的 fCost > openSet中的某个结点的 fCost 或者 如果当前结点的 fCost = openSet中的某个结点的 fCost，但是当前结点的 hCost > i结点的hCost
                if(currentNode.getfCost() > openSet.get(i).getfCost() ||
                        (currentNode.getfCost() == openSet.get(i).getfCost() && openSet.get(i).gethCost() < currentNode.gethCost()))
                {
                    currentNode = openSet.get(i);
                    index = i;
                }
            }

            //移除这个点
            openSet.remove(index);

            //这个集合加入这个点
            closeSet.add(currentNode);

            //如果当前点等于终点 返回路径
            if(currentNode.isTheSame(end))
            {
                return getRoad(start,currentNode);
            }

            //把当前点它相邻的结点加入到 openset 中，他们的 gCost 一样， hCost不一样
            for(AStarNode node : getNeighbour(currentNode))
            {
                if(!node.isWalkable() || containsNode(closeSet,node))
                    continue;
                //newCost 等于 当前结点的gCost + 当前结点的hCost
                int newCost = currentNode.getgCost() + getDistance(currentNode,end);
                if(!containsNode(openSet,node))//newCost < node.getgCost() ||
                {
                    //新节点的gCost是父节点到终点的距离 + 父节点的父节点到终点的距离 + 父节点的父节点的父节点到终点的距离
                    node.setgCost(newCost);
                    //新节点的hCost指的是新结点到终点的距离。
                    //新节点的fCost 为新节点到终点的距离  + 父节点到终点的距离 + 父节点的父节点到终点的距离
                    node.sethCost(getDistance(node,end));
                    //设置父节点为当前节点
                    node.setParent(currentNode);
                    if(!containsNode(openSet,node))
                        //加入到openset中
                        openSet.add(node);
                }
            }
        }
        return null;
    }

    private List<AStarNode> getRoad(AStarNode start, AStarNode end)
    {
        List<AStarNode> road = new ArrayList<>();
        AStarNode cur = end;
        while(!cur.isTheSame(start))
        {
            //添加cur结点
            road.add(cur);
            //获得最终结点的父节点 赋值给cur
            cur = cur.getParent();
            //int value = floorController.getValueInMap(cur.getI(), cur.getJ());

        }
        //反转 然后返回路径集合
        Collections.reverse(road);
        return road;
    }

    private List<AStarNode> getNeighbour(AStarNode node)
    {
        List<AStarNode> neighbourList = new ArrayList<>();
        for(int i = -1;i <= 1;i++)
        {
            if (i == 0)
                continue;
            int checkX = node.getI() + i;
            //把node结点四周结点全部存进去。
            if (checkX >= 0 && checkX < GamePlayConstants.MAP_WIDTH) {
                neighbourList.add(new AStarNode(checkX, node.getJ(),
                        canBeTarget(node)));
            }

            int checkY = node.getJ() + i;
            if (checkY >= 0 && checkY < GamePlayConstants.MAP_WIDTH) {
                neighbourList.add(new AStarNode(node.getI(), checkY,
                        canBeTarget(node)));
            }
        }
        return neighbourList;
    }

    //判断闭集中是否包含 这个结点。
    private boolean containsNode(List<AStarNode> closeSet,AStarNode target)
    {
        for(AStarNode node : closeSet)
        {
            if(node.isTheSame(target))
                return true;
        }
        return false;
    }

    //获得距离方法 将来把距离赋值给 hCost
    private int getDistance(AStarNode start,AStarNode end)
    {
        //X轴上的距离
        int dstX = Math.abs(start.getI() - end.getI());
        //Y轴上的距离
        int dstY = Math.abs(start.getJ() - end.getJ());
        //没看懂 如果起点——终点 x轴上的距离 大于 起点——终点 y轴上的距离
        if(dstX > dstY) {
            //返回距离为 14*（起点——终点 y轴上的距离） + 10*（（起点——终点 x轴上的距离）-（起点——终点 y轴上的距离））
            //return 10 * dstY + 10 * (dstX - dstY);
            return dstY + dstX;
        }
        //如果起点——终点 x轴上的距离 小于 起点——终点 y轴上的距离
        //返回距离为 14*（起点——终点 x轴上的距离） + 10*（（起点——终点 y轴上的距离）-（起点——终点 x轴上的距离））
        //return 10 * dstX + 10 * (dstY - dstX);
        return dstY + dstX;
    }

    //人物移动
    public int move(int i ,int j) throws InterruptedException {
        int value = mapController.getValueInMap(i, j);
        switch (value) {
            case GamePlayConstants.GameValueConstants.WALL:
            case GamePlayConstants.GameValueConstants.STONEWALL:
            case GamePlayConstants.GameValueConstants.FIRE:
            case GamePlayConstants.GameValueConstants.STAR:
            case 122:
            case 123:
                return 0;
            case GamePlayConstants.GameValueConstants.YELLOW_KEY:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addYellowKey();
                break;
            case GamePlayConstants.GameValueConstants.YELLOW_DOOR:
                if (personAttribute.getYellowKey() > 0) {
                    //开门的声音
                    MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                    personAttribute.setYellowKey(personAttribute.getYellowKey() - 1);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.YELLOW_DOOR + 4);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.YELLOW_DOOR + 8);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.YELLOW_DOOR + 12);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.GROUND);
                    Thread.sleep(100);
                } else {
                    return GamePlayConstants.MoveStatusCode.NO_YELLOW_KEY;
                }
                break;
            case GamePlayConstants.GameValueConstants.BLUE_KEY:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addBlueKey();
                break;
            case GamePlayConstants.GameValueConstants.IRON_GATE:
            case GamePlayConstants.GameValueConstants.YINCANGWALL:
                MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                break;
            case GamePlayConstants.GameValueConstants.BLUE_DOOR:
                if (personAttribute.getBlueKey() > 0) {
                    //开门的声音
                    MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                    personAttribute.setRedKey(personAttribute.getBlueKey() - 1);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.BLUE_DOOR + 4);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.BLUE_DOOR + 8);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.BLUE_DOOR + 12);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.GROUND);
                    Thread.sleep(100);
                } else {
                    return GamePlayConstants.MoveStatusCode.NO_BLUE_KEY;
                }
                break;
            case GamePlayConstants.GameValueConstants.RED_DOOR:
                if (personAttribute.getRedKey() > 0) {
                    //开门的声音
                    MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                    personAttribute.setRedKey(personAttribute.getRedKey() - 1);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.RED_DOOR + 4);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.RED_DOOR + 8);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.RED_DOOR + 12);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.GROUND);
                    Thread.sleep(100);
                } else {
                    return GamePlayConstants.MoveStatusCode.NO_RED_KEY;
                }
                break;
            case GamePlayConstants.GameValueConstants.CONDITION_DOOR:
                if (mapController.getValueInMap(i + 1, j - 1) == GamePlayConstants.GameValueConstants.GROUND
                && mapController.getValueInMap(i + 1, j + 1) == GamePlayConstants.GameValueConstants.GROUND) {
                    //开门的声音
                    MainActivity.soundPool.play(MainActivity.soundMap.get(2), 1, 1, 0, 0, 1);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.CONDITION_DOOR + 4);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.CONDITION_DOOR + 8);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.CONDITION_DOOR + 12);
                    Thread.sleep(80);
                    mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.GROUND);
                    Thread.sleep(100);
                } else {
                    return 0;
                }
                break;
            case GamePlayConstants.GameValueConstants.RED_KEY:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addRedKey();
                break;
            case GamePlayConstants.GameValueConstants.BIG_BLOOD:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addHPbig();
                break;
            case GamePlayConstants.GameValueConstants.LITTLE_BLOOD:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addHPlittle();
                break;
            case GamePlayConstants.GameValueConstants.ATTACK_BUFF:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addatk();
                break;
            case GamePlayConstants.GameValueConstants.DEFENSE_BUFF:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addDef();
                break;
            case GamePlayConstants.GameValueConstants.CRIT_BUFF:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(3), 1, 1, 0, 0, 1);
                personAttribute.addDamage();
                break;
                //五种剑
            case GamePlayConstants.GameValueConstants.MONSTER_MANUAL:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[2] = true;
                break;
            case GamePlayConstants.GameValueConstants.CROSS:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[5] = true;
                break;
            case GamePlayConstants.GameValueConstants.IRON_SWORD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.swords[0] = true;
                personAttribute.setAtk(personAttribute.getAtk() + 20);
                MapView.ShowCode = GamePlayConstants.GameValueConstants.IRON_SWORD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.SILVER_SWORD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setAtk(personAttribute.getAtk() + 80);
                personAttribute.swords[1] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.SILVER_SWORD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.KNIGHT_SWORD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setAtk(personAttribute.getAtk() + 300);
                personAttribute.swords[2] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.KNIGHT_SWORD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.GRM_SWORD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setAtk(personAttribute.getAtk() + 1000);
                personAttribute.swords[3] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.GRM_SWORD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.HOLY_SWORD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setAtk(personAttribute.getAtk() + 3000);
                personAttribute.swords[4] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.HOLY_SWORD;
                MapView.time = 0;
                break;
                //五种盾
            case GamePlayConstants.GameValueConstants.IRON_SHIELD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setDef(personAttribute.getDef() + 20);
                personAttribute.shields[0] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.IRON_SHIELD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.SILVER_SHIELD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setDef(personAttribute.getDef() + 80);
                personAttribute.shields[1] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.SILVER_SHIELD;
                MapView.time = 0;
                break;
            case 277:
                //捡东西的声音
               return -999;
            case GamePlayConstants.GameValueConstants.KNIGHT_SHIELD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setDef(personAttribute.getDef() + 300);
                personAttribute.shields[2] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.KNIGHT_SHIELD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.GRM_SHIELD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setDef(personAttribute.getDef() + 1000);
                personAttribute.shields[3] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.GRM_SHIELD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.HOLY_SHIELD:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.setDef(personAttribute.getDef() + 3000);
                personAttribute.shields[4] = true;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.HOLY_SHIELD;
                MapView.time = 0;
                break;
            case GamePlayConstants.GameValueConstants.PICK:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[4] = true;
                break;
            case GamePlayConstants.GameValueConstants.FLYING:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[3] = true;
                break;
            case GamePlayConstants.GameValueConstants.BOOM:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[8] = true;
                break;
            case GamePlayConstants.GameValueConstants.FREEZE_BADGE:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[6] = true;
                break;
            case GamePlayConstants.GameValueConstants.HOLY_BADGE:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[9] = true;
                break;
            case GamePlayConstants.GameValueConstants.MAGIC_KEY:
                //捡东西的声音
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.tools[7] = true;
                break;

            case GamePlayConstants.GameValueConstants.LUCK_GOLD_COIN:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                personAttribute.addCoin();
                break;
            case GamePlayConstants.GameValueConstants.LUCKY:
                int k = (int)(Math.random()*100+1);
                if(k == 1){ // 1%几率获得大奖
                    MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                    MapView.ShowCode = GamePlayConstants.MoneyCode.ONE;
                    personAttribute.setAtk(personAttribute.getAtk() + 500);
                    personAttribute.setDef(personAttribute.getDef() + 500);
                }else if( k > 1 && k < 5){ //4%几率获得二等奖
                    MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                    MapView.ShowCode = GamePlayConstants.MoneyCode.TWO;
                    personAttribute.setCoin(personAttribute.getCoin() + 1000);
                }else if(k >= 5  && k < 30){ // 25%几率获得三等奖
                    MapView.ShowCode = GamePlayConstants.MoneyCode.THREE;
                    MainActivity.soundPool.play(MainActivity.soundMap.get(10), 1, 1, 1, 0, 1);
                    for(int v = 1;v <=3; ++v){
                        personAttribute.setLv(personAttribute.getLv() + 1);
                        personAttribute.setAtk(personAttribute.getAtk() + 1* personAttribute.getLv());
                        personAttribute.setDef(personAttribute.getDef() + 1* personAttribute.getLv());
                        personAttribute.setHp(personAttribute.getHp() + 100*1* personAttribute.getLv());
                    }
                }else if(k >= 30 && k < 60) { //30%几率获得四等奖
                    MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                    MapView.ShowCode = GamePlayConstants.MoneyCode.FOUR;
                    personAttribute.setAtk(personAttribute.getAtk() + 6);
                    personAttribute.setDef(personAttribute.getDef() + 6);
                }else if(k >= 60 && k <= 100) { //40%几率获得五等奖
                    MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                    MapView.ShowCode = GamePlayConstants.MoneyCode.FIVE;
                    personAttribute.setAtk(personAttribute.getAtk() + 3);
                    personAttribute.setDef(personAttribute.getDef() + 3);
                }
                break;
            case GamePlayConstants.GameValueConstants.SUPER_GOLD_COIN:
                MainActivity.soundPool.play(MainActivity.soundMap.get(4), 1, 1, 0, 0, 1);
                MapView.ShowCode = GamePlayConstants.GameValueConstants.SUPER_GOLD_COIN;
                MapView.time = 0;
                personAttribute.addBigCoin();
                break;
            case GamePlayConstants.GameValueConstants.LITTLE_UPDATE_WING:
                MainActivity.soundPool.play(MainActivity.soundMap.get(10), 1, 1, 1, 0, 1);
                MapView.ShowCode = GamePlayConstants.GameValueConstants.LITTLE_UPDATE_WING;
                MapView.time = 0;
                personAttribute.setLv(personAttribute.getLv() + 1);
                personAttribute.setAtk(personAttribute.getAtk() + 1* personAttribute.getLv());
                personAttribute.setDef(personAttribute.getDef() + 1* personAttribute.getLv());
                personAttribute.setHp(personAttribute.getHp() + 100*1* personAttribute.getLv());
                break;
            case GamePlayConstants.GameValueConstants.MIDDLE_UPDATE_WING:
                MainActivity.soundPool.play(MainActivity.soundMap.get(10), 1, 1, 1, 0, 1);
                MapView.ShowCode = GamePlayConstants.GameValueConstants.MIDDLE_UPDATE_WING;
                MapView.time = 0;
                for(int v = 1;v <= 3;++v) {
                    personAttribute.setLv(personAttribute.getLv() + 1);
                    personAttribute.setAtk(personAttribute.getAtk() + 1 * personAttribute.getLv());
                    personAttribute.setDef(personAttribute.getDef() + 1 * personAttribute.getLv());
                    personAttribute.setHp(personAttribute.getHp() + 100 * 1 * personAttribute.getLv());
                }
                break;
            case GamePlayConstants.GameValueConstants.SUPER_UPDATE_WING:
                MainActivity.soundPool.play(MainActivity.soundMap.get(10), 1, 1, 1, 0, 1);
                MapView.ShowCode = GamePlayConstants.GameValueConstants.SUPER_UPDATE_WING;
                MapView.time = 0;
                for(int v = 1;v <= 10;++v) {
                    personAttribute.setLv(personAttribute.getLv() + 1);
                    personAttribute.setAtk(personAttribute.getAtk() + 1 * personAttribute.getLv());
                    personAttribute.setDef(personAttribute.getDef() + 1 * personAttribute.getLv());
                    personAttribute.setHp(personAttribute.getHp() + 100 * 1 * personAttribute.getLv());
                }
                break;
            //上楼
            case GamePlayConstants.GameValueConstants.UP_STAIR:
                MainActivity.soundPool.play(MainActivity.soundMap.get(9), 1, 1, 0, 0, 1);
                personAttribute.floor[personAttribute.getCurLevel()+1] = true;
                mapController.upStairs();
                findPersonLocation();
                return GamePlayConstants.MoveStatusCode.MOVE_FLOOR;
            //下楼
            case GamePlayConstants.GameValueConstants.DOWN_STAIR:
                MainActivity.soundPool.play(MainActivity.soundMap.get(9), 1, 1, 0, 0, 1);
                mapController.downStairs();
                findPersonLocation();
                return GamePlayConstants.MoveStatusCode.MOVE_FLOOR;

            case GamePlayConstants.GameValueConstants.FIRST_STORE_MID:
            case GamePlayConstants.GameValueConstants.FIRST_STORE_RIGHT:
            case GamePlayConstants.GameValueConstants.FIRST_STORE_LEFT:
                return GamePlayConstants.MoveStatusCode.SHOPPING;
            case GamePlayConstants.GameValueConstants.SECOND__STORE_MID:
            case GamePlayConstants.GameValueConstants.SECOND_STORE_LEFT:
            case GamePlayConstants.GameValueConstants.SECOND_STORE_RIGHT:
                return GamePlayConstants.MoveStatusCode.SHOPPING2;
            case GamePlayConstants.GameValueConstants.NPC_ELDER:
                return GamePlayConstants.MoveStatusCode.TALKING_WITH_ELDER;
            case GamePlayConstants.GameValueConstants.NPC_TRADER:
                return GamePlayConstants.MoveStatusCode.TALKING_WITH_TRADER;
            case GamePlayConstants.GameValueConstants.NPC_MAIDEN:
                return GamePlayConstants.MoveStatusCode.TALKING_WITH_SPIRIT_ONE;
            case GamePlayConstants.GameValueConstants.NPC_MAIDEN_SISTER:
                return GamePlayConstants.MoveStatusCode.TALKING_WITH_SPIRIT_TWO;
            case GamePlayConstants.GameValueConstants.NPC_THIEF:
                return GamePlayConstants.MoveStatusCode.TALKING_WITH_THIEF;
    }
        if(value >= GamePlayConstants.GameValueConstants.DOOR_BEGIN && value <= GamePlayConstants.GameValueConstants.DOOR_END ){
            return 0;
        }
        if (value >= GamePlayConstants.GameValueConstants.MONSTER_ID_BEGIN &&
                value <= GamePlayConstants.GameValueConstants.MONSTER_ID_END) {
            if(value == GamePlayConstants.GameValueConstants.BIG_BOSS && personAttribute.talk[6] == false){
                return -80;
            }
            if(value == GamePlayConstants.GameValueConstants.GOLD_BIG_BOSS && personAttribute.talk[7] == false){
                return -81;
            }
            if (fight(value,i * 11 + j) != GamePlayConstants.MoveStatusCode.FIGHT_SUCCESS) {
                return GamePlayConstants.MoveStatusCode.FIGHT_CANT;
            }
            //Thread.sleep(30);


        }

        if (i - PersonI > 0)  //向下走
        {
            if(MapView.symbol != 1){
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.GROUND);
            }else{
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.FIRE_GROUND);
            }
            if(value == GamePlayConstants.GameValueConstants.FIRE_GROUND){
                MapView.symbol = 1;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.FIRE_GROUND;
                personAttribute.setHp(personAttribute.getHp() - 100);
            }else{
                MapView.symbol = 0;
            }
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_forward1);
            Thread.sleep(30);
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_forward2);

        }
        else if (i - PersonI < 0) //向上走
        {
            if(MapView.symbol != 1){
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.GROUND);
            }else{
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.FIRE_GROUND);
            }
            if(value == GamePlayConstants.GameValueConstants.FIRE_GROUND){
                MapView.symbol = 1;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.FIRE_GROUND;
                personAttribute.setHp(personAttribute.getHp() - 100);
            }else{
                MapView.symbol = 0;
            }
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_back1);
            Thread.sleep(30);
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_back2);

        }
        else if (j - PersonJ > 0)  //向右走
        {
            if(MapView.symbol != 1){
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.GROUND);
            }else{
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.FIRE_GROUND);
            }
            if(value == GamePlayConstants.GameValueConstants.FIRE_GROUND){
                MapView.symbol = 1;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.FIRE_GROUND;
                personAttribute.setHp(personAttribute.getHp() - 100);
            }else{
                MapView.symbol = 0;
            }
            //把英雄所在位置设置为向右走1
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_right1);
            //休息30s 完成一次刷新
            Thread.sleep(30);
            //把英雄所在位置设置为向右走2
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_right2);


        }
        else if (j - PersonJ < 0)  //向左走
        {
            if(MapView.symbol != 1){
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.GROUND);
            }else{
                mapController.setValueInMap(PersonI, PersonJ, GamePlayConstants.GameValueConstants.FIRE_GROUND);
            }
            if(value == GamePlayConstants.GameValueConstants.FIRE_GROUND){
                MapView.symbol = 1;
                MapView.ShowCode = GamePlayConstants.GameValueConstants.FIRE_GROUND;
                personAttribute.setHp(personAttribute.getHp() - 100);
            }else{
                MapView.symbol = 0;
            }
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_left1);
            Thread.sleep(30);
            mapController.setValueInMap(i, j, GamePlayConstants.GameValueConstants.HERO_left2);

        }
        PersonI = i;
        PersonJ = j;
        return GamePlayConstants.MoveStatusCode.MOVE_SUCCESS_CODE;
    }

    @Override
    public void save(int id) {
        switch (id){
            case 0:
                personAttribute.setCurLevel(mapController.getCurrentFloor());
                IOUtil.save(personAttribute,"user_heroAttr.json");
                break;
            case 1:
                personAttribute.setCurLevel(mapController.getCurrentFloor());
                IOUtil.save(personAttribute,"user_heroAttr1.json");
                break;
            case 2:
                personAttribute.setCurLevel(mapController.getCurrentFloor());
                IOUtil.save(personAttribute,"user_heroAttr2.json");
                break;
            case 3:
                personAttribute.setCurLevel(mapController.getCurrentFloor());
                IOUtil.save(personAttribute,"user_heroAttr3.json");
                break;

            case 4: //重新开始
                personAttribute.setCurLevel(mapController.getCurrentFloor());
                IOUtil.save(personAttribute,"user_heroAttr4.json");
                break;
        }

    }

    @Override
    public int load(int id) {
        switch (id){
            case 0:
                File dir = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file = new File(dir,"user_heroAttr.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file.exists() || file.length() == 0) {
                    return -1;
                }
                personAttribute = IOUtil.load("user_heroAttr.json", PersonAttribute.class);
                mapController.setLevel(personAttribute.getCurLevel());
                findPersonLocation();
                return 1;
            case 1:
                File dir1 = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file1 = new File(dir1,"user_heroAttr1.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file1.exists() || file1.length() == 0) {
                    return -1;
                }
                personAttribute = IOUtil.load("user_heroAttr1.json", PersonAttribute.class);
                mapController.setLevel(personAttribute.getCurLevel());
                findPersonLocation();
                return 1;
            case 2:
                File dir2 = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file2 = new File(dir2,"user_heroAttr2.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file2.exists() || file2.length() == 0) {
                    return -1;
                }
                personAttribute = IOUtil.load("user_heroAttr2.json", PersonAttribute.class);
                mapController.setLevel(personAttribute.getCurLevel());
                findPersonLocation();
                return 1;
            case 3:
                File dir3 = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file3 = new File(dir3,"user_heroAttr3.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file3.exists() || file3.length() == 0) {
                    return -1;
                }
                personAttribute = IOUtil.load("user_heroAttr3.json", PersonAttribute.class);
                mapController.setLevel(personAttribute.getCurLevel());
                findPersonLocation();
                return 1;
            case 4:
                personAttribute = IOUtil.load("user_heroAttr4.json", PersonAttribute.class);
                mapController.setLevel(personAttribute.getCurLevel());
                findPersonLocation();
                return 1;
        }
        return 0;
    }


    public PersonAttribute getPersonAttribute()
    {
        return personAttribute;
    }
}
