package mufanc.easyhook.demo

import android.widget.Toast
import mufanc.easyhook.wrapper.HookHelper
import mufanc.easyhook.wrapper.annotation.XposedEntry
import mufanc.easyhook.wrapper.hook

@XposedEntry
class HookEntry : HookHelper("EasyHook-Demo") {
    override fun onHook() = handle {
        onAttachApplication(BuildConfig.APPLICATION_ID) { lpparam, context ->
            findClass(MainActivity::class.java.name) hook {
                method({ name == "onCreate" }) {
                    before {
                        Toast.makeText(context, lpparam.classLoader.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
