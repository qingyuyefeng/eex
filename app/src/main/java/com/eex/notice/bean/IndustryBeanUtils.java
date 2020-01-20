package com.eex.notice.bean;

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
 * @Package: com.overthrow.notice.bean
 * @ClassName: IndustryBeanUtils
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/12 19:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 19:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class IndustryBeanUtils {


    private static IndustryBean industry;

    private static final String TAG = "UserUtil";

    public static void saveIndustryBean(IndustryBean industry) {
        if (industry == null) {
            return;
        }
        industry = industry;

        String userString = JsonUtils.toJson(industry);

        SharedPreferencesUtils.putShareData(TAG, userString);

    }


    public static IndustryBean getIndustryBean() {
        if (industry == null) {
            String userString = SharedPreferencesUtils.getShareData(TAG);
            IndustryBean subordinate = JsonUtils.fromJson(userString, IndustryBean.class);
            subordinate = subordinate;
        }
        return industry;
    }

    public static void clear() {
        industry = null;
        SharedPreferencesUtils.putShareData(TAG, "");
    }


    public static String getId() {
        return getIndustryBean() == null ? null : getIndustryBean().getId();
    }

    public static String getTitle() {
        return getIndustryBean() == null ? null : getIndustryBean().getTitle();
    }

    public static String getCategoryName() {
        return getIndustryBean() == null ? null : getIndustryBean().getCategoryName();
    }

    public static String getCreateTime() {
        return getIndustryBean() == null ? null : getIndustryBean().getCreateTime();
    }

}
