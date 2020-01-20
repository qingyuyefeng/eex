package com.eex

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex

import com.eex.common.util.CommonUtil
import com.eex.common.util.RxBus
import com.eex.common.util.SharedPreferencesUtils
import com.eex.common.websocket.RxWebSocket
import com.eex.common.websocket.WSConfig
import com.eex.di.components.DaggerAppComponent
import com.eex.extensions.toast
import com.eex.home.bean.User
import com.eex.mvp.mainpage.MainActivity
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import dagger.android.AndroidInjector

import dagger.android.DaggerApplication
import tech.linjiang.pandora.Pandora
import tech.linjiang.pandora.function.IFunc
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

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
 * @Package: com.overthrow
 * @ClassName: OverThrowApp
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/4/29 16:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/29 16:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class EEXApp : DaggerApplication() {
    @Inject
    lateinit var rxErrHandler: RxErrHandler

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().create(this)

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @SuppressLint("CheckResult")
    override fun onCreate()  {
        super.onCreate()
        instance = this
        context = this


        val dir = filesDir.absolutePath + "/mmkv_user"
        val rootDir = MMKV.initialize(dir)
        println("mmkv root: $rootDir")

        SharedPreferencesUtils.config(context)
        RxBus.getInstance()
                .register(User::class.java)
                .subscribe({ user ->

                    EEXApp.finishAllActivity()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }, { throwable ->
                    EEXApp.finishAllActivity()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                })

        val config = WSConfig.Builder()
                //show  log
                .setShowLog(true)
                .build()
        RxWebSocket.setConfig(config)

        // 获取当前包名
        val packageName = context!!.packageName
        // 获取当前进程名
        val processName = CommonUtil.getProcessName(android.os.Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        CrashReport.initCrashReport(context, "ffb7ffa65d", false,strategy)

//        BaseCaughtExceptionHandler.getInstance().init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        rxErrHandler.init()
        Pandora.get()
                .addFunction(object : IFunc {
                    override fun onClick(): Boolean {
                        Config.MOCK = Config.MOCK.not()
                        toast { "当前mock:${Config.MOCK}" }
                        return false
                    }

                    override fun getIcon(): Int = R.drawable.ic_mock

                    override fun getName(): String = getString(R.string.mock_switch)

                })


    }

    companion object {

        // 定义保存的文件的名称
        val languageName = "sharedfile"

        var instance: EEXApp? = null
            private set

        var context: Context? = null

        internal var activities = ArrayList<Activity>()

        fun addActivity(activity: Activity) {
            activities.add(activity)
        }

        fun removeActivity(activity: Activity) {
            activities.remove(activity)
        }

        fun finishAllActivity() {
            for (activity in activities) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            activities.clear()
        }
    }

}
