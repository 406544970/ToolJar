package lh.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 梁昊
 * @date 2019/8/24
 * @function
 * @editLog
 */
public final class ResultVOTotal<T, V> extends ResultVO<T> {
    private List<V> totalData;

    @Override
    public Integer getCode() {
        return super.getCode();
    }

    @Override
    public void setCode(Integer code) {
        super.setCode(code);
    }

    @Override
    public String getMsg() {
        return super.getMsg();
    }

    @Override
    public void setMsg(String msg) {
        super.setMsg(msg);
    }

    @Override
    public T getData() {
        return super.getData();
    }

    @Override
    public void setData(T data) {
        super.setData(data);
    }

    @Override
    public long getCount() {
        return super.getCount();
    }

    @Override
    public void setCount(long count) {
        super.setCount(count);
    }

    public List<V> getTotalData() {
        return totalData;
    }

    public void setTotalData(List<V> totalData) {
        this.totalData = totalData;
    }
}
