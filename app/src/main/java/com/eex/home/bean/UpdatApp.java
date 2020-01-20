package com.eex.home.bean;

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
 * @ClassName: UpdatApp
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/11 17:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/11 17:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UpdatApp {

    /**
     * 版本号
     */
    private String buildVersion;
    /**
     * 上传版本编号
     */
    private String buildVersionNo;
    /**
     * 蒲公英生成用于区分buid版本号
     */
    private Integer buildBuildVersion;
    /**
     * 应用更新说明
     */
    private String buildUpdateDescription;
    /**
     * 应用短链接
     */
    private String buildShortcutUrl;
    /**
     * 是否有新版本
     */
    private boolean buildHaveNewVersion;
    /**
     * 应用下载地址
     */
    private String downloadURL;
    /**
     * 是否强制更新 0是 1否（默认强制更新）
     */
    private int updata;

    /**
     * 版本更新内容
     */
    private String versionUpdateContent;

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getBuildVersionNo() {
        return buildVersionNo;
    }

    public void setBuildVersionNo(String buildVersionNo) {
        this.buildVersionNo = buildVersionNo;
    }

    public Integer getBuildBuildVersion() {
        return buildBuildVersion;
    }

    public void setBuildBuildVersion(Integer buildBuildVersion) {
        this.buildBuildVersion = buildBuildVersion;
    }

    public String getBuildUpdateDescription() {
        return buildUpdateDescription;
    }

    public void setBuildUpdateDescription(String buildUpdateDescription) {
        this.buildUpdateDescription = buildUpdateDescription;
    }

    public String getBuildShortcutUrl() {
        return buildShortcutUrl;
    }

    public void setBuildShortcutUrl(String buildShortcutUrl) {
        this.buildShortcutUrl = buildShortcutUrl;
    }

    public boolean isBuildHaveNewVersion() {
        return buildHaveNewVersion;
    }

    public void setBuildHaveNewVersion(boolean buildHaveNewVersion) {
        this.buildHaveNewVersion = buildHaveNewVersion;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public int getUpdata() {
        return updata;
    }

    public void setUpdata(int updata) {
        this.updata = updata;
    }

    public String getVersionUpdateContent() {
        return versionUpdateContent;
    }

    public void setVersionUpdateContent(String versionUpdateContent) {
        this.versionUpdateContent = versionUpdateContent;
    }
}
