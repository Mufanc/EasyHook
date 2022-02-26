package com.github.mufanc.easyhook.util

import android.app.Activity
import android.app.Application
import android.util.ArrayMap

object ContextUtils {
    private val ActivityThread by lazy { findClass("android.app.ActivityThread") }

    private fun currentActivityThread(): Any {
        return ActivityThread.callStaticMethod("currentActivityThread")!!
    }

    /**
     * 返回第一个非 pause 状态的 Activity 实例
     */
    fun getCurrentActivity(): Activity? {
        val mActivities = currentActivityThread().getField("mActivities") as ArrayMap<*, *>
        mActivities.values.forEach {
            if (!it.getFieldAs<Boolean>("paused")!!) {
                return it.getFieldAs("activity")
            }
        }
        return null
    }

    /**
     * 返回应用的 Application 实例，注意不能过早调用
     */
    fun getApplication(): Application {
        return currentActivityThread().callMethodAs<Application>("getApplication")!!
    }
}