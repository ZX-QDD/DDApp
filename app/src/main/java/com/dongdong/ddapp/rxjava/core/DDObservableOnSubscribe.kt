package com.dongdong.ddapp.rxjava.core

/**
 * 真正的被观察者（上游）
 */
interface DDObservableOnSubscribe<T> {

    //订阅，设置下游
    fun subscribe(observer: DDObserver<T>)

}