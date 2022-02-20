package com.github.mufanc.easyhook.util

import android.app.Activity
import android.app.Application
import android.util.ArrayMap

/**
 * 捕获代码块中的错误，并输出到 Logcat
 */
fun catch(func: () -> Unit) {
    try {
        func()
    } catch (err: Throwable) {
        Log.e(err)
    }
}


object Misc {

    private val ActivityThread by lazy { findClass("android.app.ActivityThread") }

    private fun currentActivityThread(): Any {
        return ActivityThread.callStaticMethod("currentActivityThread")!!
    }

    fun getCurrentActivity(): Activity? {
        val mActivities = currentActivityThread().getField("mActivities") as ArrayMap<*, *>
        mActivities.values.forEach {
            if (!it.getFieldAs<Boolean>("paused")!!) {
                return it.getFieldAs("activity")
            }
        }
        return null
    }

    fun getApplication(): Application {
        return currentActivityThread().callMethodAs<Application>("getApplication")!!
    }
}
