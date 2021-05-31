package wang.com.M_tower.util;
//导入谷歌下的Gson类
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import wang.com.M_tower.activity.MTBaseActivity;
import wang.com.M_tower.model.FloorMap;
import wang.com.M_tower.model.PersonAttribute;
import wang.com.M_tower.model.Monster;

public class JsonUtil {
    //声明并创建静态的Gson类型的对象  gson
    private static Gson gson = new Gson();

    //声明静态的 返回值为楼层地图型的数组 的方法getMap
    public static FloorMap[] getMap(String value)
    {
        return parseJson(value, FloorMap[].class);//返回 parseJson方法。返回一个FloorMap数组
    }

    //静态的parseJson方法，调用 gson下的（fromJson方法，参数：value 字符串，cls） 一个类 解析Json 返回一个这种类型的对象
    public static <T> T parseJson(String value,Class<T> cls)
    {
        return gson.fromJson(value,cls);
    }

    //静态的获取英雄属性的方法
    public static PersonAttribute getPersonAttribute(String value)
    {
        //返回英雄属性类对象
        return parseJson(value, PersonAttribute.class);
    }

    //静态的获取怪物属性的方法
    public static Monster[] getMonster(String value)
    {
        //返回怪物数组类 的 对象
        return parseJson(value, Monster[].class);
    }

    //资源库加载Json文件的方法，把Json文件加载成字符串 参数：基类类型的 context，字符串name
    public static String loadJsonFromAsset(MTBaseActivity context, String name) {
        //字符流对象 is
        InputStream is = null;
        //字符序列对象 StringBuilder
        StringBuilder stringBuffer = null;
        //context调用getAssets()方法从名为name的文件中获取资源放在输入流里
        try {
            is = context.getAssets().open(name);
            //创建一个StringBuilder类型的对象。
            stringBuffer = new StringBuilder();
            //创建byte类型的数组，大小为1024
            byte[] buf = new byte[1024];
            //定义int型变量byteCount
            int byteCount;
            // 从is每次读一个buf这么多的内容，并把它赋值给buf数组中，当他没有读完时。
            while ( (byteCount = is.read(buf)) != -1)
            {
                //首先创建一个字符串 内容是buf数组里从0到byteCount之间的字符串，添加到stringBuffer之后。
                stringBuffer.append(new String(buf,0,byteCount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //最后如果stringBuffer不为空，则把stringBuffer转为字符串，并返回。
        if(stringBuffer != null)
            return stringBuffer.toString();
        //否则返回空
        return null;
    }

    //静态方法 toJson 调用gson的toJson方法 参数：T类型的变量 map
    public static <T> String toJson(T map)
    {
        //返回 gson下的toJson方法，参数T类对象 转化为string
        return gson.toJson(map);
    }
}

