package lh.toolclass;

import java.lang.reflect.Field;
import java.util.List;

public class LhClass {
    /**
     * 根据属性值，得到get方法名
     *
     * @param key 属性值
     * @return get方法名
     */
    public static String getCamelGetMethodName(String key) {
        return getCamelMethodName(key, true);
    }

    /**
     * 根据属性值，得到set方法名
     *
     * @param key 属性值
     * @return set方法名
     */
    public static String getCamelSetMethodName(String key) {
        return getCamelMethodName(key, false);
    }

    /**
     * 得到驼峰命名法的方法名
     * 如：输入useId，返回:getUseId或setUseId
     *
     * @param key     属性名
     * @param getSign true:get方法；false:set方法
     * @return 驼峰命名法的方法名，若key为null，返回为：null
     */
    private static String getCamelMethodName(String key, boolean getSign) {
        if (key == null || key.trim().isEmpty())
            return null;
        key = key.substring(0, 1).toUpperCase() + key.substring(1);
        return getSign ? String.format("get%s", key) : String.format("set%s", key);
    }
    public static String getVersion(){
        return String.format("version :%s","1.0");
    }
    /**
     * 指定属性列表，在类中，是否全部存在
     *
     * @param fieldNameList 属性列表
     * @param tClass        类名
     * @param <T>           泛型
     * @return 是否全部存在
     */
    public static <T> boolean fieldIsHave(List<String> fieldNameList, Class<T> tClass) {
        boolean finder = true;
        if (fieldNameList == null || fieldNameList.isEmpty()) {
            return false;
        }
        for (String fieldName :
                fieldNameList) {
            finder = finder && fieldIsHave(fieldName, tClass);
        }
        return finder;
    }

    /**
     * 指定属性，在类中，是否存在
     *
     * @param fieldName 属性名乐
     * @param tClass    类名
     * @param <T>       泛型
     * @return 是否存在
     */
    public static <T> boolean fieldIsHave(String fieldName, Class<T> tClass) {
        boolean finder = false;
        Field[] declaredFields = tClass.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            int index = 0;
            while (!finder) {
                if (declaredFields[index].getName().equals(fieldName)) {
                    finder = true;
                }
                index++;
            }
        }
        return finder;
    }

}
