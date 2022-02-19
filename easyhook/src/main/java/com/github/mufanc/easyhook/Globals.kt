package com.github.mufanc.easyhook

import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

internal object Globals {
    var TAG = "EasyHook"
    var lpparam: XC_LoadPackage.LoadPackageParam? = null
        set(value) {
            defaultClassLoader = value!!.classLoader
            field = value
        }

    lateinit var startupParam: IXposedHookZygoteInit.StartupParam

    lateinit var defaultClassLoader: ClassLoader
}
