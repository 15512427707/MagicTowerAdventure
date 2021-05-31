package wang.com.M_tower.controller;

public class ShowController{

    //定义布尔型变量正在被展示为 false
    private boolean isShow = false;
    //定义开始方法，使正在被展示 为真
    public void start()
    {
        isShow = true;
    }
    //结束方法，使正在被展示 为false
    public void end()
    {
        isShow = false;
    }


    //是否被展示方法
    public boolean isShowing()
    {
        //返回一个布尔值
        return isShow;
    }
}
