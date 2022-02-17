package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member

typealias Hooker = (param: XC_MethodHook.MethodHookParam) -> Unit

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
 * Hook 指定方法/构造函数
 * @param param: XC_MethodHook 对象
 */
fun Member.hookMethod(param: XC_MethodHook) {
    XposedBridge.hookMethod(this, param)
}

/**
 * 在指定方法/构造函数执行前插入 Hook
 * @param priority: 优先级
 * @param func: 钩子函数的具体实现
 */
fun Member.beforeCall(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    func: Hooker
) {
    hookMethod(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            catch { func(param) }
        }
    })
}

/**
 * 在指定方法/构造函数执行后插入 Hook
 * @param priority: 优先级
 * @param func: 钩子函数的具体实现
 */
fun Member.afterCall(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    func: Hooker
) {
    hookMethod(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam) {
            catch { func(param) }
        }
    })
}

/**
 * 替换指定方法/构造函数为给定的函数
 * @param func: 用于替换的函数
 */
fun Member.replaceWith(func: (param: XC_MethodHook.MethodHookParam) -> Any?) {
    hookMethod(object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam) {
            catch { param.result = func(param) }
        }
    })
}
