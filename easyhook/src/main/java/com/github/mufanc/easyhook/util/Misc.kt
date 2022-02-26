package com.github.mufanc.easyhook.util

import android.util.ArrayMap
import java.util.*

private val objectExtras = WeakHashMap<Any, ArrayMap<String, Any?>>()

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

/**
 * 在某个实例上附加额外的键值对
 */
fun Any.setExtraField(key: String, value: Any?) {
    synchronized (objectExtras) {
        objectExtras[this] ?: let {
            objectExtras[this] = ArrayMap<String, Any?>()
        }
    }
    val extras = objectExtras[this]!!
    synchronized (extras) {
        extras[key] = value
    }
}

/**
 * 在实例上通过键获取相应的附加值
 */
fun Any.getExtraField(key: String): Any? {
    synchronized (objectExtras) {
        objectExtras[this]?.let {
            synchronized (it) {
                return it[key]
            }
        }
        return null
    }
}

/**
 * 移除实例上附加的指定键值
 */
fun Any.removeExtraField(key: String) {
    synchronized (objectExtras) {
        objectExtras[this]?.let {
            synchronized (it) {
                it.remove(key)
            }
        }
    }
}
