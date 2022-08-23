package mufanc.easyhook.api

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import mufanc.easyhook.api.annotation.InternalApi

class LoaderContext internal constructor(private val classLoader: ClassLoader?) {
    fun findClass(className: String): Class<*> {
        return XposedHelpers.findClass(className, classLoader)
    }
}

private typealias LoadPackageCallback = LoaderContext.(XC_LoadPackage.LoadPackageParam) -> Unit
private typealias InitZygoteCallback = LoaderContext.(IXposedHookZygoteInit.StartupParam) -> Unit
private typealias AttachApplicationCallback = LoaderContext.(XC_LoadPackage.LoadPackageParam, Context) -> Unit

@Keep
object EasyHook {

    private val onLoadPackageCallbacks = mutableMapOf<String, MutableList<LoadPackageCallback>>()
    private val onInitZygoteCallback = mutableListOf<InitZygoteCallback>()
    private val onAttachApplicationCallbacks = mutableMapOf<String, MutableList<AttachApplicationCallback>>()

    fun handle(register: EasyHook.() -> Unit) {
        register(this)
    }

    fun onLoadPackage(packageName: String, callback: LoadPackageCallback) {
        onLoadPackageCallbacks[packageName]?.add(callback) ?: let {
            onLoadPackageCallbacks[packageName] = mutableListOf(callback)
        }
    }

    fun onInitZygote(callback: InitZygoteCallback) {
        onInitZygoteCallback.add(callback)
    }

    fun onAttachApplication(packageName: String, callback: AttachApplicationCallback) {
        onAttachApplicationCallbacks[packageName]?.add(callback) ?: let {
            onAttachApplicationCallbacks[packageName] = mutableListOf(callback)
        }
    }

    @InternalApi
    fun dispatchLoadPackageEvent(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (onAttachApplicationCallbacks.isNotEmpty()) {
            XposedHelpers.findAndHookMethod(
                Application::class.java, "attach",
                Context::class.java,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val context = param.args[0] as Context
                        onAttachApplicationCallbacks[lpparam.packageName]?.forEach { callback ->
                            catch {
                                callback(LoaderContext(context.classLoader), lpparam, context)
                            }
                        }
                    }
                }
            )
        }
        onLoadPackageCallbacks[lpparam.packageName]?.forEach { callback ->
            catch {
                callback(LoaderContext(lpparam.classLoader), lpparam)
            }
        }
    }

    @InternalApi
    fun dispatchInitZygoteEvent(startupParam: IXposedHookZygoteInit.StartupParam) {
        onInitZygoteCallback.forEach { callback ->
            callback(LoaderContext(null), startupParam)
        }
    }
}
