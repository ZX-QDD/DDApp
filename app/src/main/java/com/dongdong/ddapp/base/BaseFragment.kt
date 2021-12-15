package com.dongdong.ddapp.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment() : Fragment() {

    val TAG = "BaseFragment"

    @LayoutRes
    private var contentLayoutId: Int? = null

    constructor(@LayoutRes contentLayoutId: Int) : this() {
        this.contentLayoutId = contentLayoutId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.let { contentLayoutId = it.getInt("contentLayoutId", 0) }
        return contentLayoutId?.let {
            if (it > 0) inflater.inflate(it, container, false) else null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentLayoutId?.let { outState.putInt("contentLayoutId", it) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, " ${this.javaClass.simpleName} ==> onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "${this.javaClass.simpleName} ==> onDetach")
    }
}
