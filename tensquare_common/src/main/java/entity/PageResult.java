package entity;

import java.util.List;

/**
 * @author xiaokuli
 * @date 2019/5/22 - 14:57
 */
//分页返回实体
public class PageResult <T>{
    private Long total; //总数
    private List<T> rows;  //数据

    //空构造函数
    public PageResult() {
    }

    //主要构造函数
    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
