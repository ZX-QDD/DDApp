package com.dongdong.ddapp.rxjava.core

class DDMapObservable<T, R>(
    private val source: DDObservableOnSubscribe<T>,
    private val func: (T) -> R
) : DDObservableOnSubscribe<R> {

    override fun subscribe(observer: DDObserver<R>) {
        val map = DDMapObserver(observer, func) //创建自己的观察者对象
        source.subscribe(map)   //将自己传递给上游
    }

    class DDMapObserver<T, R>(
        private val observer: DDObserver<R>,
        private val func: (T) -> R
    ) : DDObserver<T> {
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