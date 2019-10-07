package lh.toolclass;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 得到拼音
 *
 * @author 梁昊
 * @date:2019/9/6
 */
public class LhGetPinYinClass {
    /**
     * 将汉字转成拼音(取首字母)
     *
     * @param content 汉字内容
     * @return
     */
    public static String getPinYin(String content) {
        return getPinYin(content, false);
    }

    /***
     * 将汉字转成拼音(取首字母或全拼)
     * @param content 汉字内容
     * @param full 是否全拼
     * @return
     */
    public static String getPinYin(String content, boolean full) {
        /***
         * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
         * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
         * ^[\u4E00-\u9FA5]+$ 匹配简体
         */
        String regExp = "^[\u4E00-\u9FFF]+$";
        StringBuffer sb = new StringBuffer();
        if (content == null || "".equals(content.trim())) {
            return "";
        }
        String pinyin;
        for (int i = 0; i < content.length(); i++) {
            char unit = content.charAt(i);
            if (match(String.valueOf(unit), regExp))//是汉字，则转拼音
            {
                pinyin = getSinglePinyin(unit);
                if (full) {
                    sb.append(pinyin);
                } else {
                    sb.append(pinyin.charAt(0));
                }
            } else {
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    /***
     * 将单个汉字转成拼音
     * @param content 汉字内容
     * @return
     */
    private static String getSinglePinyin(char content) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        StringBuffer sb = new StringBuffer();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(content, outputFormat);
            sb.append(res[0]);//对于多音字，只用第一个拼音
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    /***
     * 匹配方法
     * @param str 源字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean match(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
