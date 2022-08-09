package mufanc.easyhook.wrapper

inline infix fun Class<*>.hook(init: HookCreator.() -> Unit) {
    init(HookCreator(this))
}
