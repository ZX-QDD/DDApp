package com.dongdong.ddapp.rxjava.core

/**
 * 观察者（下游）
 */
interface Observer<T> {

    fun onSubscribe()

    fun onNext(item: T)

    fun onError(e: Throwable)

    fun onComplete()
}