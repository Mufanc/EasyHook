package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XposedHelpers

/**
 * 调用对象的某个方法
 * @param name: 方法名
 * @param args: 参数列表...
 * @return 方法的返回值
 */
fun Any.invokeMethod(name: String, vararg args: Any?): Any? {
    return XposedHelpers.callMethod(this, name, *args)
}

/**
 * 调用对象的某个方法并将返回值转换为指定类型
 * @param name: 方法名
 * @param args: 参数列表...
 * @return 方法的返回值
 */
@Suppress("Unchecked_Cast")
fun <T> Any.invokeMethodAs(name: String, vararg args: Any?): T? {
    return invokeMethod(name, *args) as T
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
    return getField(name) as T
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
    return getFieldOrNull(name) as T
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
    return getStaticField(name) as T
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
    return getStaticFieldOrNull(name) as T
}
