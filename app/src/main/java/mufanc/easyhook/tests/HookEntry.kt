package mufanc.easyhook.tests

import com.github.mufanc.easyhook.HookHelper
import com.github.mufanc.easyhook.util.Log

class HookEntry : HookHelper() {
    override fun onInitZygote() {
        Log.i("Test: onInitZygote | module path: ${startupParam.modulePath}")
    }

    override fun onHandleLoadPackage() {
        Log.i("Test: onHandleLoadPackage | package: ${lpparam.packageName}, process: ${lpparam.processName}")
    }

    override fun onApplicationAttach(classLoader: ClassLoader) {
        Log.i("Test: onApplicationAttach | classloader: $classLoader")
    }
}
