package com.dongdong.ddapp.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongdong.ddapp.R
import com.dongdong.ddapp.rxjava.core.DDObservable
import com.dongdong.ddapp.rxjava.core.DDObservableOnSubscribe
import com.dongdong.ddapp.rxjava.core.DDObserver

class RxjavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)
    }

}