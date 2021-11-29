package com.dongdong.ddapp.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dongdong.ddapp.R
import com.dongdong.ddapp.rxjava.core.Observable
import com.dongdong.ddapp.rxjava.core.ObservableOnSubscribe
import com.dongdong.ddapp.rxjava.core.Observer
import com.dongdong.ddapp.rxjava.core.Schedulers

class RxjavaActivity : AppCompatActivity() {
    private val TAG = "RxjavaActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)

        Observable.create(
            object : ObservableOnSubscribe<String> {  //创建实际的被观察者
                override fun subscribe(observer: Observer<String>) {  //重写subscribe方法完成发送数据
                    val data = "三点几啦，饮茶了喂！"
                    Log.d(TAG, "上游发送数据：$data")
                    Log.d(TAG, "上游线程:${Thread.currentThread().name}")
                    observer.onNext(data)
                }
            })
            .subscribeOn(Schedulers.IO())
            .observerOn(Schedulers.mainThread())
            .map { item -> "转换后的数据：$item" }
            .subscribe(object : Observer<String> {  //通过DDObservable中subscribe方法完成订阅
                override fun onSubscribe() {
                    Log.d(TAG, "onSubscribe")
                }

                override fun onNext(item: String) {
                    Log.d(TAG, "下游接收到数据：$item")
                    Log.d(TAG, "下游线程:${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}

            })
    }

}