package com.eex.market.weight;

import com.eex.assets.frgament.AssetsFrgamnet;
import com.eex.mvp.homefrag.HomeFragment;
import com.eex.market.frgament.MarketFragment;
import com.eex.mine.frgament.MineFragment;
import com.eex.notice.frgament.NoticeFragment;

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
 * @Package: com.overthrow.market
 * @ClassName: GlobalParms
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/10 15:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/10 15:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GlobalParms {


    /**
     * 首页
     */
    private static HomeFragment homefragment;
    /**
     * 市场
     */
    private static MarketFragment marketFragment;
    /**
     * 资产
     */
    private static AssetsFrgamnet assetsFrgamnet;
    /**
     * 公告
     */
    private static NoticeFragment noticeFragment;
    /**
     * 我的
     */
    private static MineFragment mineFragment;

    /**
     * 改变选中Frangment的接口
     */
    public static ChangeFragment SchangeFragment;





    /**
     * 首页fragmnet
     *
     * @return
     */
    public static HomeFragment getHomefragmentt() {
        if (homefragment == null) {
            homefragment = new HomeFragment();
        }
        return homefragment;
    }

    /**
     * 市场fragmnet
     *
     * @return
     */
    public static MarketFragment getMarketFragment() {
        if (marketFragment == null) {
            marketFragment = new MarketFragment();
        }
        return marketFragment;
    }

    /**
     * 资产fragmnet
     *
     * @return
     */
    public static AssetsFrgamnet getAssetsFrgamnet() {
        if (assetsFrgamnet == null) {
            assetsFrgamnet = new AssetsFrgamnet();
        }
        return assetsFrgamnet;
    }

    /**
     * //公告fragmnet
     *
     * @return
     */
    public static NoticeFragment getNoticeFragment() {
        if (noticeFragment == null) {
            noticeFragment = new NoticeFragment();
        }
        return noticeFragment;
    }

    /**
     * //我的fragmnet
     *
     * @return
     */
    public static MineFragment getMineFragment() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }

    /**
     * 设置被选中的Fragment
     *
     * @param changeFragment
     */
    public static void setFragmentSelected(ChangeFragment changeFragment) {
        SchangeFragment = changeFragment;

    }

}
