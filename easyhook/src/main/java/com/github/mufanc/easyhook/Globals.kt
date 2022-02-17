package com.github.mufanc.easyhook

import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

object Globals {
    var TAG = "EasyHook"
    lateinit var lpparam: XC_LoadPackage.LoadPackageParam
    lateinit var startupParam: IXposedHookZygoteInit.StartupParam
}
