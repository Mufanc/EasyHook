package mufanc.easyhook.wrapper

import de.robv.android.xposed.XposedBridge

inline fun catchAndLogExceptions(block: () -> Unit) {
    try {
        block()
    } catch (err: Throwable) {
        Logger.e(err)
        XposedBridge.log(err)
    }
}

inline infix fun Class<*>.hook(init: HookCreator.() -> Unit) {
    init(HookCreator(this))
}
