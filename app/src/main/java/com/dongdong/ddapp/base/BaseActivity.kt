package com.dongdong.ddapp.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        Log.i("BaseActivity", "onCreate ==> $this")
    }

    abstract fun getLayoutId(): Int

    override fun onStart() {
        super.onStart()
        Log.i("BaseActivity", "onStart ==> $this")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("BaseActivity", "onRestart ==> $this")
    }

    override fun onResume() {
        super.onResume()
        Log.i("BaseActivity", "onResume ==> $this")
    }

    override fun onPause() {
        super.onPause()
        Log.i("BaseActivity", "onPause ==> $this")
    }

    override fun onStop() {
        super.onStop()
        Log.i("BaseActivity", "onStop ==> $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("BaseActivity", "onDestroy ==> $this")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i("BaseActivity", "onNewIntent ==> $this")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("BaseActivity", "onSaveInstanceState ==> $this")
    }
}