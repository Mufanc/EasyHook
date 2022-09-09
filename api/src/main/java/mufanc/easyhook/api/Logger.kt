package mufanc.easyhook.api

import android.util.Log
import de.robv.android.xposed.XposedBridge

object Logger {

    enum class Level(val priority: Int) {
        TRACE(1000),
        DEBUG(2000),
        INFO(3000),
        WARN(4000),
        ERROR(5000)
    }

    enum class Target(val flag: Int) {
        LOGCAT(1 shl 0), XPOSED_BRIDGE(1 shl 1);

        operator fun unaryPlus(): Int {
            return flag
        }

        operator fun unaryMinus(): Int {
            return -(flag xor -1)
        }
    }

    interface Handler {
        fun onLogPrinted(level: Level, message: String, err: Throwable?)
    }

    private var logcatTag = "EasyHook"
    private var printTo = +Target.LOGCAT
    private var logLevel = Level.DEBUG
    private var logHandler: Handler? = null

    fun configure(TAG: String? = null, target: Int? = null, level: Level? = null, handler: Handler? = null) {
        TAG?.let { logcatTag = it }
        target?.let {
            printTo = if (target > 0) {
                printTo or target
            } else {
                printTo and (-1 xor -target)
            }
        }
        level?.let { logLevel = level }
        handler?.let { logHandler = it }
    }

    private fun log(level: Level, args: Array<out Any?>, err: Throwable? = null) {
        if (level.priority < logLevel.priority) return
        var message = args.joinToString(" ") { "$it" }

        if (printTo and Target.LOGCAT.flag != 0) {
            err?.let {
                message += "\n" + Log.getStackTraceString(err)
            }
            when (level) {
                Level.TRACE -> Log.v(logcatTag, message)
                Level.DEBUG -> Log.d(logcatTag, message)
                Level.INFO -> Log.i(logcatTag, message)
                Level.WARN -> Log.w(logcatTag, message)
                Level.ERROR -> Log.e(logcatTag, message)
            }
        }

        if (printTo and Target.XPOSED_BRIDGE.flag != 0) {
            val prefix = when (level) {
                Level.TRACE -> "[TRACE]"
                Level.DEBUG -> "[DEBUG]"
                Level.INFO -> "[ INFO]"
                Level.WARN -> "[ WARN]"
                Level.ERROR -> "[ERROR]"
            }
            XposedBridge.log("$prefix $message")
            err?.let {
                XposedBridge.log(err)
            }
        }

        logHandler?.apply {
            onLogPrinted(level, message, err)
        }
    }

    fun v(vararg args: Any?, err: Throwable? = null) = log(Level.TRACE, args, err)

    fun d(vararg args: Any?, err: Throwable? = null) = log(Level.DEBUG, args, err)

    fun i(vararg args: Any?, err: Throwable? = null) = log(Level.INFO, args, err)

    fun w(vararg args: Any?, err: Throwable? = null) = log(Level.WARN, args, err)

    fun e(vararg args: Any?, err: Throwable? = null) = log(Level.ERROR, args, err)
}
