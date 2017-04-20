package com.guonima.wxapp.domain.common;

import java.util.List;

/**
 * @desc 封装分页查询条件
 */
public class Pageable<T> {
    /*
     * 当前页编号
     */
    private int pageNum = 1;
    /*
     * 每页记录数
     */
    private int pageSize = 10;
    /*
     * 总页数
     */
    private int totalNum = 0;
    /*
     * 总记录数
     */
    private long totalRows = 0;
    /*
     * 数据结果
     */
    private List<T> result;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
