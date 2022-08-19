package mufanc.easyhook.demo

import android.widget.Toast
import mufanc.easyhook.api.Logger
import mufanc.easyhook.api.annotation.XposedEntry
import mufanc.easyhook.api.hook.HookHelper
import mufanc.easyhook.api.hook.hook
import mufanc.easyhook.api.reflect.findMethod

@XposedEntry
class HookEntry : HookHelper("EasyHook-Demo") {
    override fun onHook() = handle {
        Logger.configure(toXposedBridge = true)
        onAttachApplication(BuildConfig.APPLICATION_ID) { _, context ->
            findClass(MainActivity::class.java.name).hook { clazz ->
                method({ name == "onCreate" }) {
                    before {
                        Toast.makeText(context, clazz.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                clazz.findMethod { name == "onCreate" }!!.hook { method ->
                    after {
                        Toast.makeText(context, method.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
