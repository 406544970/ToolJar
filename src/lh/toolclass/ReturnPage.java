package lh.toolclass;

/**
 * 分页返回结构体
 *
 * @param <T>
 */
public class ReturnPage<T> {
    private int total;
    private T data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
