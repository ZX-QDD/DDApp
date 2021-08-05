package com.dongdong.ddapp.recycle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dongdong.ddapp.R
import com.dongdong.ddapp.utils.dpToPx
import kotlinx.android.synthetic.main.recycle_activity_some_data_list.*

class SomeDataListActivity : AppCompatActivity() {

    companion object {
        val TAG = "RecyclerViewActivity"
        private val SMALL_SPACE = 300.dpToPx
        fun launch(context: Context) {
            context.startActivity(Intent(context, SomeDataListActivity::class.java))
        }
    }

    private var someDataListScrollMonitor = SomeDataListScrollMonitor()
    private var someDataList = arrayListOf<SomeData>()
    private val someDataAdapter = SomeDataListAdapter()
    private var scrollY = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_activity_some_data_list)
        recyclerView.adapter = someDataAdapter
        initData()
    }

    private fun initData() {
        for (i in 0..30){
            someDataList.add(SomeData(i, i.toString()))
        }
        someDataAdapter.setNewData(someDataList)
        initSomeDataScrollUms(someDataList)
    }

    private fun initSomeDataScrollUms(someDataList: ArrayList<SomeData>) {
        recyclerView.post {
            someDataListScrollMonitor.compute()
        }
        someDataListScrollMonitor.bind(recyclerView, someDataList, lifecycle, object : SomeDataListUmsCallback {

            override fun showItem(id: Int, title: String) {
                //打点
                Log.e("====", "$id+++$title")
            }

            //到达一定高度改变导航栏颜色
            override fun applyNavigation(dy: Int) {
                    scrollY += dy
                    if (scrollY > SMALL_SPACE) {
//                        applyWhiteNavigation()
                    } else {
//                        applyDarkNavigation()
                    }
                //如果需要背景跟随滑动则设置
//                ivHeader.scrollTo(0, scrollY)
            }
        })
    }
}

