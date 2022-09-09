package mufanc.easyhook.demo

import android.widget.Toast
import mufanc.easyhook.api.Logger
import mufanc.easyhook.api.annotation.XposedEntry
import mufanc.easyhook.api.hook.HookHelper
import mufanc.easyhook.api.hook.hook
import mufanc.easyhook.api.reflect.findMethod
import java.lang.reflect.Method

@XposedEntry
class HookEntry : HookHelper("EasyHook-Demo") {
    override fun onHook() = handle {
        Logger.configure(target = +Logger.Target.XPOSED_BRIDGE)
        onAttachApplication(BuildConfig.APPLICATION_ID) { _, context ->
            findClass(MainActivity::class.java.name).hook { clazz ->
                method({ name == "onCreate" }) {
                    before {
                        Toast.makeText(context, clazz.signature(), Toast.LENGTH_SHORT).show()
                    }
                }
                clazz.findMethod { name == "onCreate" }!!.hook { method ->
                    after {
                        Toast.makeText(context, method.signature(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun Class<*>.signature(): String {
        return java.lang.reflect.Array.newInstance(this, 0)
            .javaClass.name.replace('.', '/').substring(1)
    }

    private fun Method.signature(): String {
        val builder = StringBuilder("(")
        this.parameterTypes.forEach {
            builder.append(it.signature())
        }
        builder.append(")")
        builder.append(if (returnType == Void.TYPE) "V" else returnType.signature())
        return builder.toString()
    }
}
