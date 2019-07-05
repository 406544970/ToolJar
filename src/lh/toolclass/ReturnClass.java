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
        return failure(null);
    }

    public ReturnClass failure(String message) {
        setResultCode(ResultCode.ERROR);
        if (message != null) {
            setMessage(message);
        }
        return this;
    }

    public ReturnClass success(T data) {
        return success(data,null);
    }

    public ReturnClass success(T data, String message) {
        setData(data);
        setResultCode(ResultCode.SUCCESS);
        if (message != null) {
            setMessage(message);
        }
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
