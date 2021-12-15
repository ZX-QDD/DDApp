package com.dongdong.ddapp.fragment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dongdong.ddapp.MainActivity
import com.dongdong.ddapp.R
import com.dongdong.ddapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_find.*

class FindFragment : BaseFragment(R.layout.fragment_find) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_goto_main.setOnClickListener {
            MainActivity.launch(requireContext())
        }
    }

}