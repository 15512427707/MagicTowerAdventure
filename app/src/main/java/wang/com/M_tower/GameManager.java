package wang.com.M_tower;

import java.util.ArrayList;
import java.util.List;

import wang.com.M_tower.controller.IController;

public class GameManager {
    //单例模式对象。
    private static volatile  GameManager mInstance = null;
    public static GameManager getInstance()
    {
        if(mInstance == null) {
            synchronized (GameManager.class) {
                if (mInstance == null)
                    mInstance = new GameManager();
            }
        }
        return mInstance;
    }

    //定义一个私有的IController类型的 集合 controllerList
    private List<IController> controllerList = new ArrayList<>();

    public void register(IController controller)
    {
        controllerList.add(controller);
    }

    //保存方法。
    public void save(int id)
    {
        //增强for循环，controllerList集合中的所有控制器 依次调用 IController类下的save方法。
        for (IController controller : controllerList)
        {
            controller.save(id);
        }
    }

    //加载方法
    public int load(int id)
    {
        //增强for循环，controllerList集合中的所有控制器 依次调用 IController类下的load方法。
        for (IController controller : controllerList)
        {
            int a = controller.load(id);
            if(a == -1){
                return -1;
            }
        }
        return 0;
    }

}

