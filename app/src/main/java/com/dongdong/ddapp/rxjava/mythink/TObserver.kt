package com.dongdong.ddapp.rxjava.mythink

interface TObserver<T> {

    fun onSubscribe()

    fun onNext(item: T)

    fun onError(e: Throwable)

    fun onComplete()
}