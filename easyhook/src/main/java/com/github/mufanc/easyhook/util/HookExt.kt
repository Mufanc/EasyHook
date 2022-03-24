package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member


/**
 * Hook 指定方法/构造函数
 * @param priority: 优先级
 * @param handler: 捕获器
 */
fun Member.hook(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    handler: Hooker.() -> Unit
) {
    XposedBridge.hookMethod(this, Hooker(priority).apply(handler))
}

/**
 * Hook 指定方法/构造函数
 * @param hooker: 回调接口
 */
fun Member.hook(hooker: Hooker) {
    XposedBridge.hookMethod(this, hooker)
}

/**
 * 在指定方法/构造函数执行前插入 Hook
 * @param callback: 回调函数
 */
infix fun Member.before(callback: Callback) {
    this.hook {
        before { callback(it) }
    }
}

/**
 * 在指定方法/构造函数执行后插入 Hook
 * @param callback: 回调函数
 */
infix fun Member.after(callback: Callback) {
    this.hook {
        after { callback(it) }
    }
}

/**
 * 替换指定方法/构造函数的执行
 * @param func: 回调函数
 */
infix fun Member.replace(func: Replacement) {
    this.hook {
        replace { func(it) }
    }
}

infix fun Class<*>.hooks(func: Matcher.() -> Unit) {
    Matcher(this).func()
}

infix fun String.hooks(func: Matcher.() -> Unit) {
    Matcher(findClass(this)).func()
}
