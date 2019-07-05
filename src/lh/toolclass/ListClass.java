package lh.toolclass;

import lh.model.FieldModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 梁昊
 * @date 2019/7/5
 * @function list常规操作类
 * @editLog
 */
public class ListClass {
    /**
     * 根据主键值，在关联表中，关联出与主对象的属性名、属性类型相同，且主对象属性值为null的属性
     * 第一步：根据主键名，得到get方法名
     * 第二步：判断该方法名是否存在
     * 第三步：根据get方法名，得到该属性值
     * 第四步：判断值是否为null
     * 第五步：判断关联列表是否为null
     * 第六步：判断关联列表是否存在关联主键属性
     * 第七步：判断关联列表对象中，是否存在该主键的get方法
     * 第八步：在关联表中，过滤出等于主对象值的列表
     * 第九步：判断过滤后的关联表，是否为null
     * 最后一步：万事俱备，只欠东风，开始关联
     *
     * @param myObject    主对象
     * @param outObject   关联表
     * @param mainKeyName 主对象中的外键属性名
     * @param outKeyName  关联表中的主键属性
     * @param <M>         主对象类
     * @param <O>         关联表对象类
     * @return 若返回为null，则正确，否则，关联出错，返回值中是出错原因；
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    public static <M, O> String getValueByKeyName(M myObject, List<O> outObject
            , String mainKeyName, String outKeyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        String outMessage = null;
        /**
         *第一步：根据主键名，得到get方法名
         */
        String getMainMethodName = LhClass.getCamelGetMethodName(mainKeyName);
        /**
         * 第二步：判断该方法名是否存在
         */
        if (!getClassMethodExist(getMainMethodName, myObject.getClass())) {
            outMessage = String.format("%s该方法不存在;", getMainMethodName);
            return outMessage;
        }
        /**
         * 第三步：根据get方法名，得到该属性值
         */
        Class myObjectClass = myObject.getClass();
        Method method = myObjectClass.getMethod(getMainMethodName);
        @SuppressWarnings("unchecked")
        Object keyValue = method.invoke(myObject);
        /**
         * 第四步：判断值是否为null
         */
        if (keyValue == null) {
            outMessage = String.format("%s该方法取出的值为null;", getMainMethodName);
            return outMessage;
        }
        /**
         * 第五步：判断关联列表是否为null
         */
        if (outObject == null || outObject.isEmpty()) {
            outMessage = "关联列表为null;";
            return outMessage;
        }
        /**
         * 第六步：判断关联列表是否存在关联主键属性
         */
        if (!getExistFieldName(outObject.get(0), outKeyName)) {
            outMessage = String.format("关联类中，不存在%s属性;", outKeyName);
            return outMessage;
        }
        /**
         * 第七步：判断关联列表对象中，是否存在该主键的get方法
         */
        String getLinkMethodName = LhClass.getCamelGetMethodName(outKeyName);
        Class outClass = outObject.get(0).getClass();
        Method outMainMethod = outClass.getMethod(getLinkMethodName);
        if (outMainMethod == null) {
            outMessage = String.format("关联类中，不存在%s方法;", getLinkMethodName);
            return outMessage;
        }
        /**
         * 第八步：在关联表中，过滤出等于主对象值的列表
         */
        List<Object> valueList = new ArrayList<>();
        valueList.add(keyValue);
        List<O> keyEqualsList = getKeyEqualsList(outObject, getLinkMethodName, valueList, outClass);
        /**
         * 第九步：判断过滤后的关联表，是否为null
         */
        if (keyEqualsList == null) {
            outMessage = String.format("关联类中，不存在属性为%s,值为%s的数据;", outKeyName, keyValue);
            keyEqualsList.clear();
            return outMessage;
        }
        /**
         * 最后一步：万事俱备，只欠东风，开始关联
         */
        for (O row : keyEqualsList
                ) {
            myObject = getKeyAndValueByT(myObject, row);
        }
        return outMessage;
    }

    /**
     * 根据主对象，在关联对象中，关联出与主对象的属性名、属性类型相同，且主对象属性值为null的属性
     *
     * @param myObject  主对象
     * @param outObject 关联表
     * @param <M>       主对象类
     * @param <O>       关联表对象类
     * @return
     * @throws IllegalAccessException
     */
    public static <M, O> M getKeyAndValueByT(M myObject, O outObject) throws IllegalAccessException {
        List<FieldModel> outObjectFieldAllInfo = getFieldAllInfo(outObject);
        if (outObjectFieldAllInfo != null && outObjectFieldAllInfo.size() > 0) {
            Class myObjectClass = myObject.getClass();
            for (Field field : myObjectClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object o = field.get(myObject);
                if (o == null) {
                    List<FieldModel> collect = outObjectFieldAllInfo.stream()
                            .filter(item -> item.getFieldName().equals(field.getName())
                                    && item.getFieldType().equals(field.getType().toString()))
                            .collect(Collectors.toList());
                    if (collect != null && collect.size() == 1) {
                        field.set(myObject, collect.get(0).getFieldValue());
                    }
                }
            }
        }
        return myObject;
    }

    /**
     * 得到指定对象的每个属性的名称、类型和值
     *
     * @param obj 指定对象
     * @return 自定义属性列表FieldModel
     * @throws IllegalAccessException
     */
    public static List<FieldModel> getFieldAllInfo(Object obj) throws IllegalAccessException {
        List<FieldModel> fieldModelList = new ArrayList<>();
        Class myObjectClass = obj.getClass();
        for (Field field : myObjectClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object o = field.get(obj);
            if (o != null) {
                FieldModel fieldModel = new FieldModel();
                fieldModel.setFieldName(field.getName());
                fieldModel.setFieldType(field.getType().toString());
                fieldModel.setFieldValue(field.get(obj));
                fieldModelList.add(fieldModel);
            }
        }
        return fieldModelList;
    }

    /**
     * 指定类中，指定方法是否存在
     *
     * @param methodName 方法名
     * @param kClass     指定类
     * @param <T>        类点位符
     * @return true:存在；false:不存在
     */
    public static <T> boolean getClassMethodExist(String methodName, Class<T> kClass) {
        boolean existSign = false;
        for (Method method : kClass.getMethods()) {
            if (method.getName().equals(methodName))
                existSign = true;
        }
        return existSign;
    }

    /**
     * 转换成驼峰命名法的字符串
     * 以下划线为分割符
     *
     * @param key 需要转换的字符串，如：vou_use_name
     * @return 驼峰命名法的字符串字符串
     */
    public static String getCamelNameByDownLine(String key) {
        if (key == null || key.trim().isEmpty())
            return null;
        String[] split = key.trim().split("_");
        if (split != null && split.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                if (split[i] != null && !split[i].trim().isEmpty()) {
                    split[i] = split[i].trim();
                    if (i > 0)
                        stringBuilder.append(split[i].substring(0, 1).toUpperCase());
                    else
                        stringBuilder.append(split[i].substring(0, 1).toLowerCase());
                    stringBuilder.append(split[i].substring(1));
                }
            }
            return stringBuilder.toString();
        } else
            return null;
    }

    /**
     * 判断指定类中，是否存在指定属性
     * 仅判断本类中的属性，不能判断父类中的属性
     *
     * @param myObject  指定类
     * @param fieldName 属性名
     * @param <T>       占位符
     * @return true:存在；false:不存在；
     */
    public static <T> boolean getExistFieldName(T myObject, String fieldName) throws NoSuchFieldException {
        if (fieldName == null)
            return false;
        fieldName = fieldName.replace(" ", "");
        if (!fieldName.isEmpty())
            return myObject.getClass().getDeclaredField(fieldName) != null ? true : false;
        else
            return false;
    }

    /**
     * 在原始列表中，对指定属性去掉重复
     *
     * @param kList   原列表
     * @param keyName 得到属性名，如：name
     * @param kClass  原列表中对象类
     * @param <T>     返回对象类
     * @param <K>     原列表中对象类
     * @return 去重复后的单属性列表
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T, K> List<T> getClearRepetitionSingleAttributeListByKey(List<K> kList, String keyName, Class<K> kClass, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String keyMethodName = LhClass.getCamelGetMethodName(keyName);
        return getClearRepetitionSingleAttributeList(kList, keyMethodName, kClass, tClass);
    }

    /**
     * 根据指定属性的值，在原始列表中，找出等于该属性值的对象
     *
     * @param tList     原始列表
     * @param keyName   得到属性的名称
     * @param valueList 值列表
     * @param tClass    原始列表对象类
     * @param <T>       原始类泛型
     * @param <K>       值类泛型
     * @return 返回符合该属性值的原始对象列表
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T, K> List<T> getKeyEqualsListByKeyName(List<T> tList, String keyName, List<K> valueList, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String keyMethodName = LhClass.getCamelGetMethodName(keyName);
        return getKeyEqualsList(tList, keyMethodName, valueList, tClass);
    }

    /**
     * 在原始列表中，对指定属性去掉重复
     *
     * @param kList         原列表
     * @param keyMethodName 得到属性值的方法名，如：getName
     * @param kClass        原列表中对象类
     * @param <T>           返回对象类
     * @param <K>           原列表中对象类
     * @return 去重复后的单属性列表
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T, K> List<T> getClearRepetitionSingleAttributeList(List<K> kList, String keyMethodName, Class<K> kClass, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (kList == null || kList.isEmpty())
            return null;
        List<T> list = new ArrayList();
        if (getClassMethodExist(keyMethodName, kClass)) {
            Method method = kClass.getMethod(keyMethodName);
            for (K row : kList
                    ) {
                @SuppressWarnings("unchecked")
                T key = (T) method.invoke(row);
                if (list.indexOf(key) < 0) {
                    list.add(key);
                }
            }
        }
        return list;
    }

    /**
     * 在原始列表中，根据指定属性的值，在关联列表中，找出等于该属性值的对象列表
     *
     * @param kList       原始列表
     * @param mainKeyName 得到属性名，如：name
     * @param kClass      原始列表对象的类名
     * @param vClass      原始列表对象指定属性类型
     * @param tList       关联列表
     * @param linkKeyName 关联列表的主键属性名称
     * @param tClass      关联列表对象的类名
     * @param <T>         原始列表对象类占位符
     * @param <K>         关联列表对象类占位符
     * @param <V>         原始列表对象指定属性类占位符
     * @return 返回过滤后的关联列表
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T, K, V> List<T> getFilterList(List<K> kList, String mainKeyName, Class<K> kClass, Class<V> vClass, List<T> tList, String linkKeyName, Class<T> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<V> clearRepetitionSingleAttributeListByKey = getClearRepetitionSingleAttributeListByKey(kList, mainKeyName, kClass, vClass);
        return getKeyEqualsListByKeyName(tList, linkKeyName, clearRepetitionSingleAttributeListByKey, tClass);
    }

    /**
     * 根据指定属性的值，在原始列表中，找出等于该属性值的对象
     *
     * @param tList         原始列表
     * @param keyMethodName 得到属性值的方法名称
     * @param valueList     值列表
     * @param tClass        原始列表对象类
     * @param <T>           原始类泛型
     * @param <K>           值类泛型
     * @return 返回符合该属性值的原始对象列表
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T, K> List<T> getKeyEqualsList(List<T> tList, String keyMethodName, List<K> valueList, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (tList == null || tList.isEmpty())
            return null;
        List<T> list = new ArrayList();
        Method method = tClass.getMethod(keyMethodName);
        for (T row : tList
                ) {
            @SuppressWarnings("unchecked")
            K key = (K) method.invoke(row);
            if (valueList.indexOf(key) > -1)
                list.add(row);
        }
        return list;
    }
}
