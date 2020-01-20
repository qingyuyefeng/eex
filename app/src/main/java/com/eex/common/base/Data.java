package com.eex.common.base;


/**
 * @author huche
 */
public class Data<T>  {


    /**
     * 是否成功
     */
    private boolean stauts;

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public boolean isStauts() {
        return stauts;
    }

    public void setStauts(boolean stauts) {
        this.stauts = stauts;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "stauts=" + stauts +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
