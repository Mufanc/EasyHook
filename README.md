# EasyHook

* Xposed API wrapper for Kotlin

## Usage

* In project root directory

```shell
git submodule add -b <branch> 'https://github.com/Mufanc/EasyHook' easyhook 
```

* In `settings.gradle.kts`

```kotlin
include(
    ":easyhook:api",
    ":easyhook:ksp-xposed"
)
```

* In module `build.gradle.kts`

```kotlin
plugins {
    id("com.google.devtools.ksp") version "<version>"
}

dependencies {
    implementation(project(":easyhook:api"))
    ksp(project(":easyhook:ksp-xposed"))
}
```

## Demo

* Before

```kotlin
class HookEntry : IXposedHookLoadPackage {
    
    companion object {
        const val TAG = "CustomTAG"
    }
    
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == BuildConfig.APPLICATION_ID) {
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
}
```

* After

```kotlin
@XposedEntry
class HookEntry : HookHelper("CustomTAG") {
    override fun onHook() = handle {
        onLoadPackage(BuildConfig.APPLICATION_ID) {
            Activity::class.java.hook {
                method({ name == "onCreate" }) {
                    Logger.i("Activity created!")
                }
            }
        }
    }
}
```
