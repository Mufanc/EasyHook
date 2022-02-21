package com.github.mufanc.easyhook.util

import android.util.Log
import com.github.mufanc.easyhook.Globals

object Log {
    fun v(message: Any?, err: Throwable? = null) {
        Log.v(Globals.TAG, "$message", err)
    }

    fun v(err: Throwable, message: Any? = "") {
        Log.v(Globals.TAG, "$message", err)
    }

    fun d(message: Any?, err: Throwable? = null) {
        Log.d(Globals.TAG, "$message", err)
    }

    fun d(err: Throwable, message: Any? = "") {
        Log.d(Globals.TAG, "$message", err)
    }

    fun i(message: Any?, err: Throwable? = null) {
        Log.i(Globals.TAG, "$message", err)
    }

    fun i(err: Throwable, message: Any? = "") {
        Log.i(Globals.TAG, "$message", err)
    }

    fun w(message: Any?, err: Throwable? = null) {
        Log.w(Globals.TAG, "$message", err)
    }

    fun w(err: Throwable, message: Any? = "") {
        Log.w(Globals.TAG, "$message", err)
    }

    fun e(message: Any?, err: Throwable? = null) {
        Log.e(Globals.TAG, "$message", err)
    }

    fun e(err: Throwable, message: Any? = "") {
        Log.e(Globals.TAG, "$message", err)
    }
}
