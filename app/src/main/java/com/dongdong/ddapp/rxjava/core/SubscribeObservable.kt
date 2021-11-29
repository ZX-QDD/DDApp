package com.dongdong.ddapp.rxjava.core

class SubscribeObservable<T>(
    private val source: ObservableOnSubscribe<T>,
    private val thread: Int
) : ObservableOnSubscribe<T> {

    override fun subscribe(observer: Observer<T>) {
        val downStream = SubscribeObserver(observer)
        Schedulers.INSTANCE.submitSubscribeWork(source, downStream, thread)
    }

    class SubscribeObserver<T>(private val emitter: Observer<T>) : Observer<T> {
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