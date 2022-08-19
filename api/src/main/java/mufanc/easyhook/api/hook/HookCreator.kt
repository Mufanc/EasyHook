package mufanc.easyhook.api.hook

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import mufanc.easyhook.api.catch
import mufanc.easyhook.api.reflect.findConstructor
import mufanc.easyhook.api.reflect.findMethod
import java.lang.reflect.Constructor
import java.lang.reflect.Method

private typealias HookCallback = (XC_MethodHook.MethodHookParam) -> Unit
private typealias HookReplacement = (XC_MethodHook.MethodHookParam) -> Any?

class HookCreator @PublishedApi internal constructor(
    @PublishedApi internal val target: Class<*>
) {

    class Hooker @PublishedApi internal constructor(priority: Int): XC_MethodHook(priority) {

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
        initializer: Hooker.() -> Unit
    ) {
        target.findMethod(filter)?.let {
            XposedBridge.hookMethod(it, Hooker(priority).apply(initializer))
        } ?: throw NoSuchMethodError()
    }

    inline fun constructor(
        filter: Constructor<*>.() -> Boolean,
        priority: Int = XCallback.PRIORITY_DEFAULT,
        initializer: Hooker.() -> Unit
    ) {
        target.findConstructor(filter)?.let {
            XposedBridge.hookMethod(it, Hooker(priority).apply(initializer))
        } ?: throw NoSuchMethodError()
    }
}
