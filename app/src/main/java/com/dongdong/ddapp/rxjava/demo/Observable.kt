package com.dongdong.ddapp.rxjava.demo

class Observable {
    //观察者订阅集合
    private val observerList = mutableListOf<Observer>()

    //订阅方法
    fun subscribe(observer: Observer) {
        observerList.add(observer)
    }

    fun notifyObserver() {
        observerList.forEach {
            it.change()
        }
    }
}