package lh.toolclass;

import lh.myenum.MainKeySign;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LhClass {
    /**
     * 得到文件上传主键，长
     *
     * @param port 端口号
     * @return
     */
    public static String getPicturyKey(short port) {
        return getMainKeyLen(MainKeySign.PICTURE.getSignName(), port);
    }

    /**
     * 得到订单主表主键，长
     *
     * @param port 端口号
     * @return
     */
    public static String getOrderMainLenKey(short port) {
        return getMainKeyLen(MainKeySign.ORDERMAIN.getSignName(), port);
    }

    /**
     * 得到订单主表主键，短
     *
     * @param port 端口号
     * @return
     */
    public static String getOrderMainShortKey(short port) {
        return getMainKeyShort(MainKeySign.ORDERMAIN.getSignName().substring(0, 1), port);
    }

    /**
     * 得到管理端用户主键
     *
     * @param port 端口号
     * @return
     */
    public static String getManagerUseKey(short port) {
        return getMainKeyLen(MainKeySign.MANAGERUSER.getSignName(), port);
    }

    /**
     * 得到企业用户主键
     *
     * @param port 端口号
     * @return
     */
    public static String getCompanyUseKey(short port) {
        return getMainKeyLen(MainKeySign.COMPANYUSER.getSignName(), port);
    }

    /**
     * 得到司机用户主键
     *
     * @param port 端口号
     * @return
     */
    public static String getDriverUseKey(short port) {
        return getMainKeyLen(MainKeySign.DRIVERUSER.getSignName(), port);
    }

    /**
     * 得到供应商用户主键
     *
     * @param port 端口号
     * @return
     */
    public static String getSupplierUseKey(short port) {
        return getMainKeyLen(MainKeySign.SUPPLIERUSER.getSignName(), port);
    }

    /**
     * 得到全权限主键
     *
     * @param port 端口号
     * @return
     */
    public static String getPowerKey(short port) {
        return getMainKeyLen(MainKeySign.POWER.getSignName(), port);
    }

    /**
     * 得到主数据，树状主键
     *
     * @param port 端口号
     * @return
     */
    public static String getMainDataTreeKey(short port) {
        return getMainKeyLen(MainKeySign.DICTORYTREE.getSignName(), port);
    }

    /**
     * 得到主数据，线性主键
     *
     * @param port 端口号
     * @return
     */
    public static String getMainDataLineKey(short port) {
        return getMainKeyLen(MainKeySign.DICTORYLINE.getSignName(), port);
    }

    /**
     * 得到长型主键
     * 规则：标识(2个字符) + 年月日时分秒 + 时间戳 + 端口号
     *
     * @param signName 主键标识
     * @param port     端口号
     * @return 例如：CC2019082710251215668727122052344
     */
    private static String getMainKeyLen(String signName, short port) {
        return getIdMainKeyPrivate(signName, (byte) 2, port, true);
    }

    /**
     * 得到短型主键
     * 规则：标识(1个字符) + 年月日 + 时间戳的后4位 + 端口号
     *
     * @param signName 主键标识
     * @param port     端口号
     * @return 例如：CC2019082712222344
     */
    private static String getMainKeyShort(String signName, short port) {
        return getIdMainKeyPrivate(signName, (byte) 1, port, false);
    }

    private static String getIdMainKeyPrivate(String signName, byte signLen, short port, boolean lenType) {
        if (signName == null) {
            return null;
        }
        if (signName != null && signName.length() != signLen) {
            return null;
        }
        int max = 1000, min = 1;//1000以内
        int i = (int) (Math.random() * (max - min) + min);
        System.out.println(i);
        if (i < 10) {
            signName = String.format("%s00%d", signName, i);
        } else if (i > 9 && i < 100) {
            signName = String.format("%s0%d", signName, i);
        } else {
            signName = String.format("%s%d", signName, i);
        }
        String myDate;
        String myTime = Long.valueOf(System.currentTimeMillis()).toString();
        SimpleDateFormat df;//设置日期格式
        if (lenType) {
            df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        } else {
            df = new SimpleDateFormat("yyyyMMddSSS");
            myTime = myTime.substring(myTime.length() - 1 - 4, myTime.length() - 1);
        }
        myDate = df.format(new Date());
        myDate = myDate.substring(2);
        return String.format("%s%s%s%d", signName, myDate, myTime, port);
    }

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

    public static String getVersion() {
        return String.format("version :%s", "1.0");
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
