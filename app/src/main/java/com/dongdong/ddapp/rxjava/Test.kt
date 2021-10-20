package com.dongdong.ddapp.rxjava

import android.annotation.SuppressLint
import com.dongdong.ddapp.rxjava.core.*
import com.dongdong.ddapp.rxjava.mythink.Box
import com.dongdong.ddapp.rxjava.mythink.TObservable
import com.dongdong.ddapp.rxjava.mythink.TObserver

import io.reactivex.Observable

//fun main() {
//    //创建一个被观察者
//    val observable = Observable()
//    //创建一个观察者
//    val observer = Observer()
//    //观察者订阅被观察者 -- 建立两个角色的联系（将观察者加入订阅集合）
//    observable.subscribe(observer)
//    //被观察者通知订阅自己的观察者事情发生改变
//    observable.notifyObserver()
//}

fun main() {
    test()
}

@SuppressLint("CheckResult")
fun test() {

//    //创建上游（被观察者）
//    Observable.create<Int> { emitter ->
//        //发送事件出去
//        emitter.onNext(10)
//    }.subscribe { item -> //1）订阅，建立上下游连接  2）创建下游（观察者）
//        //在下游处理事件
//        println(item)
//    }

    DDObservable.create(object : DDObservableOnSubscribe<String> {  //创建实际的被观察者
        override fun subscribe(observer: DDObserver<String>) {  //重写subscribe方法完成发送数据
            val data = "三点几啦，饮茶了喂！"
            println("上游发送数据：$data")
            observer.onNext(data)
        }
    }).map { item ->
        "转换后的数据：$item"
    }.subscribeOn(Schedulers.IO())
        .subscribe(object : DDObserver<String> {  //通过DDObservable中subscribe方法完成订阅
        override fun onSubscribe() {
            println("onSubscribe")
        }

        override fun onNext(item: String) {
            println("下游接收到数据：$item")
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {}

    })

    DDObservable.create(DDObservableOnSubscribe<Int> {
        println("zzz", "上游线程:${Thread.currentThread().name}")
        it.onNext(10)
    })
        .subscribeOn(Schedulers.IO())
        .subscribe(object : MlxObserver<Int> {
            override fun onNext(item: Int) {
                Log.i("zzz", "下游线程:${Thread.currentThread().name}")
            }
            ...
        })
}