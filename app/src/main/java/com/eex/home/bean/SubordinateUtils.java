package com.eex.home.bean;

import com.eex.common.util.JsonUtils;
import com.eex.common.util.SharedPreferencesUtils;

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
 * @ClassName: SubordinateUtils
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 17:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 17:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SubordinateUtils {

    private static Subordinate subordinate;

    private static final String TAG = "UserUtil";

    public static void saveSubordinate(Subordinate subordinate) {
        if (subordinate == null) {
            return;
        }
        subordinate = subordinate;

        String userString = JsonUtils.toJson(subordinate);

        SharedPreferencesUtils.putShareData(TAG, userString);

    }


    public static Subordinate getSubordinate() {
        if (subordinate == null) {
            String userString = SharedPreferencesUtils.getShareData(TAG);
            Subordinate subordinate = JsonUtils.fromJson(userString, Subordinate.class);
            subordinate = subordinate;
        }
        return subordinate;
    }

    public static void clear() {
        subordinate = null;
        SharedPreferencesUtils.putShareData(TAG, "");
    }


    public static String getId() {
        return getSubordinate() == null ? null : getSubordinate().getId();
    }

    public static String getBannerName() {
        return getSubordinate() == null ? null : getSubordinate().getBannerName();
    }

    public static String getPath() {
        return getSubordinate() == null ? null : getSubordinate().getPath();
    }

    public static String getOutsideAddr() {
        return getSubordinate() == null ? null : getSubordinate().getOutsideAddr();
    }




}
