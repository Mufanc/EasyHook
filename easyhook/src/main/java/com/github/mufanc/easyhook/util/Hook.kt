package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Method

internal typealias Callback = (XC_MethodHook.MethodHookParam) -> Unit
internal typealias Replacement = (XC_MethodHook.MethodHookParam) -> Any?


class Hooker(priority: Int) : XC_MethodHook(priority) {

    private lateinit var beforeCall: Callback
    private lateinit var afterCall: Callback
    private lateinit var replacement: Replacement

    override fun beforeHookedMethod(param: MethodHookParam) {
        catch {
            if (this::replacement.isInitialized) {
                param.result = replacement(param)
            } else if (this::beforeCall.isInitialized) {
                beforeCall(param)
            }
        }
    }

    override fun afterHookedMethod(param: MethodHookParam) {
        catch {
            if (this::afterCall.isInitialized) afterCall(param)
        }
    }

    fun before(callback: Callback) {
        beforeCall = callback
    }

    fun after(callback: Callback) {
        afterCall = callback
    }

    fun replace(func: Replacement) {
        replacement = func
    }
}

class Matcher(private val clazz: Class<*>) {
    fun find(filter: Method.() -> Boolean): Method {
        return findMethod(clazz, filter)!!
    }
}

fun hooker(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    handler: Hooker.() -> Unit
): Hooker {
    return Hooker(priority).apply(handler)
}
