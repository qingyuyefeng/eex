package com.eex.assets.bean;

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
 * @Package: com.overthrow.assets.bean
 * @ClassName: Remittance
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/1 16:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/1 16:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Remittance {

    private String api_key;
    private String baggage_fields;
    private String character_encoding;
    private String interface_language;
    private String nopayment_url;
    private String payee_account;
    private String payee_name;
    private BigDecimal payment_amount;
    private String payment_units;
    private String payment_url;
    private String status_url;
    private String url;
    private String v2_hash;

    public String getPayee_account() {
        return payee_account;
    }

    public void setPayee_account(String payee_account) {
        this.payee_account = payee_account;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getBaggage_fields() {
        return baggage_fields;
    }

    public void setBaggage_fields(String baggage_fields) {
        this.baggage_fields = baggage_fields;
    }

    public String getCharacter_encoding() {
        return character_encoding;
    }

    public void setCharacter_encoding(String character_encoding) {
        this.character_encoding = character_encoding;
    }

    public String getInterface_language() {
        return interface_language;
    }

    public void setInterface_language(String interface_language) {
        this.interface_language = interface_language;
    }

    public String getNopayment_url() {
        return nopayment_url;
    }

    public void setNopayment_url(String nopayment_url) {
        this.nopayment_url = nopayment_url;
    }


    public String getPayee_name() {
        return payee_name;
    }

    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name;
    }

    public BigDecimal getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(BigDecimal payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_units() {
        return payment_units;
    }

    public void setPayment_units(String payment_units) {
        this.payment_units = payment_units;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getStatus_url() {
        return status_url;
    }

    public void setStatus_url(String status_url) {
        this.status_url = status_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getV2_hash() {
        return v2_hash;
    }

    public void setV2_hash(String v2_hash) {
        this.v2_hash = v2_hash;
    }
}
