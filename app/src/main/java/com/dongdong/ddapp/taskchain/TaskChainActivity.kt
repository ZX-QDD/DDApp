package com.dongdong.ddapp.taskchain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.dongdong.ddapp.R
import com.dongdong.ddapp.taskchain.core.Recoverable
import kotlinx.android.synthetic.main.activity_task_chain.*

class TaskChainActivity : AppCompatActivity(), Recoverable by Recoverable.Default(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_chain)
        registerRecoverable(this, this.javaClass.simpleName)
        val chain = Chain(object : ChainView {
            override fun showTaskDialog(msg: String, cb: () -> Unit) {
                executeWhenActive {
                    AlertDialog.Builder(this@TaskChainActivity)
                        .setMessage(msg)
                        .setOnCancelListener { cb() }
                        .show()
                }
            }
        })
        executeRecoverableTask(this, Unit, chain.getTask())
    }
}