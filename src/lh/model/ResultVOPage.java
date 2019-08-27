package lh.model;

/**
 * 带分页参数
 *
 * @author 梁昊
 * @date 2019/8/24
 * @function
 * @editLog
 */
public class ResultVOPage<T> extends ResultVO<T> {
    /**
     * 当前页数
     */
    private int page;
    /**
     * 每页条数
     */
    private int limit;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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
}
