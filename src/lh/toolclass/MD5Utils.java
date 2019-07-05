package lh.toolclass;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * MD5加密类
 * @author 梁昊
 * @version 1.0
 */
public class MD5Utils {
    /**
     * @param source 原文
     * @return 密文
     */
    public static String getBase64(String source) {
//        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte;
        try {
            textByte = source.getBytes("UTF-8");
            final String strEncoded = encoder.encodeToString(textByte);
            return strEncoded;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * md5 32bit getBytes("UTF-8")解决与.net不一致md5不一致的问题
     *
     * @param source 原文
     * @return 密文
     */
    public static String getMd5(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5 16bit
     *
     * @param source
     * @return 密文
     */
    public static String getMd516(String source) {
        StringBuffer buffer = new StringBuffer(getMd5(source));
        return buffer.toString().substring(8, 24);
    }

    /**
     * silverFamily md5 upperCase base64
     *
     * @param source
     * @return 密文
     */
    public static String getSig(String source) {
        return getBase64(getMd5(source).toUpperCase());
    }
}