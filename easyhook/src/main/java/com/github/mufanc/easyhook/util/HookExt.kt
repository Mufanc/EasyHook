package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member


private typealias Callback = (param: XC_MethodHook.MethodHookParam) -> Unit

class Hooker(priority: Int) : XC_MethodHook(priority) {

    private lateinit var beforeCall: Callback
    private lateinit var afterCall: Callback
    private lateinit var replacement: (param: MethodHookParam) -> Any?

    override fun beforeHookedMethod(param: MethodHookParam) {
        if (this::replacement.isInitialized) {
            param.result = replacement(param)
        } else if (this::beforeCall.isInitialized) {
            beforeCall(param)
        }
    }

    override fun afterHookedMethod(param: MethodHookParam) {
        if (this::afterCall.isInitialized) afterCall(param)
    }

    fun before(callback: Callback) {
        beforeCall = callback
    }

    fun after(callback: Callback) {
        afterCall = callback
    }

    fun replace(func: (param: MethodHookParam) -> Any?) {
        replacement = func
    }
}

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
