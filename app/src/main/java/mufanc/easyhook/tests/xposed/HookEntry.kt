package mufanc.easyhook.tests.xposed

import android.app.Activity
import android.content.Context
import com.github.mufanc.easyhook.HookHelper
import com.github.mufanc.easyhook.util.*
import mufanc.easyhook.tests.BuildConfig
import mufanc.easyhook.tests.ui.MainActivity
import java.util.*

class HookEntry : HookHelper() {
    override fun onInitZygote() {
        Log.i("Test: onInitZygote | module path: ${startupParam.modulePath}")
    }

    override fun onHandleLoadPackage() {
        if (lpparam.packageName != BuildConfig.APPLICATION_ID) {
            return
        }

        Log.i("Test: onHandleLoadPackage | package: ${lpparam.packageName}, process: ${lpparam.processName}")

        findMethod(Activity::class.java) {
            name == "onCreate"
        }!!.hook {
            before {
                it.thisObject.setExtraField("message", "Test: setExtraField | this message was set in onCreate")
                Log.i("Test: hook Activity#onCreate | activity: ${it.thisObject}")
            }
        }

        findMethod(MainActivity::class.java.name) {
            name == "getToastText"
        }!!.hook {
            replace { "Replaced!!" }
        }

        findMethod(MainActivity::class.java.name) {
            name == "onResume"
        }!!.hook {
            after {
                Log.i("Test: getApplication | application: ${ContextUtils.getApplication()}")
                Log.i(it.thisObject.getExtraField("message"))
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        Log.i("Test: getCurrentActivity | activity: ${ContextUtils.getCurrentActivity()}")
                    }
                }, 2000)
            }
        }
    }

    override fun onApplicationAttach(context: Context) {
        Log.i("Test: onApplicationAttach | classloader: ${context.classLoader}")
    }
}
