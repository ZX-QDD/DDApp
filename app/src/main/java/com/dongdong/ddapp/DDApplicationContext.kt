package com.dongdong.ddapp

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.res.AssetManager
import android.content.res.Resources

object DDApplicationContext {

    private lateinit var context: Application

    fun setApp(c: Application) {
        context = c
    }

    @Deprecated("")
    fun getContext(): Application {
        return context
    }

    fun getApp(): Application {
        return context
    }

    fun getPackageName(): String? {
        return context.packageName
    }

    fun getResources(): Resources {
        return context.resources
    }

    fun getString(resourceId: Int): String =
        getResources().getString(resourceId)

    fun getString(resourceId: Int, vararg formatArgs: Any?): String? {
        return getResources().getString(resourceId, formatArgs)
    }

    fun getAssets(): AssetManager? {
        return context.assets
    }

    fun getApplicationInfo(): ApplicationInfo? {
        return context.applicationInfo
    }
}