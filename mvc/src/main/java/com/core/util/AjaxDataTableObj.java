package com.core.util;

import java.util.List;

public class AjaxDataTableObj<T> {
    /** 返回数据 */
    private List<T> aaData;
    /** 请求标识 */
    private int sEcho;
    /** 数据库中总共有多少条 */
    private int iTotalRecords;
    /** 数据库中查询过滤后有多少条记录,和iTotalRecords设置为相同 */
    private int iTotalDisplayRecords;

    /**
     * 无参构造函数
     */
    public AjaxDataTableObj() {
    }

    /**
     * 构造函数
     * 
     * @param sEcho 请求标识
     */
    public AjaxDataTableObj(int sEcho) {
        this.sEcho = sEcho;
    }

    /**
     * 构造函数
     * 
     * @param sEcho 请求标识
     * @param prs 分页结果集
     */
    public AjaxDataTableObj(int sEcho, PaginationResult<List<T>> prs) {
        this.sEcho = sEcho;
        // 设置列表数据
        this.aaData = prs.getR();
        // 设置显示总记录条数
        this.iTotalDisplayRecords = prs.getPagination().getTotalRows();
        // 设置总记录条数
        this.iTotalRecords = prs.getPagination().getTotalRows();
    }

    /**
     * @return aaData 列表数据
     */
    public List<T> getAaData() {
        return aaData;
    }

    /**
     * @param aaData 列表数据
     */
    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    /**
     * @return sEcho 请求标识
     */
    public int getsEcho() {
        return sEcho;
    }

    /**
     * @param sEcho 请求标识
     */
    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    /**
     * @return iTotalRecords 总记录条数
     */
    public int getiTotalRecords() {
        return iTotalRecords;
    }

    /**
     * @param iTotalRecords 总记录条数
     */
    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    /**
     * @return iTotalDisplayRecords 总显示记录条数
     */
    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    /**
     * @param iTotalDisplayRecords 总显示记录条数
     */
    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }
}
