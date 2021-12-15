package com.dongdong.ddapp.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity  : AppCompatActivity() {

    val TAG = "BaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initView()
        Log.i(TAG, "${this.javaClass.simpleName} ==> onCreate")
    }

    abstract fun getLayoutId(): Int

    open fun initView() {
    }

    open fun initData() {
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "${this.javaClass.simpleName} ==> onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "${this.javaClass.simpleName} ==> onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "${this.javaClass.simpleName} ==> onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "${this.javaClass.simpleName} ==> onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "${this.javaClass.simpleName} ==> onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "${this.javaClass.simpleName} ==> onDestroy")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "${this.javaClass.simpleName} ==> onNewIntent")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "${this.javaClass.simpleName} ==> onSaveInstanceState")
    }
}