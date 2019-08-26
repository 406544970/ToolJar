package lh.model;

/**
 * @ClassName: ResultVO
 * @Description:
 * @Auther: 梁昊
 * @Date: 2016/2/22 14:16
 * @Version: 1.0
 */
public class ResultVO<T> {
    /**
     * 状态码.
     */
    private Integer code;

    /**
     * 消息提示.
     */
    private String message;

    /**
     * 数据.
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
