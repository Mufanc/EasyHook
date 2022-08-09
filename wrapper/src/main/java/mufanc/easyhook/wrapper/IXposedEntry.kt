package mufanc.easyhook.wrapper

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit

interface IXposedEntry : IXposedHookZygoteInit, IXposedHookLoadPackage