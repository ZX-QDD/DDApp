package com.dongdong.ddapp.rxjava.core

class DoOnNextObservable<T>(
    val source: ObservableOnSubscribe<T>, val func: ((T) -> Unit)
) : ObservableOnSubscribe<T> {

    override fun subscribe(emitter: Observer<T>) {
        val map = MlxDoOnNextObserver(emitter, func)
        source.subscribe(map)
    }

    class MlxDoOnNextObserver<T>(val emitter: Observer<T>, val func: ((T) -> Unit)) : Observer<T> {

        override fun onSubscribe() {
            emitter.onSubscribe()
        }

        override fun onNext(item: T) {
            val result = func.invoke(item)
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