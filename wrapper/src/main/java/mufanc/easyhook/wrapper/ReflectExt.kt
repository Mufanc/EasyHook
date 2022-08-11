package mufanc.easyhook.wrapper

import de.robv.android.xposed.XposedHelpers

fun Any.callMethod(name: String, vararg args: Any?): Any? {
    return XposedHelpers.callMethod(this, name, *args)
}

fun Any.callMethodExact(name: String, parameterTypes: Array<Class<*>>, vararg args: Any?): Any? {
    return XposedHelpers.callMethod(this, name, parameterTypes, *args)
}

@Suppress("Unchecked_Cast")
fun <T> Any.callMethodAs(name: String, vararg args: Any?): T? {
    return callMethod(name, *args) as T?
}

@Suppress("Unchecked_Cast")
fun <T> Any.callMethodExactAs(name: String, parameterTypes: Array<Class<*>>, vararg args: Any?): T? {
    return callMethodExact(name, parameterTypes, *args) as T?
}

fun Any.getField(name: String): Any? {
    return XposedHelpers.getObjectField(this, name)
}

@Suppress("Unchecked_Cast")
fun <T> Any.getFieldAs(name: String): T? {
    return getField(name) as T?
}

fun Any.getFieldOrNull(name: String): Any? {
    return try {
       getField(name)
    } catch (err: NoSuchFieldError) {
        null
    }
}

@Suppress("Unchecked_Cast")
fun <T> Any.getFieldOrNullAs(name: String): T? {
    return getFieldOrNull(name) as T?
}

fun Class<*>.callStaticMethod(name: String, vararg args: Any?): Any? {
    return XposedHelpers.callStaticMethod(this, name, *args)
}

fun Class<*>.callStaticMethodExact(
    name: String, parameterTypes: Array<Class<*>>, vararg args: Any?
): Any? {
    return XposedHelpers.callStaticMethod(this, name, parameterTypes, *args)
}

@Suppress("Unchecked_Cast")
fun <T> Class<*>.callStaticMethodAs(name: String, vararg args: Any?): T? {
    return callStaticMethod(name, args) as T?
}

@Suppress("Unchecked_Cast")
fun <T> Class<*>.callStaticMethodExactAs(
    name: String, parameterTypes: Array<Class<*>>, vararg args: Any?
): Any? {
    return callStaticMethodExact(name, parameterTypes, *args) as T?
}

fun Class<*>.getStaticField(name: String): Any? {
    return XposedHelpers.getStaticObjectField(this, name)
}

@Suppress("Unchecked_Cast")
fun <T> Class<*>.getStaticFieldAs(name: String): T? {
    return getStaticField(name) as T?
}

fun Class<*>.getStaticFieldOrNull(name: String): Any? {
    return try {
        getStaticField(name)
    } catch (err: NoSuchFieldError) {
        null
    }
}

@Suppress("Unchecked_Cast")
fun <T> Class<*>.getStaticFieldOrNullAs(name: String): T? {
    return getStaticFieldOrNull(name) as T?
}
