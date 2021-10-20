package com.dongdong.ddapp.rxjava.core

/**
 * 观察者（下游）
 */
interface DDObserver<T> {

    fun onSubscribe()

    fun onNext(item: T)

    fun onError(e: Throwable)

    fun onComplete()
}