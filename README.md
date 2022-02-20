# EasyHook

* 让 Xposed 模块开发变得更简单

**注意：本项目仍处于早期开发阶段，API 可能有较大变动**

## 使用方法

```groovy
allprojects {
    ...
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    ...
    implementation 'com.github.mufanc:EasyHook:<version>'
}
```

## 示例

* 原始写法

```kotlin
class HookEntry : IXposedHookLoadPackage {
    
    companion object {
        const val TAG = "CustomTAG"
    }
    
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedHelpers.findAndHookMethod(
            Activity::class.java,
            "onCreate",
            Bundle::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.i(TAG, "Activity created!")
                }
            }
        )
    }
}
```

* 使用 EasyHook 后

```kotlin
class HookEntry : HookHelper("CustomTAG") {
    
    override fun onHandleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        findMethod(Activity::class.java) {
            name == "onCreate"
        }.afterCall {
            Log.i("Activity created!")
        }
    }
}
```
