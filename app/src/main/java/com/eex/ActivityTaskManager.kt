package com.eex

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context.ACTIVITY_SERVICE
import android.text.TextUtils
import java.util.*

/**
 * Activity管理器管理活动的Activity。
 *
 * @author
 */

object ActivityTaskManager {
    private var activityMap: HashMap<String, Activity> = LinkedHashMap()

    /**
     * 获取栈中所有的Activity
     */
    val allActivity: Set<String>
        get() = activityMap.keys

    /**
     * 返回管理器的Activity是否为空。
     *
     * @return 当且当管理器中的Activity对象为空时返回true，否则返回false。
     */
    val isEmpty: Boolean
        get() = activityMap.isEmpty()

    /**
     * 将一个activity添加到管理器。
     *
     * @param activity
     */
    fun putActivity(name: String, activity: Activity): Activity? {
        return activityMap.put(name, activity)
    }

    /**
     * 得到保存在管理器中的Activity对象。
     *
     * @param name
     * @return
     */
    fun getActivity(name: String): Activity? {
        return activityMap[name]
    }

    /**
     * 返回管理器中Activity对象的个数。
     *
     * @return 管理器中Activity对象的个数。
     */
    fun size(): Int {
        return activityMap.size
    }

    /**
     * 返回管理器中是否包含指定的名字。
     *
     * @param name 要查找的名字。
     * @return 当且仅当包含指定的名字时返回true, 否则返回false。
     */
    fun containsName(name: String): Boolean {
        return activityMap.containsKey(name)
    }

    /**
     * 返回管理器中是否包含指定的Activity。
     *
     * @param activity 要查找的Activity。
     * @return 当且仅当包含指定的Activity对象时返回true, 否则返回false。
     */
    fun containsActivity(activity: Activity): Boolean {
        return activityMap.containsValue(activity)
    }

    /**
     * 关闭所有活动的Activity。
     */
    fun closeAllActivity() {
        val activityNames = activityMap.keys
        for (string in activityNames) {
            finisActivity(activityMap[string])
        }
        activityMap.clear()
    }

    /**
     * 关闭所有活动的Activity除了指定的一个之外。
     *
     * @param nameSpecified 指定的不关闭的Activity对象的名字。
     */
    fun closeAllActivityExceptOne(nameSpecified: String) {
        val activityNames = activityMap.keys
        val activitySpecified = activityMap[nameSpecified]
        for (name in activityNames) {
            if (name == nameSpecified) {
                continue
            }
            finisActivity(activityMap[name])
        }
        activityMap.clear()
        if (activitySpecified != null) {
            activityMap[nameSpecified] = activitySpecified
        }
    }

    /**
     * 移除Activity对象,如果它未结束则结束它。
     *
     * @param name Activity对象的名字。
     */
    fun removeActivity(name: String) {
        if (!TextUtils.isEmpty(name)) {
            val activity = activityMap.remove(name)
            finisActivity(activity)
        }
    }

   private fun finisActivity(activity: Activity?) {
        if (activity != null) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    /**
     * 获取栈顶类名
     */
    fun getTopActivity(activity: Activity): ComponentName? {
        val manager = activity.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = manager.getRunningTasks(1)
        return if (runningTasks != null) {
            runningTasks[0].topActivity
        } else {
            null
        }
    }

}