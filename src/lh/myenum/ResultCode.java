package lh.myenum;

/**
 * 返回状态码枚举
 * @author 梁昊
 * @version 1.0
 */
public enum ResultCode {
    SUCCESS(0000, "操作成功"),
    ERROR(1000, "操作失败");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态码信息
     */
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}