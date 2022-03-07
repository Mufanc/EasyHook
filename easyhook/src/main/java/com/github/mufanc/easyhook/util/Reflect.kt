package com.github.mufanc.easyhook.util

import com.github.mufanc.easyhook.Globals
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

typealias MethodFilter = Method.() -> Boolean
typealias ConstructorFilter = Constructor<*>.() -> Boolean
typealias FieldFilter = Field.() -> Boolean

/**
 * 根据类名和类加载器搜索类
 * @param name: 完整类名
 * @param classLoader: 类加载器（可选）
 */
fun findClass(
    name: String,
    classLoader: ClassLoader = Globals.defaultClassLoader
): Class<*> {
    return classLoader.loadClass(name)
}

/**
 * 在类中搜索方法
 * @param clazz: 类对象
 * @param filter: 条件函数
 * @return 第一个满足条件的方法
 */
fun findMethod(
    clazz: Class<*>,
    filter: MethodFilter
): Method? {
    clazz.declaredMethods.forEach {
        it.isAccessible = true
        if (filter(it)) {
            return it
        }
    }
    return null
}

/**
 * 在类中搜索方法
 * @param clazz: 类名字符串
 * @param classLoader: 类加载器（可选）
 * @param filter: 条件函数
 * @return 第一个满足条件的方法
 */
fun findMethod(
    clazz: String,
    classLoader: ClassLoader = Globals.defaultClassLoader,
    filter: MethodFilter
): Method? {
    return findMethod(classLoader.loadClass(clazz), filter)
}

/**
 * 在类中搜索方法
 * @param clazz: 类对象
 * @param filter: 条件函数
 * @return 所有满足条件的方法
 */
fun findMethods(
    clazz: Class<*>,
    filter: MethodFilter
): Array<Method> {
    return clazz.declaredMethods.filter {
        it.isAccessible = true
        filter(it)
    }.toTypedArray()
}

/**
 * 在类中搜索方法
 * @param clazz: 类名字符串
 * @param classLoader: 类加载器（可选）
 * @param filter: 条件函数
 * @return 所有满足条件的方法
 */
fun findMethods(
    clazz: String,
    classLoader: ClassLoader = Globals.defaultClassLoader,
    filter: MethodFilter
): Array<Method> {
    return findMethods(classLoader.loadClass(clazz), filter)
}

/**
 * 在类中搜索构造方法
 * @param clazz: 类名字符串
 * @param filter: 条件函数
 * @return 第一个满足条件的构造函数
 */
fun findConstructor(
    clazz: Class<*>,
    filter: ConstructorFilter
): Constructor<*>? {
    clazz.declaredConstructors.forEach {
        it.isAccessible = true
        if (filter(it)) {
            return it
        }
    }
    return null
}

/**
 * 在类中搜索方法
 * @param clazz: 类名字符串
 * @param classLoader: 类加载器（可选）
 * @param filter: 条件函数
 * @return 第一个满足条件的构造函数
 */
fun findConstructor(
    clazz: String,
    classLoader: ClassLoader = Globals.defaultClassLoader,
    filter: ConstructorFilter
): Constructor<*>? {
    return findConstructor(classLoader.loadClass(clazz), filter)
}

/**
 * 在类中搜索构造方法
 * @param clazz: 类名字符串
 * @param filter: 条件函数
 * @return 所有满足条件的构造函数
 */
fun findConstructors(
    clazz: Class<*>,
    filter: ConstructorFilter
): Array<Constructor<*>> {
    return clazz.declaredConstructors.filter {
        it.isAccessible = true
        filter(it)
    }.toTypedArray()
}

/**
 * 在类中搜索方法
 * @param clazz: 类名字符串
 * @param classLoader: 类加载器（可选）
 * @param filter: 条件函数
 * @return 所有满足条件的构造函数
 */
fun findConstructors(
    clazz: String,
    classLoader: ClassLoader = Globals.defaultClassLoader,
    filter: ConstructorFilter
): Array<Constructor<*>> {
    return findConstructors(classLoader.loadClass(clazz), filter)
}

/**
 * 在类中搜索属性
 * @param clazz: 类对象
 * @param filter: 条件函数
 * @return 第一个满足条件的属性
 */
fun findField(
    clazz: Class<*>,
    filter: FieldFilter
): Field? {
    clazz.declaredFields.forEach {
        it.isAccessible = true
        if (filter(it)) {
            return it
        }
    }
    return null
}

/**
 * 在类中搜索属性
 * @param clazz: 类名字符串
 * @param classLoader: 类加载器（可选）
 * @param filter: 条件函数
 * @return 第一个满足条件的属性
 */
fun findField(
    clazz: String,
    classLoader: ClassLoader = Globals.defaultClassLoader,
    filter: FieldFilter
): Field? {
    return findField(classLoader.loadClass(clazz), filter)
}

/**
 * 在类中搜索属性
 * @param clazz: 类对象
 * @param filter: 条件函数
 * @return 所有满足条件的属性
 */
fun findFields(
    clazz: Class<*>,
    filter: FieldFilter
): Array<Field> {
    return clazz.declaredFields.filter {
        it.isAccessible = true
        filter(it)
    }.toTypedArray()
}

/**
 * 在类中搜索属性
 * @param clazz: 类名字符串
 * @param classLoader: 类加载器（可选）
 * @param filter: 条件函数
 * @return 所有满足条件的属性
 */
fun findFields(
    clazz: String,
    classLoader: ClassLoader = Globals.defaultClassLoader,
    filter: FieldFilter
): Array<Field> {
    return findFields(classLoader.loadClass(clazz), filter)
}
