package lh.toolclass;

import com.google.gson.Gson;

import java.io.*;

/**
 * @author 梁昊
 * @date:2019/8/28
 */
public class LhJsonClass {
    /**
     * 读取json文件中的数据
     * @param jsonFileName 文件全路径
     * @param tClass 返回类
     * @param <T> 泛型
     * @return
     */
    public static <T> T formJsonToClass(String jsonFileName, Class<T> tClass) {
        File file = new File(jsonFileName);
        try {
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file)));
                StringBuffer stringBuffer = new StringBuffer();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuffer.append(temp);
                }
                String s = stringBuffer.toString();
                Gson gson = new Gson();
                T t = gson.fromJson(s, tClass);
                return t;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
