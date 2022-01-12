package com.dongdong.ddapp.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initView()
        lifecycleLog("onCreate")
    }

    abstract fun getLayoutId(): Int

    open fun initView() {
    }

    open fun initData() {
    }

    override fun onStart() {
        super.onStart()
        lifecycleLog("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        lifecycleLog("onRestart")
    }

    override fun onResume() {
        super.onResume()
        lifecycleLog("onResume")
    }

    override fun onPause() {
        super.onPause()
        lifecycleLog("onPause")
    }

    override fun onStop() {
        super.onStop()
        lifecycleLog("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleLog("onDestroy")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        lifecycleLog("onNewIntent")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        lifecycleLog("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lifecycleLog("onRestoreInstanceState")
    }

    fun lifecycleLog(lifecycle: String) {
        Log.i("BaseActivity", "${this.javaClass.simpleName} ==> $lifecycle")
    }
}