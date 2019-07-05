package lh.toolclass;

import java.lang.reflect.Field;
import java.util.List;

public class LhClass {
    public LhClass() {
        super();
        this.version = "1.0";
    }
    private String version;
    public String getVersion(){
        return String.format("version :%s",this.version);
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
