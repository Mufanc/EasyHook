package mufanc.easyhook.wrapper

import android.util.Log

object Logger {
    
    var TAG = "EasyHook"
    
    fun v(message: Any?, err: Throwable? = null) {
        Log.v(TAG, "$message", err)
    }

    fun v(err: Throwable) {
        Log.v(TAG, "", err)
    }

    fun v(vararg args: Any?) {
        Log.v(TAG, args.joinToString(" ") { "$it" })
    }

    fun d(message: Any?, err: Throwable? = null) {
        Log.d(TAG, "$message", err)
    }

    fun d(err: Throwable) {
        Log.d(TAG, "", err)
    }

    fun d(vararg args: Any?) {
        Log.d(TAG, args.joinToString(" ") { "$it" })
    }

    fun i(message: Any?, err: Throwable? = null) {
        Log.i(TAG, "$message", err)
    }

    fun i(err: Throwable) {
        Log.i(TAG, "", err)
    }

    fun i(vararg args: Any?) {
        Log.i(TAG, args.joinToString(" ") { "$it" })
    }

    fun w(message: Any?, err: Throwable? = null) {
        Log.w(TAG, "$message", err)
    }

    fun w(err: Throwable) {
        Log.w(TAG, "", err)
    }

    fun w(vararg args: Any?) {
        Log.w(TAG, args.joinToString(" ") { "$it" })
    }

    fun e(message: Any?, err: Throwable? = null) {
        Log.e(TAG, "$message", err)
    }

    fun e(err: Throwable) {
        Log.e(TAG, "", err)
    }

    fun e(vararg args: Any?) {
        Log.e(TAG, args.joinToString(" ") { "$it" })
    }
}
