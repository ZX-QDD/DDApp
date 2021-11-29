package com.dongdong.ddapp.rxjava.core

class MapObservable<T, R>(
    private val source: ObservableOnSubscribe<T>,
    private val func: (T) -> R
) : ObservableOnSubscribe<R> {

    override fun subscribe(observer: Observer<R>) {
        val map = MapObserver(observer, func) //创建自己的观察者对象
        source.subscribe(map)   //将自己传递给上游
    }

    class MapObserver<T, R>(
        private val observer: Observer<R>,
        private val func: (T) -> R
    ) : Observer<T> {
        override fun onSubscribe() {
            observer.onSubscribe()
        }

        override fun onNext(item: T) {
            val result = func.invoke(item)
            observer.onNext(result)
        }

        override fun onError(e: Throwable) {
            observer.onError(e)
        }

        override fun onComplete() {
            observer.onComplete()
        }

    }
}