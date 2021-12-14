package com.dongdong.ddapp

object Constants {
    const val SELECT_TAB_HOME = "select_tab_home"
    const val SELECT_TAB_ME = "select_tab_me"
    const val SELECT_TAB_ADDRESS = "select_tab_address"
    const val SELECT_TAB_FIND = "select_tab_find"
}


enum class SelectTabType(val value: Int) {
    UNKNOWN(-1),
    SELECT_TAB_HOME(0),
    SELECT_TAB_ADDRESS_BOOK(1),
    SELECT_TAB_FIND(2),
    SELECT_TAB_ME(3)
}