package mufanc.easyhook.tests.xposed

import android.app.Activity
import com.github.mufanc.easyhook.HookHelper
import com.github.mufanc.easyhook.util.*
import mufanc.easyhook.tests.ui.MainActivity
import java.util.*

class HookEntry : HookHelper() {
    override fun onInitZygote() {
        Log.i("Test: onInitZygote | module path: ${startupParam.modulePath}")
    }

    override fun onHandleLoadPackage() {
        Log.i("Test: onHandleLoadPackage | package: ${lpparam.packageName}, process: ${lpparam.processName}")

        findMethod(Activity::class.java) {
            name == "onCreate"
        }?.beforeCall {
            Log.i("Test: hook Activity#onCreate | activity: ${it.thisObject}")
        }

        findMethod(MainActivity::class.java.name) {
            name == "getToastText"
        }?.replaceWith {
            "Replaced!!"
        }

        findMethod(MainActivity::class.java.name) {
            name == "onResume"
        }?.afterCall {
            Log.i("Test: getApplication | application: ${Misc.getApplication()}")
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    Log.i("Test: getCurrentActivity | activity: ${Misc.getCurrentActivity()}")
                }
            }, 3000)
        }
    }

    override fun onApplicationAttach(classLoader: ClassLoader) {
        Log.i("Test: onApplicationAttach | classloader: $classLoader")
    }
}
