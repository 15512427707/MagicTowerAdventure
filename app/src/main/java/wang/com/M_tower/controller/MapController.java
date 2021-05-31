package wang.com.M_tower.controller;

import android.os.Environment;

import java.io.File;

import wang.com.M_tower.GameManager;
import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.activity.MTBaseActivity;
import wang.com.M_tower.model.FloorMap;
import wang.com.M_tower.util.IOUtil;
import wang.com.M_tower.util.JsonUtil;

public class MapController implements IController {
    //声明 int型 楼层 赋初值为0
    private int level = 0;
    //声明 楼层地图 类型的数组 localMap
    private FloorMap[] localMap;  //总楼层

    //FloorController类的构造方法，参数：MT基类对象
    public MapController(MTBaseActivity context) {
        //楼层地图数组 赋值为 x, x 调用Json工具类的获取地图方法，参数（调用Json工具类的从资源库加载Json文件的方法，文件："floor.json"）
        localMap = JsonUtil.getMap(JsonUtil.loadJsonFromAsset(context, "floor.json"));
    }

    //getMap()方法 返回当前楼层对应的FloorMap对象
    public FloorMap getMap()
    {
        return localMap[level];
    }

    //在地图中获得值方法 参数（i，j）
    public int getValueInMap(int i,int j)
    {
        //返回  第level层调用getmap方法获得的数组 的第 i*地图宽度 + j + 1 个元素。
        return localMap[level].getMap()[i * GamePlayConstants.MAP_WIDTH + j];
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setValueInMap(int i ,int j,int value)
    {
        localMap[level].setValue(i,j,value);
    }

    //获得当前楼层方法，返回 level
    public int getCurrentFloor()
    {
        return level;
    }

    //上楼方法
    public void upStairs()
    {
        if(level <= 35){
            //楼层+1
            level++;
        }
        //游戏管理.获取单例对象调用save方法  ？
        GameManager.getInstance().save(0);
    }

    //传送到某一层
    public void trans(int floor)
    {
        level = floor;
    }

    //下楼方法
    public void downStairs()
    {
        //楼层-1
        if(level >= 1){
            level--;
        }
        //游戏管理.获取单例对象调用save方法  ？
        GameManager.getInstance().save(0);
    }


    @Override
    public void save(int id) {
        switch (id){
            case 0:
                //调用IO工具下的保存方法，参数：localMap类型的数组，，保存在user_floor.json中
                IOUtil.save(localMap,"user_floor.json");
                break;
            case 1:
                IOUtil.save(localMap,"user_floor1.json");
                break;
            case 2:
                IOUtil.save(localMap,"user_floor2.json");
                break;
            case 3:
                IOUtil.save(localMap,"user_floor3.json");
                break;
            case 4:
                IOUtil.save(localMap,"user_floor4.json");
                break;
        }
    }

    @Override
    public int load(int id) {
        switch (id){
            case 0:
                File dir = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file = new File(dir,"user_floor.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file.exists() || file.length() == 0) {
                    return -1;
                }
                localMap = IOUtil.load("user_floor.json", FloorMap[].class);
                return 1;
            case 1:
                File dir1 = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file1 = new File(dir1,"user_floor1.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file1.exists() || file1.length() == 0) {
                    return -1;
                }
                localMap = IOUtil.load("user_floor1.json", FloorMap[].class);
                return 1;
            case 2:
                File dir2 = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file2 = new File(dir2,"user_floor2.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file2.exists() || file2.length() == 0) {
                    return -1;
                }
                localMap = IOUtil.load("user_floor2.json", FloorMap[].class);
                return 1;
            case 3:
                File dir3 = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
                File file3 = new File(dir3,"user_floor3.json");
                //创建输入流is对象，表示以后输入到file中
                if(!file3.exists() || file3.length() == 0) {
                    return -1;
                }
                localMap = IOUtil.load("user_floor3.json", FloorMap[].class);
                return 1;
            case 4:
                localMap = IOUtil.load("user_floor4.json", FloorMap[].class);
                return 1;
        }
        return 0;
    }

}
