package com.dongdong.ddapp.rxjava.mythink

interface TObservable<T> {

    //订阅，设置下游
    fun subscribe(observer: TObserver<T>)
}