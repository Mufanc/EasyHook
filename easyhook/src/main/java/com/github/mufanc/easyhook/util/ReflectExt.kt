package com.github.mufanc.easyhook.util

import de.robv.android.xposed.XposedHelpers

/**
 * 调用对象的某个方法
 * @param name: 方法名
 * @param args: 参数列表...
 * @return 方法的返回值
 */
fun Any.callMethod(name: String, vararg args: Any?): Any? {
    return XposedHelpers.callMethod(this, name, *args)
}

/**
 * 根据给定的参数类型，调用对象的某个方法
 * @param name: 方法名
 * @param parameterTypes: 参数类型数组
 * @param args: 参数列表...
 * @return 方法的返回值
 */
fun Any.callMethodExact(name: String, parameterTypes: Array<Class<*>>, vararg args: Any?): Any? {
    return XposedHelpers.callMethod(this, name, parameterTypes, *args)
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
 * 根据给定的参数类型，调用对象的某个方法并将返回值转换为指定类型
 * @param name: 方法名
 * @param parameterTypes: 参数类型数组
 * @param args: 参数列表...
 * @return 方法的返回值
 */
@Suppress("Unchecked_Cast")
fun <T> Any.callMethodExactAs(name: String, parameterTypes: Array<Class<*>>, vararg args: Any?): T? {
    return callMethodExact(name, parameterTypes, *args) as T?
}

/**
 * 获取对象的某个属性
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
fun Any.getField(name: String, recursive: Boolean = false): Any? {
    findField(javaClass, recursive) {
        this.name == name
    }?.let { return it.get(this) }
    throw NoSuchFieldException()
}

/**
 * 获取对象的某个属性并转换为指定类型
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
@Suppress("Unchecked_Cast")
fun <T> Any.getFieldAs(name: String, recursive: Boolean = false): T? {
    return getField(name, recursive) as T?
}

/**
 * 获取对象的某个属性
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值，找不到目标属性时将返回 null
 */
fun Any.getFieldOrNull(name: String, recursive: Boolean = false): Any? {
    findField(javaClass, recursive) {
        this.name == name
    }?.let { return it.get(this) }
    return null
}

/**
 * 获取对象的某个属性并转换为指定类型
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值，找不到目标属性时将返回 null
 */
@Suppress("Unchecked_Cast")
fun <T> Any.getFieldOrNullAs(name: String, recursive: Boolean = false): T? {
    return getFieldOrNull(name, recursive) as T?
}

/**
 * 调用类的某个静态方法
 * @param name: 方法名
 * @param args: 参数列表...
 * @return 方法的返回值
 */
fun Class<*>.callStaticMethod(name: String, vararg args: Any?): Any? {
    return XposedHelpers.callStaticMethod(this, name, *args)
}

/**
 * 根据给定的参数类型，调用类的某个静态方法
 * @param name: 方法名
 * @param parameterTypes: 参数类型数组
 * @param args: 参数列表...
 * @return 方法的返回值
 */
fun Class<*>.callStaticMethodExact(
    name: String, parameterTypes: Array<Class<*>>, vararg args: Any?
): Any? {
    return XposedHelpers.callStaticMethod(this, name, parameterTypes, *args)
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
 * 根据给定的参数类型，调用类的某个静态方法并将返回值转换为指定类型
 * @param name: 方法名
 * @param parameterTypes: 参数类型数组
 * @param args: 参数列表...
 * @return 方法的返回值
 */
@Suppress("Unchecked_Cast")
fun <T> Class<*>.callStaticMethodExactAs(
    name: String, parameterTypes: Array<Class<*>>, vararg args: Any?
): Any? {
    return callStaticMethodExact(name, parameterTypes, *args) as T?
}

/**
 * 获取类的某个静态属性
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
fun Class<*>.getStaticField(name: String, recursive: Boolean = false): Any? {
    findField(this, recursive) {
        this.name == name
    }?.let { return it.get(null) }
    throw NoSuchFieldException()
}

/**
 * 获取类的某个静态属性并转换为指定类型
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值
 * @throws NoSuchFieldException 未找到属性
 */
@Suppress("Unchecked_Cast")
fun <T> Class<*>.getStaticFieldAs(name: String, recursive: Boolean = false): T? {
    return getStaticField(name, recursive) as T?
}

/**
 * 获取类的某个静态属性
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值，找不到目标属性时将返回 null
 */
fun Class<*>.getStaticFieldOrNull(name: String, recursive: Boolean = false): Any? {
    findField(this, recursive) {
        this.name == name
    }?.let { return it.get(null) }
    return null
}

/**
 * 获取类的某个静态属性并转换为指定类型
 * @param name: 属性名
 * @param recursive: 是否递归查找父类
 * @return 属性值，找不到目标属性时将返回 null
 */
@Suppress("Unchecked_Cast")
fun <T> Class<*>.getStaticFieldOrNullAs(name: String, recursive: Boolean = false): T? {
    return getStaticFieldOrNull(name, recursive) as T?
}
