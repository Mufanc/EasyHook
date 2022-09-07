package mufanc.easyhook.api

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit

interface IXposedEntry : IXposedHookZygoteInit, IXposedHookLoadPackage
