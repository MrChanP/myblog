package per.myblog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {
    public static void main(String[] args) {
//
//        try {
//            // 初始化服务端socket并且绑定9999端口
//            ServerSocket serverSocket  =new ServerSocket(9999);
//
//            //等待客户端的连接
//            Socket socket = serverSocket.accept();
//
//            //获取输入流
//            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            //读取一行数据
//            String str = bufferedReader.readLine();
//
//            //输出打印
//            System.out.println(str);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File rootFile = new File("D:\\video");
        Map<String, String> map = new HashMap<>();
        Map<String, String> map1 = findPath(rootFile, map);
        System.out.println(map1.get("lv3.mp4"));
    }

    public static Map<String, String> findPath(File searchRoot, Map<String, String> map) {
        File[] fileArr = searchRoot.listFiles();
        if (fileArr != null) {
            for (File tmp : fileArr) {
                if (!tmp.isDirectory()) {
                    map.put(tmp.getName(),tmp.getPath());
                } else {
                    findPath(tmp, map);
                }
            }
        }
        return map;
    }
}
