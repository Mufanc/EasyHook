package mufanc.easyhook.wrapper

import android.app.Application
import android.content.Context
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage
import de.robv.android.xposed.callbacks.XCallback

abstract class HookHelper(
    TAG: String,
    private val hookApplicationAttach: Boolean = false
) : IXposedHookLoadPackage, IXposedHookZygoteInit {

    init {
        Logger.TAG = TAG
    }

    final override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // Todo: 自动生成 xposed_init
        // Todo: 自动生成作用域 arrays.xml？
        if (hookApplicationAttach) {
            Application::class.java hook {
                method({ parameterCount == 1 && name == "attach" }, XCallback.PRIORITY_HIGHEST) {
                    before { param ->
                        val context = param.args[0] as Context
                        lpparam.classLoader = context.classLoader
                        onApplicationAttach(lpparam, context)
                    }
                }
            }
        }
        onLoadPackage(lpparam)
    }

    final override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        onZygoteInit(startupParam)
    }

    open fun onLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) { }

    open fun onZygoteInit(startupParam: IXposedHookZygoteInit.StartupParam) { }

    open fun onApplicationAttach(lpparam: XC_LoadPackage.LoadPackageParam, context: Context) { }

}