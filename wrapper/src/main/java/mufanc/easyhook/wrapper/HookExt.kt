package mufanc.easyhook.wrapper

import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member

// Todo: 使用扩展方法代替中缀表达式

inline infix fun Class<*>.hook(initializer: HookCreator.(Class<*>) -> Unit) {
    initializer(HookCreator(this), this)
}

inline infix fun <T : Member>T.hook(initializer: HookCreator.Hooker.(T) -> Unit) {
    XposedBridge.hookMethod(
        this,
        HookCreator.Hooker(XCallback.PRIORITY_DEFAULT).apply { initializer(this, this@hook) }
    )
}
