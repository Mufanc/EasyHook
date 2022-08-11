package mufanc.easyhook.wrapper

import android.app.Application
import android.content.Context
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import mufanc.easyhook.wrapper.annotation.InternalApi

class ClassFinder internal constructor(private val classLoader: ClassLoader?) {
    fun findClass(className: String) : Class<*> {
        return XposedHelpers.findClass(className, classLoader)
    }
}

private typealias LoadPackageCallback = ClassFinder.(XC_LoadPackage.LoadPackageParam) -> Unit
private typealias InitZygoteCallback = ClassFinder.(IXposedHookZygoteInit.StartupParam) -> Unit
private typealias AttachApplicationCallback = ClassFinder.(XC_LoadPackage.LoadPackageParam, Context) -> Unit

object XposedEventManager {

    private val onLoadPackageCallbacks = mutableMapOf<String, LoadPackageCallback>()
    private lateinit var onInitZygoteCallback: InitZygoteCallback
    private val onAttachApplicationCallbacks = mutableMapOf<String, AttachApplicationCallback>()

    fun onLoadPackage(packageName: String, callback: LoadPackageCallback) {
        onLoadPackageCallbacks[packageName] = callback
    }

    fun onInitZygote(callback: InitZygoteCallback) {
        onInitZygoteCallback = callback
    }

    fun onAttachApplication(packageName: String, callback: AttachApplicationCallback) {
        onAttachApplicationCallbacks[packageName] = callback
    }

    @InternalApi
    fun dispatchLoadPackageEvent(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (onAttachApplicationCallbacks.isNotEmpty()) {
            XposedHelpers.findAndHookMethod(
                Application::class.java, "attach",
                Context::class.java,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        onAttachApplicationCallbacks[lpparam.packageName]?.let { callback ->
                            catch {
                                val context = param.args[0] as Context
                                callback(ClassFinder(context.classLoader), lpparam, context)
                            }
                        }
                    }
                }
            )
        }
        onLoadPackageCallbacks[lpparam.packageName]?.let { callback ->
            catch {
                callback(ClassFinder(lpparam.classLoader), lpparam)
            }
        }
    }

    @InternalApi
    fun dispatchInitZygoteEvent(startupParam: IXposedHookZygoteInit.StartupParam) {
        if (::onInitZygoteCallback.isInitialized) {
            onInitZygoteCallback(ClassFinder(null), startupParam)
        }
    }
}
