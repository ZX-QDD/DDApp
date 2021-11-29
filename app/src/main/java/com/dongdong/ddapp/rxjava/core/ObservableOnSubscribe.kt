package com.dongdong.ddapp.rxjava.core

/**
 * 真正的被观察者（上游）
 */
interface ObservableOnSubscribe<T> {

    //订阅，设置下游
    fun subscribe(observer: Observer<T>)

}