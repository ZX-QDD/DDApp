package com.dongdong.ddapp.rxjava.core

/**
 * 封装好的被观察者
 */
class Observable<T> constructor() {

    //上游对象
    private var source: ObservableOnSubscribe<T>? = null

    //次构造方法，用于接收上游对象
    constructor(source: ObservableOnSubscribe<T>) : this() {
        this.source = source
    }

    //静态方法封装一个真正的被观察者(emitter)
    companion object {
        fun <T> create(emitter: ObservableOnSubscribe<T>): Observable<T> {
            return Observable(emitter)
        }
    }

    //接收下游对象
    fun subscribe(observer: Observer<T>) {
        observer.onSubscribe()
        //建立联系（设置下游）
        source?.subscribe(observer)
    }

    fun <R> map(func: (T) -> R): Observable<R> {
        val map = MapObservable(this.source!!, func)
        return Observable(map)
    }

    fun subscribeOn(scheduler: Int): Observable<T> {
        val subscribe = SubscribeObservable(this.source!!, scheduler)
        return Observable(subscribe)
    }

    fun observerOn(scheduler: Int): Observable<T> {
        val subscribe = ObserverObservable(this.source!!, scheduler)
        return Observable(subscribe)
    }

    fun doOnNext(func: (T) -> Unit): Observable<T> {
        val subscribe = DoOnNextObservable(this.source!!, func)
        return Observable(subscribe)
    }
}