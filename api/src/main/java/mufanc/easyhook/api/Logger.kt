package mufanc.easyhook.api

import android.util.Log
import de.robv.android.xposed.XposedBridge

object Logger {

    private var TAG = "EasyHook"
    private var toLogcat = true   // 打印到系统 Logcat
    private var toXposedBridge = false  // 打印到 Xposed Bridge

    fun configure(TAG: String? = null, toLogcat: Boolean? = null, toXposedBridge: Boolean? = null) {
        TAG?.let { this.TAG = it }
        toLogcat?.let { this.toLogcat = it }
        toXposedBridge?.let { this.toXposedBridge = it }
    }
    
    fun v(message: Any?, err: Throwable? = null) {
        if (toLogcat) Log.v(TAG, "$message", err)
        if (toXposedBridge) {
            XposedBridge.log("$message")
            err?.let { XposedBridge.log(it) }
        }
    }

    fun v(err: Throwable) {
        if (toLogcat) Log.v(TAG, "", err)
        if (toXposedBridge) XposedBridge.log(err)
    }

    fun v(vararg args: Any?) {
        val message = args.joinToString(" ") { "$it" }
        if (toLogcat) Log.v(TAG, message)
        if (toXposedBridge) XposedBridge.log(message)
    }

    fun d(message: Any?, err: Throwable? = null) {
        if (toLogcat) Log.d(TAG, "$message", err)
        if (toXposedBridge) {
            XposedBridge.log("$message")
            err?.let { XposedBridge.log(it) }
        }
    }

    fun d(err: Throwable) {
        if (toLogcat) Log.d(TAG, "", err)
        if (toXposedBridge) XposedBridge.log(err)
    }

    fun d(vararg args: Any?) {
        val message = args.joinToString(" ") { "$it" }
        if (toLogcat) Log.d(TAG, message)
        if (toXposedBridge) XposedBridge.log(message)
    }

    fun i(message: Any?, err: Throwable? = null) {
        if (toLogcat) Log.i(TAG, "$message", err)
        if (toXposedBridge) {
            XposedBridge.log("$message")
            err?.let { XposedBridge.log(it) }
        }
    }

    fun i(err: Throwable) {
        if (toLogcat) Log.i(TAG, "", err)
        if (toXposedBridge) XposedBridge.log(err)
    }

    fun i(vararg args: Any?) {
        val message = args.joinToString(" ") { "$it" }
        if (toLogcat) Log.i(TAG, message)
        if (toXposedBridge) XposedBridge.log(message)
    }

    fun w(message: Any?, err: Throwable? = null) {
        if (toLogcat) Log.w(TAG, "$message", err)
        if (toXposedBridge) {
            XposedBridge.log("$message")
            err?.let { XposedBridge.log(it) }
        }
    }

    fun w(err: Throwable) {
        if (toLogcat) Log.w(TAG, "", err)
        if (toXposedBridge) XposedBridge.log(err)
    }

    fun w(vararg args: Any?) {
        val message = args.joinToString(" ") { "$it" }
        if (toLogcat) Log.w(TAG, message)
        if (toXposedBridge) XposedBridge.log(message)
    }

    fun e(message: Any?, err: Throwable? = null) {
        if (toLogcat) Log.e(TAG, "$message", err)
        if (toXposedBridge) {
            XposedBridge.log("$message")
            err?.let { XposedBridge.log(it) }
        }
    }

    fun e(err: Throwable) {
        if (toLogcat) Log.e(TAG, "", err)
        if (toXposedBridge) XposedBridge.log(err)
    }

    fun e(vararg args: Any?) {
        val message = args.joinToString(" ") { "$it" }
        if (toLogcat) Log.e(TAG, message)
        if (toXposedBridge) XposedBridge.log(message)
    }
}
