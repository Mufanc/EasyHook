package com.github.mufanc.easyhook

import android.app.Application
import android.content.Context
import com.github.mufanc.easyhook.util.findMethod
import com.github.mufanc.easyhook.util.hook
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

abstract class HookHelper(TAG: String? = null) : IXposedHookLoadPackage, IXposedHookZygoteInit {

    companion object {
        fun setDefaultClassLoader(classLoader: ClassLoader) {
            Globals.defaultClassLoader = classLoader
        }
    }

    init {
        TAG?.let { Globals.TAG = TAG }
    }

    protected val lpparam get() = Globals.lpparam!!

    protected val startupParam get() = Globals.startupParam

    final override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        Globals.lpparam = lpparam
        findMethod(Application::class.java) {
            parameterCount == 1 && name == "attach"
        }!!.hook {
            before { onApplicationAttach(it.args[0] as Context) }
        }
        onHandleLoadPackage()
    }

    final override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        Globals.startupParam = startupParam
        onInitZygote()
    }

    open fun onHandleLoadPackage() { }

    open fun onInitZygote() { }

    open fun onApplicationAttach(context: Context) { }
}
