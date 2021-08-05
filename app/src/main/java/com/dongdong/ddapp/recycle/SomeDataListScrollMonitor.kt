package com.dongdong.ddapp.recycle

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dongdong.ddapp.utils.DisplayUtil
import com.dongdong.ddapp.utils.toDp
import java.lang.Exception
import kotlin.math.abs

/**
 * 对本次滚动后"从未展示"-->"展示"的item进行打点
 */

@SuppressLint("LongLogTag")
class SomeDataListScrollMonitor : RecyclerView.OnScrollListener(), LifecycleObserver{
    private var recyclerView: RecyclerView? = null
    private var lastScrollTime: Long = 0
    private var vs2Log: SomeDataListUmsCallback? = null
    private var someDataList: ArrayList<SomeData>? = null

    private val showedModuleIndexList = arrayListOf<Int>()

    companion object {
        const val SPEED_CAP = 2.0f
        private const val TAG = "SomeDataListScrollMonitor"
    }

    fun bind(recyclerView: RecyclerView, someDataList: ArrayList<SomeData>, lifecycle: Lifecycle, onVS2Log: SomeDataListUmsCallback) {
        this.vs2Log = onVS2Log
        this.recyclerView = recyclerView
        this.someDataList = someDataList
        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(this)
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        showedModuleIndexList.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        compute()
    }

    fun compute() {
        recyclerView?.let { rv -> computeCntForLog(rv) }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        vs2Log?.applyNavigation(dy)
        val time = System.currentTimeMillis() - lastScrollTime
        //横向使用dx，纵向使用dy
        val speed: Float = abs(dy.toDp() / (if (time > 0) time else 1))
        if (abs(speed) < SPEED_CAP && abs(speed) > 0f) {
            runCatching {
                compute()
            }.onFailure {
                Log.e(TAG, "computeCntForLog error")
            }
        }
        lastScrollTime = System.currentTimeMillis()
    }

    private fun computeCntForLog(recyclerView: RecyclerView) {
        val parentLayoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        try {
            val thisTimeShowedIndexList = arrayListOf<Int>()
            for (i in 0 until recyclerView.childCount) {
                val childView = recyclerView.getChildAt(i)
                val position = parentLayoutManager.getPosition(childView)
                if (isBoxShown(childView)) {
                    thisTimeShowedIndexList.add(position)
                }
            }

            // 只对本次滚动后从未展示到展示的item进行打点
            thisTimeShowedIndexList.filter {
                !showedModuleIndexList.contains(it)
            }.forEach {
                someDataList?.getOrNull(it)?.let { model ->
                    vs2Log?.showItem(model.id, model.title)
                }
            }

            showedModuleIndexList.clear()
            showedModuleIndexList.addAll(thisTimeShowedIndexList)
        } catch (e: Exception) {
            Log.d(TAG, "error computeCntForLog: $e")
        }
    }

    private fun isBoxShown(view: View): Boolean {
        val result = IntArray(2)
        view.getLocationOnScreen(result)
        val startX = result[0]
        return startX < DisplayUtil.getScreenWidth() && startX >= 0 && view.isShown
    }
}

interface SomeDataListUmsCallback {
    fun showItem(id: Int, title: String)
    fun applyNavigation(dy: Int)
}
