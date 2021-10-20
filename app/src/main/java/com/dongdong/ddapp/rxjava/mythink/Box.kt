package com.dongdong.ddapp.rxjava.mythink


class Box<T> constructor() {
    //上游对象
    private var source: TObservable<T>? = null

    //次构造方法，用于接收上游对象
    constructor(source: TObservable<T>) : this() {
        this.source = source
    }

    //静态方法封装一个真正的被观察者(emitter)
    companion object {
        fun <T> create(emitter: TObservable<T>): Box<T> {
            return Box(emitter)
        }
    }

    //接收下游对象
    fun subscribe(observer: TObserver<T>) {
        observer.onSubscribe()
        //建立联系（设置下游）
        source?.subscribe(observer)
    }

//    fun <R> map(func: (T) -> R): TObservable<R> {
//        val map = DDMapObservable(this.source!!, func)
//        return DDObservable(map)
//    }
}