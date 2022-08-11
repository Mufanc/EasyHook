package mufanc.easyhook.wrapper

import de.robv.android.xposed.XposedBridge

inline fun catch(block: () -> Unit) {
    try {
        block()
    } catch (err: Throwable) {
        Logger.e(err)
        XposedBridge.log(err)
    }
}