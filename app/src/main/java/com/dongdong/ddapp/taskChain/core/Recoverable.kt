package com.dongdong.ddapp.taskChain.core

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import rx.Single
import rx.Subscription

private const val TAG = "Recoverable"
typealias Closure = () -> Unit

interface Recoverable {
    /**
     * 页面是否处于 resume 状态
     */
    var isActive: Boolean

    @UiThread
    fun registerRecoverable(lifecycleOwner: LifecycleOwner, which: String)

    /**
     * 当 resume 状态时，执行闭包
     * 否则将闭包记为 pending 状态并缓存下来，待下次 resume 时恢复执行
     */
    @UiThread
    fun executeWhenActive(closure: Closure)

    /**
     * 当 resume 状态时，执行闭包
     * 否则将闭包记为 pending 状态并缓存下来，待下次 resume 时恢复执行
     * Note: 该闭包带有 token，所以同一个 token 只缓存最新提交的闭包
     */
    @UiThread
    fun executeWhenActiveWithToken(token: String, closure: Closure)

    /**
     * 通过 token 删除缓存中的闭包
     */
    @UiThread
    fun removeExecutable(token: String)

    /**
     * 清除所有缓存的闭包
     */
    @UiThread
    fun clearAllPendingClosures()

    /**
     * 执行可暂停、恢复、取消的 task
     */
    @UiThread
    fun <In, Out> executeRecoverableTask(
        owner: LifecycleOwner,
        `in`: In,
        task: Task<In, Out>
    ): TaskChainCancelable

    /**
     * 执行可暂停、恢复、取消的 task
     */
//    fun <In, Out> executeRecoverableTask(owner: LifecycleOwner, `in`: In, task: OLTask<In, Out>): TaskChainCancelable

    interface TaskChainCancelable {
        fun cancel()
    }

    class Default : Recoverable {

        private var which: String = TAG
        override var isActive = false
        private var pendingClosures: MutableList<Closure>? = null
        private var pendingTokenClosures: MutableList<Pair<String, Closure>>? = null

        override fun registerRecoverable(lifecycleOwner: LifecycleOwner, which: String) {
            this.which = which
            lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                fun resume() {
                    isActive = true
                    pendingClosures?.run {
                        forEach {
                            it.invoke()
                            Log.d(TAG, "$which executePendingClosure $it")
                        }
                        clear()
                    }
                    pendingTokenClosures?.run {
                        forEach {
                            it.second.invoke()
                            Log.d(TAG, "$which executePendingTokenClosure $it")
                        }
                        clear()
                    }
                    Log.d(TAG, "$which resume")
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                fun pause() {
                    isActive = false
                    Log.d(TAG, "$which pause")
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun release() {
                    clearAllPendingClosures()
                }
            })
        }

        override fun executeWhenActive(closure: Closure) {
            if (isActive) {
                closure()
                Log.d(TAG, "$which execute $closure.")
            } else {
                if (pendingClosures == null) {
                    pendingClosures = mutableListOf()
                }
                pendingClosures?.add(closure)
                Log.d(TAG, "$which pending $closure")
            }
        }

        override fun executeWhenActiveWithToken(token: String, closure: Closure) {
            if (isActive) {
                closure()
                Log.d(TAG, "$which execute $token -> $closure.")
            } else {
                if (pendingTokenClosures == null) {
                    pendingTokenClosures = mutableListOf()
                }
                pendingTokenClosures?.removeAll { it.first == token }?.let {
                    Log.d(TAG, "$which pending $pendingTokenClosures removed")
                }
                pendingTokenClosures?.add(token to closure)
                Log.d(TAG, "$which pending $pendingTokenClosures by $token")
            }
        }

        override fun removeExecutable(token: String) {
            pendingTokenClosures?.removeAll { it.first == token }
        }

        override fun clearAllPendingClosures() {
            pendingClosures?.clear()
            pendingClosures = null

            pendingTokenClosures?.clear()
            pendingTokenClosures = null
        }

        override fun <In, Out> executeRecoverableTask(
            owner: LifecycleOwner,
            `in`: In,
            task: Task<In, Out>
        ): TaskChainCancelable {
            return task.startRecoverableChain(owner, `in`)
        }

//        override fun <In, Out> executeRecoverableTask(
//            owner: LifecycleOwner,
//            `in`: In,
//            task: OLTask<In, Out>
//        ): TaskChainCancelable {
//            return task.startRecoverableChain(owner, `in`)
//        }
    }
}

/**
 * 执行 dwTask 并使其可以跟随 lifecycle [中断、恢复、取消]
 */
private fun <In, Out> Task<In, Out>.startRecoverableChain(
    owner: LifecycleOwner,
    `in`: In
): Recoverable.TaskChainCancelable {

    val lifecycle = owner.lifecycle
    var sub: Subscription? = null
    var cache: Single<Out>? = null

    val obs = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            Log.d(TAG, "chain reSubscribe")
            if (sub?.isUnsubscribed == true) {
                sub = cache?.subscribe()
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            Log.d(TAG, "chain unSubscribe")
            sub?.unsubscribe()
            sub = null
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            Log.d(TAG, "chain destroy")
            sub?.unsubscribe()
            sub = null
            cache = null
        }
    }

    lifecycle.addObserver(obs)

    cache = this.invoke(`in`).doAfterTerminate {
        Log.d(TAG, "chain terminate")
        lifecycle.removeObserver(obs)
        sub = null
        cache = null
    }.cache()

    sub = cache?.subscribe()

    return object : Recoverable.TaskChainCancelable {
        override fun cancel() {
            lifecycle.removeObserver(obs)
            sub?.unsubscribe()
            cache = null
        }
    }
}

//
///**
// * 执行 dwTask 并使其可以跟随 lifecycle [中断、恢复、取消]
// */
//private fun <In, Out> OLTask<In, Out>.startRecoverableChain(
//    owner: LifecycleOwner,
//    `in`: In
//): Recoverable.TaskChainCancelable {
//
//    val lifecycle = owner.lifecycle
//    var sub: io.reactivex.disposables.Disposable? = null
//    var cache: io.reactivex.Single<Out>? = null
//
//    val obs = object : LifecycleObserver {
//        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//        fun onResume() {
//            CenterLog.d(TAG, "chain reSubscribe")
//            if (sub?.isDisposed == true) {
//                sub = cache?.subscribeWithLog()
//            }
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//        fun onPause() {
//            CenterLog.d(TAG, "chain unSubscribe")
//            sub?.dispose()
//            sub = null
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//        fun onDestroy() {
//            CenterLog.d(TAG, "chain destroy")
//            sub?.dispose()
//            sub = null
//            cache = null
//        }
//    }
//
//    lifecycle.addObserver(obs)
//
//    cache = this.invoke(`in`).doAfterTerminate {
//        CenterLog.d(TAG, "chain terminate")
//        lifecycle.removeObserver(obs)
//        sub = null
//        cache = null
//    }.cache()
//
//    sub = cache?.subscribeWithLog()
//
//    return object : Recoverable.TaskChainCancelable {
//        override fun cancel() {
//            lifecycle.removeObserver(obs)
//            sub?.dispose()
//            cache = null
//        }
//    }
//}
