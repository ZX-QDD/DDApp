package com.dongdong.ddapp.rxjava.core

/**
 * 封装好的被观察者
 */
class DDObservable<T> constructor() {

    //上游对象
    private var source: DDObservableOnSubscribe<T>? = null

    //次构造方法，用于接收上游对象
    constructor(source: DDObservableOnSubscribe<T>) : this() {
        this.source = source
    }

    //静态方法封装一个真正的被观察者(emitter)
    companion object {
        fun <T> create(emitter: DDObservableOnSubscribe<T>): DDObservable<T> {
            return DDObservable(emitter)
        }
    }

    //接收下游对象
    fun subscribe(observer: DDObserver<T>) {
        observer.onSubscribe()
        //建立联系（设置下游）
        source?.subscribe(observer)
    }

    fun <R> map(func: (T) -> R): DDObservable<R> {
        val map = DDMapObservable(this.source!!, func)
        return DDObservable(map)
    }

    fun subscribeOn(): DDObservable<T> {
        val subscribe = DDSubscribeObservable(this.source!!)
        return DDObservable(subscribe)
    }
}