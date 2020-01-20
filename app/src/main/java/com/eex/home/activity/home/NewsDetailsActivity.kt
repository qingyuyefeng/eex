package com.eex.home.activity.home

import android.os.Bundle
import android.text.Html
import com.eex.R
import com.eex.mvp.NoNetBaseActivity
import kotlinx.android.synthetic.main.activity_newsdetails.*

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
 *
 *
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: OverThrow
 * @Package: com.overthrow.home.activity.home
 * @ClassName: NewsDetailsActivity
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/6/14 15:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 15:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * go
 */
class NewsDetailsActivity : NoNetBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsdetails)


        val title = intent.getStringExtra("title") ?: "系统公告"
        val time = intent.getStringExtra("time") ?: ""
        val content = intent.getStringExtra("content") ?: "暂无内容"
        val user = intent.getStringExtra("user")?:""


        tv_news_title.text = title

        tv_news_time.text = "发布时间：$time"

        tv_news_content.text = Html.fromHtml(content)

        tv_news_user.text = "编辑：$user"
    }
}
