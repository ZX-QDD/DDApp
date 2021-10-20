package com.dongdong.ddapp.rxjava.core

class DDSubscribeObservable<T>(
    private val source: DDObservableOnSubscribe<T>,
    private val thread: Int //看这里，新增了指定线程
) : DDObservableOnSubscribe<T> {

    override fun subscribe(emitter: DDObserver<T>) {
        val downStream = DDSubscribeObserver(emitter)
        Schedulers.INSTANCE.submitSubscribeWork(source, downStream, thread)
    }

    class DDSubscribeObserver<T>(val emitter: DDObserver<T>) : DDObserver<T> {
        override fun onSubscribe() {
            emitter.onSubscribe()
        }

        override fun onNext(item: T) {
            emitter.onNext(item)
        }

        override fun onError(e: Throwable) {
            emitter.onError(e)
        }

        override fun onComplete() {
            emitter.onComplete()
        }

    }
}