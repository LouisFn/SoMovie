package com.louisfn.somovie.core.logger

object Logger {

    var adapter: LogAdapter? = null

    fun v(message: String, vararg args: Any?) = adapter?.v(message, args)

    fun v(t: Throwable, message: String, vararg args: Any?) = adapter?.v(t, message, args)

    fun v(t: Throwable) = adapter?.v(t)

    fun d(message: String, vararg args: Any?) = adapter?.d(message, args)

    fun d(t: Throwable, message: String, vararg args: Any?) = adapter?.d(t, message, args)

    fun d(t: Throwable) = adapter?.d(t)

    fun i(message: String, vararg args: Any?) = adapter?.i(message, args)

    fun i(t: Throwable, message: String, vararg args: Any?) = adapter?.i(t, message, args)

    fun i(t: Throwable) = adapter?.i(t)

    fun w(message: String, vararg args: Any?) = adapter?.w(message, args)

    fun w(t: Throwable, message: String, vararg args: Any?) = adapter?.w(t, message, args)

    fun w(t: Throwable) = adapter?.w(t)

    fun e(message: String, vararg args: Any?) = adapter?.e(message, args)

    fun e(t: Throwable, message: String, vararg args: Any?) = adapter?.e(t, message, args)

    fun e(t: Throwable) = adapter?.e(t)

    fun wtf(message: String, vararg args: Any?) = adapter?.wtf(message, args)

    fun wtf(t: Throwable, message: String, vararg args: Any?) = adapter?.wtf(t, message, args)

    fun wtf(t: Throwable) = adapter?.wtf(t)
}
