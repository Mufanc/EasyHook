package mufanc.easyhook.api.hook

import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member

inline fun Class<*>.hook(initializer: HookCreator.(Class<*>) -> Unit) {
    initializer(HookCreator(this), this)
}

inline fun <T : Member> T.hook(
    priority: Int = XCallback.PRIORITY_DEFAULT,
    initializer: HookCreator.Hooker.(T) -> Unit
) {
    XposedBridge.hookMethod(
        this,
        HookCreator.Hooker(priority).apply { initializer(this, this@hook) }
    )
}
