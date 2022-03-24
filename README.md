[![](https://jitpack.io/v/Mufanc/EasyHook.svg)](https://jitpack.io/#Mufanc/EasyHook)

# EasyHook

* 让 Xposed 模块开发变得更简单

**注意：本项目仍处于早期开发阶段，API 可能有较大变动**

## 使用方法

```groovy
allprojects {
    // ......
    repositories {
        // ......
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    // ......
    implementation 'com.github.Mufanc:EasyHook:<version>'
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
        } after {
            Log.i("Activity created!") 
        }
    }
}
```

更多使用方法参见 app demo

## 闲言碎语

* 整个项目尚处于测试阶段，所以可能 BUG 略多，还请大家见谅

* 当然，如果你愿意使用我的库并将遇到问题反馈到 issue（记得带上复现方式澳），我会非常开心的
  
* 如果有大神能够 PR，那就最最最最最好了！
