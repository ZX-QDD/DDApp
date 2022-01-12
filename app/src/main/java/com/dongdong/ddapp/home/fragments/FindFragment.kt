package com.dongdong.ddapp.home.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.dongdong.ddapp.MainActivity
import com.dongdong.ddapp.R
import com.dongdong.ddapp.base.BaseFragment
import com.dongdong.ddapp.dialog.dialogs.BindPhoneDialog
import com.dongdong.ddapp.home.NormalDialogActivity
import kotlinx.android.synthetic.main.fragment_find.*

class FindFragment : BaseFragment(R.layout.fragment_find) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_goto_main.setOnClickListener {
//            AlertDialog.Builder(requireContext())
//                .setMessage("测试")
//                .setCancelable(false)
//                .setOnCancelListener { MainActivity.launch(requireContext()) }
//                .show()
//            BindPhoneDialog(requireContext()).show()
            NormalDialogActivity.launch(requireContext())
        }
    }

}