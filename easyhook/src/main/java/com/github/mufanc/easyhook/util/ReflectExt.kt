package com.github.mufanc.easyhook.util

import java.lang.reflect.Method

private fun findMethodBestMatch(clazz: Class<*>, name: String, vararg args: Any?): Method? {
    try {
        return findMethods(clazz) {
                parameterCount == args.size && this.name == name
            }.find {
                it.parameterTypes.forEachIndexed { index, clazz ->
                    val arg = args[index] ?: return@forEachIndexed
                    if (clazz.isPrimitive) {
                        if (arg.javaClass.getDeclaredField("TYPE").get(null) != clazz) return@find false
                    } else {
                        if (!clazz.isInstance(arg)) return@find false
                    }
                }
                true
            }?.also { it.isAccessible = true }
    } catch (ignored: NoSuchMethodException) {
        return null
    }
}

/**
 * 调用对象的某个方法
 * @param name: 方法名
 * @param args: 参数列表...K
 * @return 方法的返回值
 */
fun Any.callMethod(name: String, vararg args: Any?): Any? {
    val method = findMethodBestMatch(javaClass, name, *args)
    return method!!.invoke(this, *args)
}

/**
 * 调用对象的某个方法并将返回值转换为指定类型
 * @param name: 方法名
 * @param args: 参数列表...
 * @return 方法的返回值
 */
@Suppress("Unchecked_Cast")
fun <T> Any.callMethodAs(name: String, vararg args: Any?): T? {
    return callMethod(name, *args) as T?
}

/**
 * 获取对象的某个属性
 * @param name: 属性名
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
fun Any.getField(name: String): Any? {
    findField(javaClass) {
        this.name == name
    }?.let {
        return it.get(this)
    } ?: throw NoSuchFieldException()
}

/**
 * 获取对象的某个属性并转换为指定类型
 * @param name: 属性名
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
@Suppress("Unchecked_Cast")
fun <T> Any.getFieldAs(name: String): T? {
    return getField(name) as T?
}

/**
 * 获取对象的某个属性
 * @param name: 属性名
 * @return 属性值，找不到目标属性时将返回 null
 */
fun Any.getFieldOrNull(name: String): Any? {
    findField(javaClass) {
        this.name == name
    }?.let { return it.get(this) }
    return null
}

/**
 * 获取对象的某个属性并转换为指定类型
 * @param name: 属性名
 * @return 属性值，找不到目标属性时将返回 null
 */
@Suppress("Unchecked_Cast")
fun <T> Any.getFieldOrNullAs(name: String): T? {
    return getFieldOrNull(name) as T?
}

/**
 * 调用类的某个静态方法
 * @param name: 方法名
 * @param args: 参数列表...
 * @return 方法的返回值
 */
fun Class<*>.callStaticMethod(name: String, vararg args: Any?): Any? {
    val method = findMethodBestMatch(this, name, *args)
    return method!!.invoke(null, *args)
}

/**
 * 调用类的某个静态方法并将返回值转换为指定类型
 * @param name: 方法名
 * @param args: 参数列表...
 * @return 方法的返回值
 */
@Suppress("Unchecked_Cast")
fun <T> Class<*>.callStaticMethodAs(name: String, vararg args: Any?): T? {
    return callStaticMethod(name, args) as T?
}

/**
 * 获取类的某个静态属性
 * @param name: 属性名
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
fun Class<*>.getStaticField(name: String): Any? {
    findField(this) {
        this.name == name
    }?.let {
        return it.get(null)
    }?: throw NoSuchFieldException()
}

/**
 * 获取类的某个静态属性并转换为指定类型
 * @param name: 属性名
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
@Suppress("Unchecked_Cast")
fun <T> Class<*>.getStaticFieldAs(name: String): T? {
    return getStaticField(name) as T?
}

/**
 * 获取类的某个静态属性
 * @param name: 属性名
 * @return 属性值，找不到目标属性时将返回 null
 */
fun Class<*>.getStaticFieldOrNull(name: String): Any? {
    findField(this) {
        this.name == name
    }?.let { return it.get(null) }
    return null
}

/**
 * 获取类的某个静态属性并转换为指定类型
 * @param name: 属性名
 * @return 属性值，找不到目标属性时将返回 null
 */
@Suppress("Unchecked_Cast")
fun <T> Class<*>.getStaticFieldOrNullAs(name: String): T? {
    return getStaticFieldOrNull(name) as T?
}
