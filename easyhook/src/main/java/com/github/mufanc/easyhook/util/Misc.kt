package com.github.mufanc.easyhook.util

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
