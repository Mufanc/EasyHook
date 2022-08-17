package mufanc.easyhook.api

// Todo: 自动生成作用域 arrays.xml
abstract class HookHelper(TAG: String) {

    companion object {
        private var instance = false
    }

    init {
        if (instance) {
            throw RuntimeException("`HookHelper` can only be instantiated once.")
        }
        instance = true
        Logger.configure(TAG = TAG)
    }

    abstract fun onHook()

    fun handle(register: EasyHook.() -> Unit) = EasyHook.handle(register)
}
