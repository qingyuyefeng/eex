package com.icechao.klinelib.entity;

import java.io.Serializable;
import java.text.ParseException;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.icechao.klinelib.utils
 * @FileName     : ICandle.java
 * @Author       : chao
 * @Date         : 2019/4/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/


public interface ICandle extends Serializable {


    Long getkTime() throws ParseException;

    /**
     * 开盘价
     */
    Float getOpeningPrice();

    /**
     * 最高价
     */
    float getTopPrice();

    /**
     * 最低价
     */
    float getFloorPrice();

    /**
     * 收盘价
     */
    Float getClosingPrice();

    /**
     * 交易量
     *
     * @return
     */
    float getDealQuantity();


    // 以下为MA数据

    /**
     * 五(月，日，时，分，5分等)均价
     */
    float getMaOne();

    /**
     * 十(月，日，时，分，5分等)均价
     */
    float getMaTwo();

    /**
     * 二十(月，日，时，分，5分等)均价
     */
    float getMaThree();

    // 以下为BOLL数据

    /**
     * 上轨线
     */
    float getUp();

    /**
     * 中轨线
     */
    float getMb();

    /**
     * 下轨线
     */
    float getDn();

}
