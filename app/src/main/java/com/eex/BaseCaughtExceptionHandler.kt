package com.eex

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.eex.common.api.ApiFactory
import com.eex.common.api.RxSchedulers
import com.eex.common.base.Data
import kotlin.system.exitProcess


class BaseCaughtExceptionHandler private constructor() : Thread.UncaughtExceptionHandler {
    companion object {
        val TAG = "BaseCaughtException"
        fun getInstance() = Helper.newInstance()
    }

    private object Helper {
        fun newInstance() = BaseCaughtExceptionHandler()
    }

    private lateinit var context: Context
    private var defaultHandler: Thread.UncaughtExceptionHandler? = null
    private var map = HashMap<String, String>()

    fun init(context: Context) {
        this.context = context
        //获取系统默认的UncaughtException处理器
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {

        Log.e(TAG,  e.toString())
        if (!handleException(e) && defaultHandler != null) { //没有自己处理就交给系统默认的处理器处理
            defaultHandler!!.uncaughtException(t, e)
        } else {
            try {
                Thread.sleep(3000) //等待3秒上传错误信息
            } catch (e: InterruptedException) {
                Log.e(TAG, "error : ", e)
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }
    }

    private fun handleException(ex: Throwable?): Boolean {

        Log.e(TAG,ex.toString())
        if (ex == null) {
            return false
        }
        Thread {
            kotlin.run {
                Looper.prepare()
                Toast.makeText(context, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }.start()
        //收集设备参数信息
        collectDeviceInfo(context)
        //保存日志文件(或上传服务器)
        saveCrashInfo(ex)
        return true
    }

    private fun saveCrashInfo(ex: Throwable?) {
        //处理收集的错误信息map
        ex.let {
            map["errorType"] = ex.toString()
            Log.e("xq", map.toString())
            ApiFactory.getInstance()
                    .collectError(1, map.toString())
                    .compose<Data<Any>>(RxSchedulers.io_main<Data<Any>>())
                    .subscribe {
                        t -> Log.e("xq", "上传回调==>$t")
                    }
        }
    }

    private fun collectDeviceInfo(context: Context) {
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            pi.run {
                val verName = if (versionName == null) "null" else versionName
                val verCode = versionCode.toString()
                map["versionName"] = verName
                map["versionCode"] = verCode
                0
            }
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            val info = manager!!.getRunningTasks(1)[0]
//            val shortClassName = info.topActivity!!.shortClassName    //类名
            val className = info.topActivity!!.className              //完整类名
            val packageName = info.topActivity!!.packageName          //包名
//            val stacks = Throwable().stackTrace
//            val methodName = stacks[1].methodName   //方法名
//            val clsName = stacks[1].className
            map["packageName"] = packageName
            map["className"] = className
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }
        val fields = Build::class.java.declaredFields
        fields.forEach {
            try {
                it.isAccessible = true
                if (it.name == "TIME" || it.name == "CPU_ABI" || it.name == "FINGERPRINT") {
                    map[it.name] = it.get(null).toString()
                    Log.d(TAG, it.name + " : " + it.get(null))
                }
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }
        }
    }

}