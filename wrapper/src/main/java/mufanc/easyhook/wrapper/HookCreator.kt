package mufanc.easyhook.wrapper

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import mufanc.easyhook.wrapper.annotation.InternalApi
import java.lang.reflect.Constructor
import java.lang.reflect.Method

private typealias HookCallback = (XC_MethodHook.MethodHookParam) -> Unit
private typealias HookReplacement = (XC_MethodHook.MethodHookParam) -> Any?

@InternalApi
class HookCreator @PublishedApi internal constructor(
    @PublishedApi internal val target: Class<*>
) {

    class Hooker @PublishedApi internal constructor(priority: Int) : XC_MethodHook(priority) {

        private lateinit var beforeCall: HookCallback
        private lateinit var afterCall: HookCallback
        private lateinit var replacement: HookReplacement

        override fun beforeHookedMethod(param: MethodHookParam) {
            catch {
                if (this::replacement.isInitialized) {
                    param.result = replacement(param)
                    return
                }
                if (this::beforeCall.isInitialized) {
                    beforeCall(param)
                }
            }
        }

        override fun afterHookedMethod(param: MethodHookParam) {
            catch {
                if (this::afterCall.isInitialized) afterCall(param)
            }
        }

        fun before(callback: HookCallback) {
            beforeCall = callback
        }

        fun after(callback: HookCallback) {
            afterCall = callback
        }

        fun replace(func: HookReplacement) {
            replacement = func
        }
    }

    inline fun method(
        filter: Method.() -> Boolean,
        priority: Int = XCallback.PRIORITY_DEFAULT,
        init: Hooker.() -> Unit
    ) {
        target.declaredMethods.find(filter)?.let {
            XposedBridge.hookMethod(it, Hooker(priority).apply(init))
        } ?: throw NoSuchMethodException()
    }

    inline fun constructor(
        filter: Constructor<*>.() -> Boolean,
        priority: Int = XCallback.PRIORITY_DEFAULT,
        init: Hooker.() -> Unit
    ) {
        target.declaredConstructors.find(filter)?.let {
            XposedBridge.hookMethod(it, Hooker(priority).apply(init))
        } ?: throw NoSuchMethodException()
    }
}