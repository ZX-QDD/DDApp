package com.dongdong.ddapp.rxjava.core

class ObserverObservable<T>(val source: ObservableOnSubscribe<T>, private val thread: Int) :
    ObservableOnSubscribe<T> {

    override fun subscribe(emitter: Observer<T>) {
        val downStream = MlxObserverObserver(emitter, thread)
        source.subscribe(downStream)
    }

    class MlxObserverObserver<T>(val emitter: Observer<T>, val thread: Int) : Observer<T> {

        override fun onSubscribe() {
            Schedulers.INSTANCE.submitObserverWork({
                emitter.onSubscribe()
            }, thread)
        }

        override fun onNext(item: T) {
            Schedulers.INSTANCE.submitObserverWork({
                emitter.onNext(item)
            }, thread)
        }

        override fun onError(e: Throwable) {
            Schedulers.INSTANCE.submitObserverWork({
                emitter.onError(e)
            }, thread)
        }

        override fun onComplete() {
            Schedulers.INSTANCE.submitObserverWork({
                emitter.onComplete()
            }, thread)
        }

    }

}