package com.eex.home.bean;

import java.math.BigDecimal;

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
 * @ClassName: ConducMoneydetails
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/13 18:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/13 18:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConducMoneydetails {

    private String id;
    /**
     * 理财配置表主键
     */
    private String lockAssetsConfigId;
    /**
     * 日
     */
    private int financialCycle;
    /**
     * 月
     */
    private int financialCycleMonth;
    /**
     * 年
     */
    private int financialCycleYear;
    /**
     * 活动开始时间
     */
    private String activeStartTime;
    /**
     * 活动结束时间
     */
    private String activeEndTime;
    /**
     * 活动利率
     */
    private BigDecimal activeRate;
    /**
     * 固定日利率
     */
    private BigDecimal fixedDailyRate;


    private BigDecimal lockCount;

    private BigDecimal lockAlreadyCount;



    public String getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(String activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public String getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(String activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public BigDecimal getActiveRate() {
        return activeRate;
    }

    public void setActiveRate(BigDecimal activeRate) {
        this.activeRate = activeRate;
    }

    public BigDecimal getFixedDailyRate() {
        return fixedDailyRate;
    }

    public void setFixedDailyRate(BigDecimal fixedDailyRate) {
        this.fixedDailyRate = fixedDailyRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLockAssetsConfigId() {
        return lockAssetsConfigId;
    }

    public void setLockAssetsConfigId(String lockAssetsConfigId) {
        this.lockAssetsConfigId = lockAssetsConfigId;
    }

    public int getFinancialCycle() {
        return financialCycle;
    }

    public void setFinancialCycle(int financialCycle) {
        this.financialCycle = financialCycle;
    }

    public int getFinancialCycleMonth() {
        return financialCycleMonth;
    }

    public void setFinancialCycleMonth(int financialCycleMonth) {
        this.financialCycleMonth = financialCycleMonth;
    }

    public int getFinancialCycleYear() {
        return financialCycleYear;
    }

    public void setFinancialCycleYear(int financialCycleYear) {
        this.financialCycleYear = financialCycleYear;
    }

    public BigDecimal getLockCount() {
        return lockCount;
    }

    public void setLockCount(BigDecimal lockCount) {
        this.lockCount = lockCount;
    }

    public BigDecimal getLockAlreadyCount() {
        return lockAlreadyCount;
    }

    public void setLockAlreadyCount(BigDecimal lockAlreadyCount) {
        this.lockAlreadyCount = lockAlreadyCount;
    }
}
