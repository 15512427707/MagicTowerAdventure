package wang.com.M_tower;

public class GamePlayConstants {
    //类变量 且是常量 的 int型 游戏循环帧率 = 30;
    public final static int GAME_LOOP_FRAME_RATE = 30;
    //类变量 且是常量 的 int型 游戏循环时间 等于 1/游戏循环帧率 * 1000
    public final static long GAME_LOOP_TIME = (long) (((double)1 / GAME_LOOP_FRAME_RATE) * 1000);

    //地图宽度为11
    public final static int MAP_WIDTH = 11;

    //静态类，游戏状态码类
    public static class GameStatusCode
    {
        public final static int LOAD_GAME = 0;
        public final static int NEW_GAME = 1;
    }

    //静态类，游戏资源常数类
    public static class GameResConstants {
        //元素块的宽为32
        public final static int MAP_META_WIDTH = 32;
        //元素块的高为32   暂时改为28 可以让它更高一点
        public final static int MAP_META_HEIGHT = 32;

        //怪物图 宽15个元素块
        public final static int MAP_SPRITE_WIDTH_COUNT = 15;
        //怪物图 高15
        public final static int MAP_SPRITE_HEIGHT_COUNT = 21;
        //英雄图 宽4
        public final static int HERO_SPRITE_WIDTH_COUNT = 4;
        //英雄图 高4
        public final static int HERO_SPRITE_HEIGHT_COUNT = 8;

        public static int MAP_SPRITE_FINAL_WIDTH = MAP_META_WIDTH;

    }

    //静态类，游戏值常数
    public static class GameValueConstants {
        public final static int STATIC_BEGIN = 0;
        public final static int STATIC_END = 66;

        //--------------基本地形--------------------
        public final static int GROUND = 0;
        public final static int WALL = 1;
        public final static int STONEWALL = 2;


        //--------------上下楼-------------------
        public final static int DOWN_STAIR = 4;
        public final static int UP_STAIR = 5;

        //-------------商店---------------------
        public final static int FIRST_STORE_LEFT = 6;
        public final static int FIRST_STORE_RIGHT = 7;
        public final static int SECOND_STORE_LEFT = 8;
        public final static int SECOND_STORE_RIGHT = 9;

        //--------------key---------------------
        public final static int YELLOW_KEY = 10;
        public final static int BLUE_KEY = 11;
        public final static int RED_KEY = 12;
        public final static int MAGIC_KEY = 13;

        //--------------宝石药水金币升级器----------------
        public final static int DEFENSE_BUFF = 14;
        public final static int ATTACK_BUFF = 15;
        public final static int CRIT_BUFF = 16;
        public final static int LITTLE_BLOOD = 17;
        public final static int BIG_BLOOD = 18;
        public final static int YINCANGWALL = 19;
        public final static int SUPER_BLOOD_DRUG = 20;
        public final static int CRIT_DAMAGE_DRUG = 21;
        public final static int SUPER_GOLD_COIN = 22;
        public final static int LUCK_GOLD_COIN = 23;
        public final static int LITTLE_UPDATE_WING = 24;
        public final static int MIDDLE_UPDATE_WING = 25;
        public final static int SUPER_UPDATE_WING = 26;

        //-------------武器和防具---------------------
        public final static int IRON_SWORD = 27;
        public final static int SILVER_SWORD = 28;
        public final static int KNIGHT_SWORD = 29;
        public final static int GRM_SWORD = 30;
        public final static int HOLY_SWORD = 31;
        public final static int IRON_SHIELD = 32;
        public final static int SILVER_SHIELD = 33;
        public final static int KNIGHT_SHIELD = 34;
        public final static int GRM_SHIELD = 35;
        public final static int HOLY_SHIELD = 36;

        //-------------道具---------------------
        public final static int MONSTER_MANUAL = 37;
        public final static int FLYING = 38;
        public final static int LUCKY = 39;
        public final static int BOOM = 40;
        public final static int CROSS = 41;
        public final static int HOLY_DRUG = 42;
        public final static int SUPER_PICK = 43;
        public final static int PICK = 44;
        public final static int HOLY_BADGE = 45;
        public final static int FREEZE_BADGE = 46;
        public final static int RED_STAFF = 47;
        public final static int QING_STAFF = 48;
        public final static int BLUE_STAFF = 49;
        public final static int TXT = 50;

        //--------------door--------------------
        public final static int IRON_GATE = 3;
        public final static int DOOR_BEGIN = 51;
        public final static int DOOR_END = 66;
        public final static int YELLOW_DOOR = 51;
        public final static int BLUE_DOOR = 52;
        public final static int RED_DOOR = 53;
        public final static int CONDITION_DOOR = 54;
        public final static int HIDDEN_WALL = 208;//隐藏墙

        public final static int DYNAMIC_BEGIN = 67;
        public final static int DYNAMIC_END = 85;
        //-------------动态地形---------------------
        public final static int FIRST_STORE_MID = 67;
        public final static int SECOND__STORE_MID = 69;
        public final static int STAR = 71;
        public final static int FIRE_GROUND = 73; //特殊，可以走
        public final static int FIRE = 75;
        public final static int DRAGON_WING_RIGHT = 77;
        public final static int DRAGON_WING_LEFT = 79;
        public final static int DRAGON_EAR_RIGHT = 81;
        public final static int DRAGON_HEAD = 83;
        public final static int DRAGON_EAR_LEFT = 85;

        //--------------怪物共45种-----------------
        public final static int MONSTER_ID_BEGIN =87 ; //怪物 开始87
        public final static int MONSTER_ID_END = 131;  //怪物 结束132

        public final static int BLACK_SLIME = 87; //黑色史莱姆
        public final static int SKELETON_SOLDIER= 88; //骷髅士兵
        public final static int BIG_BAT = 89;//大蝙蝠
        public final static int ZOMBIE = 90; //僵尸
        public final static int SKELETON_CAPTAIN= 91; //骷髅队长
        public final static int STONE_MAN = 92; // 石头人
        public final static int GRAY_WITCH= 93; // 灰衣女巫
        public final static int FRESHMAN_GUARD = 94; //下卫
        public final static int RED_BAT = 95;//红蝙蝠
        public final static int RED_WIZARD = 96;     //红法师
        public final static int SLIME_KING = 97; //史莱姆大王
        public final static int BLUE_DEVIL = 98; //蓝衣大法师
        public final static int YELLOW_KNIGHT = 99; //黄骑士
        public final static int RED_WITCH= 100; // 红衣女巫
        public final static int ZOMBIE_SOLDIER = 101; //僵尸兵
        public final static int MIDDLE_GUARD = 102; //中卫
        public final static int DOUBLE_SWORDSMAN = 103;     //双手剑客
        public final static int KNIGHT_CAPTAIN = 104; // 骑士队长
        public final static int RED_KNIGHT = 105; //红骑士
        public final static int SPIRIT_MASTER = 106; // 灵法师
        public final static int SKELETON_GENERAL= 107; //骷髅将军
        public final static int DEAD_KNIGHT = 108; //死亡骑士
        public final static int SHADOW_MAN = 109; //影子杀手
        public final static int RED_DEVIL = 110; //红衣大法师
        public final static int GOLD_DEVIL = 111; //黄金大法师
        public final static int FAKE_PRINCESS = 112; // 假公主
        public final static int SKELETON_BOSS = 113; //骷髅BOSS
        public final static int WHITE_DEVIL = 114; // 白衣大法师
        public final static int RIGHT_UPHOLD = 115; // 右护法
        public final static int LEFT_UPHOLD = 116; // 左护法
        public final static int BIG_BOSS = 117;     //大魔王
        public final static int GOLD_SWORDSMAN = 118;     //黄金剑客
        public final static int PT_BOSS = 119; //铂金骷髅
        public final static int GREEN_ZOMBIE = 120; //绿尸
        public final static int GOLD_GUARD = 121; //黄金卫
        public final static int DRAGON_LEG_RIGHT = 122; //龙的右腿
        public final static int DRAGON_LEG_LEFT = 123; //龙的左腿
        public final static int DRAGON_FACE = 124; //龙的脸
        public final static int GREEN_SLIME = 125; //绿色史莱姆
        public final static int RED_SLIME = 126; //红色史莱姆
        public final static int LITTLE_BAT = 127;  //小蝙蝠
        public final static int SKELETON = 128; //骷髅
        public final static int PURPLE_WIZARD = 129;     //紫法师
        public final static int PURPLE_BAT = 130;//紫蝙蝠
        public final static int GOLD_BIG_BOSS = 131;     //黄金大魔王


        //-------------npc----------------------
        public final static int NPC_BEGIN = 177; //npc长者
        public final static int NPC_END = 185;

        public final static int NPC_ELDER = 177; //npc长者
        public final static int NPC_TRADER = 179;
        public final static int NPC_MAIDEN = 181;
        public final static int NPC_MAIDEN_SISTER = 183;
        public final static int NPC_THIEF = 185;

        public final static int HERO_BEGIN = 225; //npc长者
        public final static int HERO_END = 240;
        public final static int HERO_forward = 225;
        public final static int HERO_forward1 = 226;
        public final static int HERO_forward2 = 228;
        public final static int HERO_left = 229;
        public final static int HERO_left1 = 230;
        public final static int HERO_left2 = 232;
        public final static int HERO_right = 233;
        public final static int HERO_right1 = 234;
        public final static int HERO_right2 = 236;
        public final static int HERO_back = 237;
        public final static int HERO_back1 = 238;
        public final static int HERO_back2 = 240;
    }

    //静态类 装备码
    public static class EquipmentCode{
        //类变量 且是常量 的 int型变量 点击怪物手册 等于1
        public final static int BOOK_CLICK = 1;
        public final static int TRAN_CLICK = 2;
        public final static int NOTE_CLICK = 3;
        public final static int FROZEN_CLICK = 4;
        public final static int PICK_CLICK = 5;
        public final static int HOLY_WATER_CLICK = 6;
        public final static int MAGIC_KEY_CLICK = 7;
        public final static int BOOM_CLICK = 8;
        public final static int UP_CLICK = 9;
        public final static int DOWN_CLICK = 10;
        //类变量 且是常量 的 int型变量 怪物手册 等于1
        public final static int BOOK = 10000;
    }

    //静态类 英雄状态码
    public static class HeroStatusCode{
        //类变量 且是常量 的 int型变量 英雄平常 等于0
        public final static int HERO_NORMAL = 0;
        //类变量 且是常量 的 int型变量 英雄战斗 等于1
        public final static int HERO_FIGHTING = 1;
    }

    //静态类 战斗码
    public static class BattleCode{
        //类变量 且是常量 的 int型变量 不能攻击码 等于1
        public final static int CANT_ATK = -1;
    }

    //静态类 移动状态码
    public static class MoveStatusCode
    {
        //类变量 且是常量 的 int型变量 移动成功 等于 0
        public final static int MOVE_SUCCESS_CODE = 0;
        //类变量 且是常量 的 int型变量 没有黄钥匙 等于1
        public final static int NO_YELLOW_KEY = 1;
        //类变量 且是常量 的 int型变量 没有红钥匙 等于2
        public final static int NO_RED_KEY = 2;
        //类变量 且是常量 的 int型变量 没有蓝钥匙 等于3
        public final static int NO_BLUE_KEY = 3;
        //类变量 且是常量 的 int型变量 不能到达 等于4
        public final static int CANT_REACH = 4;
        //类变量 且是常量 的 int型变量 移动到楼层 等于5
        public final static int MOVE_FLOOR = 5;
        //类变量 且是常量 的 int型变量 打不过 等于6
        public final static int FIGHT_CANT = 6;
        //类变量 且是常量 的 int型变量 战斗成功 等于7
        public final static int FIGHT_SUCCESS = 7;
        //类变量 且是常量 的 int型变量 商店 等于8
        public final static int SHOPPING = 8;
        //类变量 且是常量 的 int型变量 与老者对话 等于9
        public final static int TALKING_WITH_ELDER = 9;
        public final static int TALKING_WITH_TRADER = 10;
        public final static int TALKING_WITH_THIEF = 11;
        public final static int TALKING_WITH_SPIRIT_ONE = 12;
        public final static int TALKING_WITH_SPIRIT_TWO = 13;
        //类变量 且是常量 的 int型变量 商店 等于14
        public final static int SHOPPING2 = 14;
    }

    //静态类 移动状态码
    public static class MoneyCode
    {
        public final static int ONE = -5;
        public final static int TWO = -6;
        public final static int THREE = -7;
        public final static int FOUR = -8;
        public final static int FIVE = -9;
        public final static int SIX = -10;
    }
}
