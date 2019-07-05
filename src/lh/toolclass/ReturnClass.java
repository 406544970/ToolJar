package lh.toolclass;

import lh.myenum.ResultCode;

/**
 * 返回结构体
 *
 * @param <T>
 */
public class ReturnClass<T> {
    private ResultCode resultCode;
    private String message;
    private T data;

    public ReturnClass() {
        super();
        setResultCode(ResultCode.SUCCESS);
    }

    public ReturnClass failure() {
        setResultCode(ResultCode.ERROR);
        return this;
    }

    public ReturnClass failure(String message) {
        setMessage(message);
        return failure();
    }

    public ReturnClass success(T data, String message) {
        setMessage(message);
        return success(data);
    }

    public ReturnClass success(T data) {
        setData(data);
        setResultCode(ResultCode.SUCCESS);
        return this;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        setMessage(this.resultCode.getMessage());
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
