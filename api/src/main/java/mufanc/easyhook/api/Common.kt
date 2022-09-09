package mufanc.easyhook.api

inline fun catch(block: () -> Unit) {
    try {
        block()
    } catch (err: Throwable) {
        Logger.e(err = err)
    }
}
