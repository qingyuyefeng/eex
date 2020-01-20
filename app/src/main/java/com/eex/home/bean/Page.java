package com.eex.home.bean;


import java.util.List;

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
 * @ClassName: Page
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/5/23 15:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 15:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Page<T> {

    /**
     * pageNo : null
     * pageSize : 100
     * totalCount : 12
     * resultList : [{"id":"9e773f0b650440ae9011ba947304027d","bannerName":"24小时在线","path":"2018-09-20/8ab047efc47341bbac6f0d504146693d","outsideAddr":null,"sortNo":1,"status":0,"type":0},{"id":"a91579a058a049a0bb7cae1f405604f6","bannerName":"秒杀专区","path":"2018-09-20/1056b8230fcc41d9b165cdac15adf44d","outsideAddr":null,"sortNo":2,"status":0,"type":0},{"id":"f6396a863e9442c59a947e3cb7d0c71b","bannerName":"锁仓理财","path":"2018-09-20/119d57ed137d4e97871d72029099830d","outsideAddr":"","sortNo":3,"status":0,"type":0},{"id":"82e93f3fa986416cb3ab0b3bbf185497","bannerName":"交易挖矿火爆开启","path":"2018-09-20/28677393803249ea81689a4b205aaedf","outsideAddr":"/redmin","sortNo":4,"status":0,"type":0},{"id":"132a344d93944f21a8388ac91de6d907","bannerName":"邀请返佣","path":"2018-12-18/099898986c6140a3aeb777450786960f","outsideAddr":"/news/info/28ee761608104d9e9ea143aa9b49aaef/bb3a00b13a64463eafcc35e4bba73bca/0","sortNo":5,"status":0,"type":0},{"id":"f75f1b95d6af4e2fa3d970d78b1e228c","bannerName":"免费上币立即申请","path":"2018-12-18/5cb58e0afba643bf833dd94ad3dbd64c","outsideAddr":"/freecurrency","sortNo":6,"status":0,"type":0},{"id":"23e96542393d432eb21cca8e7c8b2577","bannerName":"全球冷热钱包互换系统","path":"2018-09-20/70d94d4c5247421b92e592bddc0c5a8d","outsideAddr":"/news/info/e907c2c08247451ca906674069f6e9a3/6359bf08f800466c87d42ac52f70fbfe/0?tdsourcetag=s_pcqq_ai","sortNo":7,"status":0,"type":0},{"id":"934f358a51e240cb8e9a96dcec9dd060","bannerName":"AISA(方舟)上线","path":"2018-11-29/9d93e360f17c41de83d96e0bdae5e1e7","outsideAddr":"","sortNo":8,"status":0,"type":0},{"id":"94952796c5fb4251abed13d103fca3db","bannerName":"法币秒充秒提","path":"2018-12-18/62f26558935b4117b91426bc237b35b1","outsideAddr":"","sortNo":9,"status":0,"type":0},{"id":"21c12d07c2e84a299031e7e8f580bfa8","bannerName":"TUSD、PAX新币上新","path":"2018-12-19/9b2fdc03e89648ea9bbf910fdfed009d","outsideAddr":"","sortNo":10,"status":0,"type":0},{"id":"7ca6ffdf30d74cbfb293e08df3ad92bf","bannerName":"FINT新币上线","path":"2018-11-08/4db81bced9ad4a6993a87ff7d9288635","outsideAddr":"","sortNo":11,"status":0,"type":0},{"id":"0043e2e9d8c544eaaa74cf7893ab535f","bannerName":"全球通用域名","path":"2018-12-18/89cd5cc128264e8986142520fcd55a24","outsideAddr":"/news/info/0b2c0822137d47a694faf0be807cd4d5/9051179236ae400bbf0df7594152bc78/0","sortNo":12,"status":0,"type":0}]
     */

    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 总的记录数
     */
    private Long totalCount;

    private List<T> resultList;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
