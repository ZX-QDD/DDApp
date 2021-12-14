package com.dongdong.ddapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.dongdong.ddapp.Constants
import com.dongdong.ddapp.R
import com.dongdong.ddapp.base.BaseActivity
import com.dongdong.ddapp.fragment.fragments.AddressBookFragment
import com.dongdong.ddapp.fragment.fragments.FindFragment
import com.dongdong.ddapp.fragment.fragments.HomeFragment
import com.dongdong.ddapp.fragment.fragments.MeFragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private var lastIndex: Int = -1
    private var mFragmentsCreator = mutableListOf<Pair<String, () -> Fragment>>(
        "home" to { HomeFragment() },
        "address" to { AddressBookFragment() },
        "find" to { FindFragment() },
        "me" to { MeFragment() }
    )

    companion object {
        const val TAG = "HomeActivity"
        private const val HOME_SELECT_TAB = "HOME_SELECT_TAB"
        private const val LAST_INDEX = "last_index"
        fun launch(context: Context, selectTab: String = Constants.SELECT_TAB_HOME) {
            context.startActivity(Intent(context, HomeActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.putExtra(HOME_SELECT_TAB, selectTab)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    private fun initView(savedInstanceState: Bundle?) {
        initBottomNavigation()
        lastIndex = savedInstanceState?.getInt(LAST_INDEX) ?: -1
        if (lastIndex == -1) {
            initHomeTab()
        } else {
            resetHomeTab(lastIndex)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_INDEX, lastIndex)
    }

    private fun initHomeTab() {
        Log.d(TAG, "initHomeTab tab = ${intent.getStringExtra(HOME_SELECT_TAB)}")
        when (intent.getStringExtra(HOME_SELECT_TAB)) {
            Constants.SELECT_TAB_HOME -> {
                bottomNavigation.selectedItemId = R.id.menu_home
            }
            Constants.SELECT_TAB_ADDRESS -> {
                bottomNavigation.selectedItemId = R.id.menu_address_book
            }
            Constants.SELECT_TAB_FIND -> {
                bottomNavigation.selectedItemId = R.id.menu_find
            }
            Constants.SELECT_TAB_ME -> {
                bottomNavigation.selectedItemId = R.id.menu_me
            }
        }
    }

    private fun resetHomeTab(index: Int) {
        Log.d(TAG, "resetHomeTab tab = $index")
        when (index) {
            0 -> { bottomNavigation.selectedItemId = R.id.menu_home }
            1 -> { bottomNavigation.selectedItemId = R.id.menu_address_book }
            2 -> { bottomNavigation.selectedItemId = R.id.menu_find }
            3 -> { bottomNavigation.selectedItemId = R.id.menu_me }
        }
    }

    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> setFragmentPosition(0)
                R.id.menu_address_book -> setFragmentPosition(1)
                R.id.menu_find -> setFragmentPosition(2)
                R.id.menu_me -> setFragmentPosition(3)
                else -> {
                }
            }
            // 这里注意返回true,否则点击失效
            true
        }
        bottomNavigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigation.itemIconTintList = null
    }

    private fun setFragmentPosition(position: Int) {
        if (lastIndex == position)
            return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val (tag, fragmentBuilder) = mFragmentsCreator[position]
        var currentFragment = supportFragmentManager.findFragmentByTag(tag)
        Log.d("setFragmentPosition", "set $tag")
        if (currentFragment == null) {
            Log.d("setFragmentPosition", "create $tag")
            currentFragment = fragmentBuilder.invoke()
            fragmentTransaction.add(R.id.ll_frameLayout, currentFragment, tag)
        }

        if (lastIndex != -1) {
            supportFragmentManager.findFragmentByTag(mFragmentsCreator[lastIndex].first)?.let {
                Log.d("setFragmentPosition", "hide ${mFragmentsCreator[lastIndex].first}")
                fragmentTransaction.hide(it)
            }
        }
        fragmentTransaction.show(currentFragment)
        fragmentTransaction.commitAllowingStateLoss()
        lastIndex = position
        Log.d("setFragmentPosition", "lastIndex: $lastIndex")
    }
}