package cloud.ciky.module;

import java.util.List;

/**
 * @Author: ciky
 * @Description: 分页实体类
 * @DateTime: 2024/11/23 11:44
 **/
public class PageResult<T> {
    private List<T> data;
    private int total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
