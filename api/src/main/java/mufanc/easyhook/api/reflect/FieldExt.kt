package mufanc.easyhook.api.reflect

import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Field

// For objects

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

// For classes

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

// Search for field

inline fun Class<*>.findField(filter: Field.() -> Boolean): Field? {
    return declaredFields.find(filter)?.apply { isAccessible = true }
}

inline fun Class<*>.findFields(filter: Field.() -> Boolean): List<Field> {
    return declaredFields.filter(filter).onEach { it.isAccessible = true }
}
