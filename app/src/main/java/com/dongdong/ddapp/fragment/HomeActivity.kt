package com.dongdong.ddapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.dongdong.ddapp.R
import com.dongdong.ddapp.SelectTabType
import com.dongdong.ddapp.base.BaseActivity
import com.dongdong.ddapp.fragment.fragments.AddressBookFragment
import com.dongdong.ddapp.fragment.fragments.FindFragment
import com.dongdong.ddapp.fragment.fragments.HomeFragment
import com.dongdong.ddapp.fragment.fragments.MeFragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private var lastIndex: SelectTabType = SelectTabType.UNKNOWN
    private var mFragmentsCreator = mutableListOf<Pair<String, () -> Fragment>>(
        "home" to { HomeFragment() },
        "address" to { AddressBookFragment() },
        "find" to { FindFragment() },
        "me" to { MeFragment() }
    )

    companion object {
        const val TAG = "HomeActivity"
        private const val LAUNCH_SELECT_TAB = "launch_select_tab"
        private const val LAST_INDEX = "last_index"
        fun launch(context: Context, selectTabType: SelectTabType = SelectTabType.UNKNOWN) {
            context.startActivity(Intent(context, HomeActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.putExtra(LAUNCH_SELECT_TAB, selectTabType)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        initHomeTab(intent)
    }

    private fun initView(savedInstanceState: Bundle?) {
        initBottomNavigation()
        val saveLastIndex = savedInstanceState?.getSerializable(LAST_INDEX) as SelectTabType?
        lastIndex = saveLastIndex ?: SelectTabType.UNKNOWN
        if (lastIndex == SelectTabType.UNKNOWN) {
            initHomeTab(intent)
        } else {
            resetHomeTab(lastIndex)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(LAST_INDEX, lastIndex)
    }

    private fun initHomeTab(intent: Intent) {
        val launchSelectTab = intent.getSerializableExtra(LAUNCH_SELECT_TAB) as SelectTabType?
        Log.d(TAG, "initHomeTab tab = $launchSelectTab")
        when (launchSelectTab) {
            SelectTabType.SELECT_TAB_ADDRESS_BOOK -> {
                bottomNavigation.selectedItemId = R.id.menu_address_book
            }
            SelectTabType.SELECT_TAB_FIND -> {
                bottomNavigation.selectedItemId = R.id.menu_find
            }
            SelectTabType.SELECT_TAB_ME -> {
                bottomNavigation.selectedItemId = R.id.menu_me
            }
            else -> {
                bottomNavigation.selectedItemId = R.id.menu_home
            }
        }
    }

    private fun resetHomeTab(selectTabType: SelectTabType) {
        Log.d(TAG, "resetHomeTab tab = $selectTabType")
        when (selectTabType) {
            SelectTabType.SELECT_TAB_ADDRESS_BOOK -> {
                bottomNavigation.selectedItemId = R.id.menu_address_book
            }
            SelectTabType.SELECT_TAB_FIND -> {
                bottomNavigation.selectedItemId = R.id.menu_find
            }
            SelectTabType.SELECT_TAB_ME -> {
                bottomNavigation.selectedItemId = R.id.menu_me
            }
            else -> {
                bottomNavigation.selectedItemId = R.id.menu_home
            }
        }
    }

    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> setFragmentPosition(SelectTabType.SELECT_TAB_HOME)
                R.id.menu_address_book -> setFragmentPosition(SelectTabType.SELECT_TAB_ADDRESS_BOOK)
                R.id.menu_find -> setFragmentPosition(SelectTabType.SELECT_TAB_FIND)
                R.id.menu_me -> setFragmentPosition(SelectTabType.SELECT_TAB_ME)
                else -> {
                }
            }
            // 这里注意返回true,否则点击失效
            true
        }
        bottomNavigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigation.itemIconTintList = null
    }

    private fun setFragmentPosition(position: SelectTabType) {
        if (lastIndex == position)
            return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val (tag, fragmentBuilder) = mFragmentsCreator[position.value]
        var currentFragment = supportFragmentManager.findFragmentByTag(tag)
        Log.d("setFragmentPosition", "set $tag")
        if (currentFragment == null) {
            Log.d("setFragmentPosition", "create $tag")
            currentFragment = fragmentBuilder.invoke()
            fragmentTransaction.add(R.id.ll_frameLayout, currentFragment, tag)
        }

        if (lastIndex != SelectTabType.UNKNOWN) {
            supportFragmentManager.findFragmentByTag(mFragmentsCreator[lastIndex.value].first)?.let {
                Log.d("setFragmentPosition", "hide ${mFragmentsCreator[lastIndex.value].first}")
                fragmentTransaction.hide(it)
            }
        }
        fragmentTransaction.show(currentFragment)
        fragmentTransaction.commitAllowingStateLoss()
        lastIndex = position
        Log.d("setFragmentPosition", "lastIndex: $lastIndex")
    }
}