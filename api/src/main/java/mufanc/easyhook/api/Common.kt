package mufanc.easyhook.api

import java.lang.reflect.Method

inline fun catch(block: () -> Unit) {
    try {
        block()
    } catch (err: Throwable) {
        Logger.e(err = err)
    }
}

fun Class<*>.signature(): String {
    return java.lang.reflect.Array.newInstance(this, 0)
        .javaClass.name.replace('.', '/').substring(1)
}

fun Method.signature(): String {
    val builder = StringBuilder("(")
    this.parameterTypes.forEach {
        builder.append(it.signature())
    }
    builder.append(")")
    builder.append(if (returnType == Void.TYPE) "V" else returnType.signature())
    return builder.toString()
}
