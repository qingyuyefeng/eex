package com.eex.home.bean;

import com.eex.common.util.JsonUtils;
import com.eex.common.util.SharedPreferencesUtils;

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
 * @ClassName: UstilMainData
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/20 11:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/20 11:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UstilMainData {


    private static MainData mainData;

    private static final String TAG = "UserUtil";

    public static void saveMainData(MainData newMainData) {
        if (newMainData == null) {
            return;
        }
        mainData = newMainData;

        String mainDataString = JsonUtils.toJson(newMainData);

        SharedPreferencesUtils.putShareData(TAG, mainDataString);

    }


    public static MainData getMainData() {
        if (mainData == null) {
            String mainDataString = SharedPreferencesUtils.getShareData(TAG);
            MainData newMainData = JsonUtils.fromJson(mainDataString, MainData.class);
            mainData = newMainData;
        }
        return mainData;
    }

    public static void clear() {
        mainData = null;
        SharedPreferencesUtils.putShareData(TAG, "");
    }


    public static String getDelKey() {
        return getMainData() == null ? null : getMainData().getDelKey();
    }

    public static BigDecimal getNewPrice() {
        return getMainData() == null ? null : getMainData().getNewPrice();
    }

    public static BigDecimal getDealNum() {
        return getMainData() == null ? null : getMainData().getDealNum();
    }

    public static BigDecimal getHigePrice() {
        return getMainData() == null ? null : getMainData().getHigePrice();
    }

    public static BigDecimal getFooPrice() {
        return getMainData() == null ? null : getMainData().getFooPrice();
    }

    public static BigDecimal getLastPrice() {
        return getMainData() == null ? null : getMainData().getLastPrice();
    }

    public static BigDecimal getUsdtCny() {
        return getMainData() == null ? null : getMainData().getUsdtCny();
    }

}
