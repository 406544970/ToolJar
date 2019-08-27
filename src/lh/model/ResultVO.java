package lh.model;

import java.util.List;

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
    private String msg;

    /**
     * 数据条数
     */
    private long count;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if (data != null) {
            switch (data.getClass().getName()) {
                case "java.util.ArrayList":
                    this.count = ((List) data).size();
                    break;
                default:
                    this.count = 0;
                    break;
            }
        }
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
