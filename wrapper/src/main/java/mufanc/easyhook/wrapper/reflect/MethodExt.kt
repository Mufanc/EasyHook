package mufanc.easyhook.wrapper.reflect

import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Constructor
import java.lang.reflect.Method

// For objects

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

// For classes

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

// Search for method/constructor

inline fun Class<*>.findConstructor(filter: Constructor<*>.() -> Boolean): Constructor<*>? {
    return declaredConstructors.find(filter)?.apply { isAccessible = true }
}

inline fun Class<*>.findConstructors(filter: Constructor<*>.() -> Boolean): List<Constructor<*>> {
    return declaredConstructors.filter(filter).onEach { it.isAccessible = true }
}

inline fun Class<*>.findMethod(filter: Method.() -> Boolean): Method? {
    return declaredMethods.find(filter)?.apply { isAccessible = true }
}

inline fun Class<*>.findMethods(filter: Method.() -> Boolean): List<Method> {
    return declaredMethods.filter(filter).onEach { it.isAccessible = true }
}
