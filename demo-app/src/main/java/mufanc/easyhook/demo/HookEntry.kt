package mufanc.easyhook.demo

import android.widget.Toast
import mufanc.easyhook.wrapper.HookHelper
import mufanc.easyhook.wrapper.Logger
import mufanc.easyhook.wrapper.annotation.XposedEntry
import mufanc.easyhook.wrapper.hook
import mufanc.easyhook.wrapper.reflect.findMethod

@XposedEntry
class HookEntry : HookHelper("EasyHook-Demo") {
    override fun onHook() = handle {
        Logger.configure(toXposedBridge = true)
        onAttachApplication(BuildConfig.APPLICATION_ID) { _, context ->
            findClass(MainActivity::class.java.name) hook { clazz ->
                method({ name == "onCreate" }) {
                    before {
                        Toast.makeText(context, clazz.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                clazz.findMethod { name == "onCreate" }!! hook { method ->
                    after {
                        Toast.makeText(context, method.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
