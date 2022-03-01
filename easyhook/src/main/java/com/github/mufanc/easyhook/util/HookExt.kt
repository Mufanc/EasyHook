package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member


private typealias Callback = (param: XC_MethodHook.MethodHookParam) -> Any?

class Hooker(priority: Int) : XC_MethodHook(priority) {

    private var replaced = false

    private lateinit var beforeCall: Callback
    private lateinit var afterCall: Callback

    override fun beforeHookedMethod(param: MethodHookParam) {
        if (this::beforeCall.isInitialized) {
            beforeCall(param).let {
                if (replaced) param.result = it
            }
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

    fun replace(callback: Callback) {
        before(callback)
        replaced = true
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
