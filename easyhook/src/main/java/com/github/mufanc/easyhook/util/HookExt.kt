package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member
import java.lang.reflect.Method

private typealias Callback = (XC_MethodHook.MethodHookParam) -> Unit
private typealias Replacement = (XC_MethodHook.MethodHookParam) -> Any?


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

/**
 * Hook 指定方法/构造函数
 * @param priority: 优先级
 * @param handler: 捕获器
 */
inline fun Member.hook(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    handler: Hooker.() -> Unit
) {
    XposedBridge.hookMethod(this, Hooker(priority).apply(handler))
}

/**
 * 在指定方法/构造函数执行前插入 Hook
 * @param callback: 回调函数
 */
inline infix fun Member.before(crossinline callback: Callback) {
    this.hook {
        before { callback(it) }
    }
}

/**
 * 在指定方法/构造函数执行后插入 Hook
 * @param callback: 回调函数
 */
inline infix fun Member.after(crossinline callback: Callback) {
    this.hook {
        after { callback(it) }
    }
}

/**
 * 替换指定方法/构造函数的执行
 * @param func: 回调函数
 */
inline infix fun Member.replace(crossinline func: Replacement) {
    this.hook {
        replace { func(it) }
    }
}


class Matcher(private val clazz: Class<*>) {
    fun find(filter: Method.() -> Boolean): Method {
        return findMethod(clazz, filter)!!
    }
}

inline infix fun Class<*>.hooks(func: Matcher.() -> Unit) {
    Matcher(this).func()
}

inline infix fun String.hooks(func: Matcher.() -> Unit) {
    Matcher(findClass(this)).func()
}
