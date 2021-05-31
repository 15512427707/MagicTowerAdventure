package wang.com.M_tower.util;

import android.os.Environment;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {
    //声明并创建 静态的 文件 dir  = 环境的外部存储目录 + /magicTower/
    private static File dir = new File(Environment.getExternalStorageDirectory() + "/magicTower/");
    //静态的加载文件方法。 参数：文件名，类
    public static <T> T load(String filName,Class<? extends T> cls)
    {
        //声明输入流 is
        InputStream is = null;
        //声明 T 类型的变量 msg
        T msg = null;
        try {
            //新建一个文件 放在 dir 目录下，名字是 filName
            File file = new File(dir,filName);
            //创建输入流is对象，表示以后输入到file中
            if(!file.exists() || file.length() == 0) {
            }
            is = new FileInputStream(file);
            //创建 StringBuilder 类

            StringBuilder stringBuffer = new StringBuilder();
            //新建byte类型的数组，大小是1024，名为buf
            byte[] buf = new byte[1024];
            //声明int型字节计数
            int byteCount;
            //从is每次读一个buf这么多的内容，并把它赋值给buf数组中，当他没有读完时。
            while ( (byteCount = is.read(buf)) != -1)
            {
                //首先创建一个字符串 内容是buf数组里从0到byteCount之间的字符串，添加到stringBuffer之后。
                stringBuffer.append(new String(buf,0,byteCount));
            }

            // msgJsonUtil下的解析Json方法，参数：字符串，T类 赋值给T 类型的变量
            msg = JsonUtil.parseJson(stringBuffer.toString(),cls);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return msg;
    }

    //IO工具下的保存方法，参数：T类型的 实例，文件名
    public static <T> void save(T entity, String fileName) {
        //环境获取外部存储信息 赋值给 string型 的 path

        String path = Environment.getExternalStorageState();
       // ApplicationUtil.log("IO", "进入方法");
        //如果path 等于 环境.媒体安装。
        if (path.equals(Environment.MEDIA_MOUNTED)) {
            //定义输出流对象
            OutputStream outputStream = null;
            //如果目录不存在
            if (!dir.exists())
                //创建一个目录
                dir.mkdirs();
            //新建一个文件 放在 dir 目录下，名字是 filName
            File file = new File(dir, fileName);
            try {
                //新建输出流对象 表示把文件输出到file里
                outputStream = new FileOutputStream(file);
              //  ApplicationUtil.log("IO", "创建文件成功");
                //输出流对象 开始写，写（调用JsonUtil下的toJson方法（参数：T类型的 实例）把T类对象变成字符串，再调用getBytes()获取字节数）
                outputStream.write(JsonUtil.toJson(entity).getBytes());
                //应用工具类调用 log方法，输出 "IO" 保存成功
                ApplicationUtil.log("IO", "save " + fileName + " success");
            } catch (FileNotFoundException e) {
                //文件没有找到 异常
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                //应用工具类调用 log方法，输出 "IO" 保存失败
                ApplicationUtil.log("IO", "save " + fileName + " failed");
            } finally {
                //最后 关闭文件
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
