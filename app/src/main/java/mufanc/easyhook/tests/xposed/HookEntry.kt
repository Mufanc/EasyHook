package mufanc.easyhook.tests.xposed

import android.app.Activity
import android.app.Application
import android.content.Context
import com.github.mufanc.easyhook.HookHelper
import com.github.mufanc.easyhook.util.*
import mufanc.easyhook.tests.BuildConfig
import mufanc.easyhook.tests.ui.MainActivity
import mufanc.easyhook.tests.ui.TestB
import java.util.*

class HookEntry : HookHelper() {
    override fun onInitZygote() {
        Log.i("Test: onInitZygote | module path: ${startupParam.modulePath}")
    }

    override fun onHandleLoadPackage() {
        if (lpparam.packageName != BuildConfig.APPLICATION_ID ||
                lpparam.processName != BuildConfig.APPLICATION_ID) {
            return
        }

        Log.i("Test: Log vararg | array", 123456, "string", Any())
        Log.i("Test: onHandleLoadPackage | package: ${lpparam.packageName}, process: ${lpparam.processName}")

        findMethod(Activity::class.java) {
            name == "onCreate"
        }!! before {
            it.thisObject.setExtraField("message", "Test: setExtraField | this message was set in onCreate")
            Log.i("Test: hook Activity#onCreate | activity: ${it.thisObject}")
        }

        MainActivity::class.java.name hooks {
            find { name == "getToastText" } replace {
                "Replaced!!"
            }

            find { name == "onCreate" } after { param ->
                with (param.thisObject) {
                    Log.i("Test: callMethod with primitive type | a + b = ${callMethod("add", 3, 5)}")
                    Log.i("Test: callMethodExact | a + b = ${
                            callMethodExact("add", arrayOf(Int::class.java, Int::class.java), 3, 5)
                        }")
                }
            }

            find { name == "onResume" } after {
                Log.i("Test: getApplication | application: ${ContextUtils.getApplication()}")
                Log.i(it.thisObject.getExtraField("message"))
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        Log.i("Test: getCurrentActivity | activity: ${ContextUtils.getCurrentActivity()}")
                    }
                }, 2000)
            }
        }

        val hook = hooker {
            before { param ->
                Log.i("Test \"hooker\" function | $param")
            }
        }

        findMethod(Application::class.java) {
            name == "onCreate"
        }!!.hook(hook)

        findMethod(TestB::class.java.name) {
            name == "method"
        }!! after {
            Log.i("Test: getField | ${it.thisObject.getField("mField", true)}")
        }
    }

    override fun onApplicationAttach(context: Context) {
        Log.i("Test: onApplicationAttach | classloader: ${context.classLoader}")
    }
}
