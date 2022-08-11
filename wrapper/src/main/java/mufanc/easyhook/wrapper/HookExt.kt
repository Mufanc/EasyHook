package mufanc.easyhook.wrapper

import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member

inline infix fun Class<*>.hook(init: HookCreator.(Class<*>) -> Unit) {
    init(HookCreator(this), this)
}

inline infix fun <T : Member>T.hook(init: HookCreator.Hooker.(T) -> Unit) {
    XposedBridge.hookMethod(
        this,
        HookCreator.Hooker(XCallback.PRIORITY_DEFAULT).apply { init(this, this@hook) }
    )
}
