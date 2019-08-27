package lh.model;

import java.util.LinkedHashMap;

/**
 * @author 梁昊
 * @date 2019/8/24
 * @function
 * @editLog
 */
public final class ResultVOTotal<T, V> extends ResultVO<T> {
    private LinkedHashMap<String, V> totalData;

    public LinkedHashMap<String, V> getTotalData() {
        return totalData;
    }

    public void setTotalData(LinkedHashMap<String, V> totalData) {
        this.totalData = totalData;
    }

    @Override
    public Integer getCode() {
        return super.getCode();
    }

    @Override
    public void setCode(Integer code) {
        super.setCode(code);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public T getData() {
        return super.getData();
    }

    @Override
    public void setData(T data) {
        super.setData(data);
    }
}
