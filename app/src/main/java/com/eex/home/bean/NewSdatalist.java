package com.eex.home.bean;

import java.util.ArrayList;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: OverThrow
 * @Package: com.overthrow.home.bean
 * @ClassName: NewSdatalist
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/11 16:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/11 16:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewSdatalist<T> {


    /**
     * 总页数
     */
    private int pages;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页索引
     */
    private int pageNum;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 当前页数量
     */
    private int size;


    private ArrayList<T> listData ;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<T> getListData() {
        return listData;
    }

    public void setListData(ArrayList<T> listData) {
        this.listData = listData;
    }
}
