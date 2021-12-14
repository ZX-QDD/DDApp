package com.dongdong.ddapp.taskchain

import android.util.Log
import com.dongdong.ddapp.taskchain.core.Task
import com.dongdong.ddapp.taskchain.core.TaskChain
import com.dongdong.ddapp.taskchain.core.pipeThen
import com.dongdong.ddapp.taskchain.core.then
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

private const val TAG = "ChainDemo"

class Chain(val view: ChainView) : TaskChain<Unit, Unit> {
    override fun getTask(): Task<Unit, Unit> {

        val trigger = TriggerTask(Unit)

        val task1 = object : Task<Unit, String>() {
            override fun onInvoke(value: Unit): Single<String> {
                Log.d(TAG, "task1 invoked")
                return Single.create<String> {
                    Log.d(TAG, "task1 emit")
                    (object : Thread() {
                        override fun run() {
                            sleep(2000)
                            it.onSuccess("hello task1")
                        }
                    }).start()
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        val task2 = object : Task<Unit, String>() {
            override fun onInvoke(p1: Unit): Single<String> {
                Log.d(TAG, "task2 invoked")
                return Single.create<String> {
                    Log.d(TAG, "task2 emit")
                    view.showTaskDialog("Task 22222") {
                        it.onSuccess("hello task2")
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        val task3 = object : Task<Unit, String>() {
            override fun onInvoke(value: Unit): Single<String> {
                Log.d(TAG, "task3 invoked")
                return Single.create<String> {
                    Log.d(TAG, "task3 emit")
                    (object : Thread() {
                        override fun run() {
                            sleep(2000)
                            it.onSuccess("hello task3")
                        }
                    }).start()
                }.subscribeOn(Schedulers.io())
            }
        }

        val task4 = object : Task<Unit, String>() {
            override fun onInvoke(p1: Unit): Single<String> {
                Log.d(TAG, "task4 invoked")
                return Single.create<String> {
                    Log.d(TAG, "task4 emit")
                    view.showTaskDialog("Task 44444") {
                        it.onSuccess("hello task4")
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        val task5 = object : Task<String, String>() {
            override fun onInvoke(p1: String): Single<String> {
                Log.d(TAG, "task5 invoked")
                return Single.create<String> {
                    Log.d(TAG, "task5 emit")
                    view.showTaskDialog("Task 55555") {
                        it.onSuccess("hello task5")
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
        }

        return trigger pipeThen
                task1 then
                task2 then
                task3 then
                task4 pipeThen
                task5 then
                EmptyTask
    }
}

interface ChainView {
    fun showTaskDialog(msg: String, cb: () -> Unit)
}

class TriggerTask<T>(
    private val msg: T
) : Task<Unit, T>() {
    override fun onInvoke(p1: Unit): Single<T> {
        Log.d(TAG, "trigger invoked")
        return Single.just(msg)
    }
}

object EmptyTask : Task<Unit, Unit>() {
    override fun onInvoke(p1: Unit): Single<Unit> {
        Log.d(TAG, "empty invoked")
        return Single.just(Unit)
    }
}
