package com.dongdong.ddapp.rxjava.core

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.concurrent.Executors

class Schedulers {
    //IO线程池
    private var IOThreadPool = Executors.newCachedThreadPool()

    //主线程
    private var handler = Handler(Looper.getMainLooper()) { message ->
        message.callback.run()
        return@Handler true
    }

    companion object {
        //定义一个线程安全的单例模式
        val INSTANCE: Schedulers by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Schedulers()
        }
        private val IO = 0 //定义IO线程策略
        private val MAIN = 1 //定义main线程策略

        fun IO():Int{
            return IO
        }

        fun mainThread():Int{
            return MAIN
        }
    }

    fun <T> submitSubscribeWork(
        source: ObservableOnSubscribe<T>, //上游
        observer: Observer<T>,//下游
        thread: Int//指定的线程
    ) {
        when (thread) {
            IO -> {
                IOThreadPool.submit {
                    //从线程池抽取一个线程执行上游和下游的连接操作
                    source.subscribe(observer)
                }
            }
            MAIN -> {
                handler.let {
                    val message=Message.obtain(it){
                        source.subscribe(observer)
                    }
                    it.sendMessage(message)
                }
            }
        }
    }

    fun  submitObserverWork(function: () -> Unit,thread:Int) {
        when(thread){
            IO->{
                IOThreadPool?.submit {
                    function.invoke()
                }
            }
            MAIN->{
                handler.let {
                    val m=Message.obtain(it){
                        function.invoke()
                    }
                    it.sendMessage(m)
                }
            }
        }
    }
}