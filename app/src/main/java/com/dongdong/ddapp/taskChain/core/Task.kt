package com.dongdong.ddapp.taskChain.core

import android.util.Log
import rx.Single

private const val TAG = "Task"

abstract class Task<In, Out> : (In) -> Single<Out> {
    override fun invoke(value: In): Single<Out> {
        Log.d(TAG, "invoke task ${this.javaClass.simpleName}")
        return onInvoke(value)
    }

    abstract fun onInvoke(value: In): Single<Out>
}

/**
 * pipe-ize two tasks, pass the output of the first to the second
 *
 * usage:
 *
 * task1 pipeThen task2
 */
infix fun <In, Out, Next> Task<In, Out>.pipeThen(next: Task<Out, Next>): Task<In, Next> {
    return object : Task<In, Next>() {
        override fun onInvoke(value: In): Single<Next> {
            Log.d(TAG, "[pipeThen] task: $this, $value -> $next")
            return this@pipeThen(value).flatMap { next(it) }
        }
    }
}

/**
 * direct append the second task, without piping values
 *
 * usage:
 *
 * task1 directThen task2
 */
infix fun <In, Out, Next> Task<In, Out>.then(next: Task<Unit, Next>): Task<In, Next> {
    return object : Task<In, Next>() {
        override fun onInvoke(value: In): Single<Next> {
            Log.d(TAG, "[pipeThen] task: $this, $value -> $next")
            return this@then(value).flatMap { next(Unit) }
        }
    }
}

/**
 * lazy append
 */
infix fun <In, Out, Next> Task<In, Out>.lazyThen(next: () -> Task<Unit, Next>): Task<In, Next> {
    return object : Task<In, Next>() {
        override fun onInvoke(value: In): Single<Next> {
            Log.d(TAG, "[lazyThen] task: $this, $value -> $next")
            return this@lazyThen(value).flatMap { next()(Unit) }
        }
    }
}

fun <In, Out> Task<In, Out>.combineWith(
    command: () -> Unit
): Task<In, Unit> {
    return object : Task<In, Unit>() {
        override fun onInvoke(value: In): Single<Unit> {
            return this@combineWith(value).map {
                command()
            }
        }
    }
}

fun <In, Out> Task<In, Out>.mergeWith(task: Task<Unit, *>): Task<In, Unit> {
    return object : Task<In, Unit>() {
        override fun onInvoke(value: In): Single<Unit> {
            return this@mergeWith(value).toCompletable().mergeWith(task.invoke(Unit).toCompletable()).toSingle { Unit }
        }
    }
}

/**
 * using predicate to direct task to two streams
 *
 * usage:
 *
 * task1 judge(predicate, trueBranch, falseBranch) then task2
 */
fun <In, Out, Next> Task<In, Out>.judge(
    predicate: (Out) -> Boolean,
    trueTask: Task<Unit, Next>,
    falseTask: Task<Unit, Next>
): Task<In, Next> {
    return object : Task<In, Next>() {
        override fun onInvoke(value: In): Single<Next> {
            Log.d(TAG, "[judge] task: $this, $value -> $trueTask, $falseTask")
            return this@judge(value).flatMap {
                if (predicate(it)) trueTask(Unit) else falseTask(Unit)
            }
        }
    }
}

class FuncTask<IN, OUT>(val func: (IN) -> Single<OUT>) : Task<IN, OUT>() {
    override fun onInvoke(value: IN): Single<OUT> {
        return func(value)
    }
}