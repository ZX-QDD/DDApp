package com.dongdong.ddapp.dagtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongdong.ddapp.R
import com.dongdong.ddapp.dagtask.core.DAGProject
import com.dongdong.ddapp.dagtask.core.DAGScheduler
import com.dongdong.ddapp.dagtask.demo.*
import kotlinx.android.synthetic.main.activity_dagtask.*


class DAGTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagtask)
        btn_run_task.setOnClickListener {
            runTask()
        }
    }

    private fun runTask() {
        val a = ATask("ATask")
        val b = BTask("BTask")
        val c = CTask("CTask")
        val d = DTask("DTask")
        val e = ETask("ETask")
        val dagProject = DAGProject.Builder()
            .addDAGTask(b)
            .addDAGTask(c)
            .addDAGTask(a)
            .addDAGTask(d)
            .addDAGTask(e)
            .addDAGEdge(b, a)
            .addDAGEdge(c, b)
            .addDAGEdge(d, c)
            .addDAGEdge(e, d)
            .builder()
        val dagScheduler = DAGScheduler()
        dagScheduler.start(dagProject)
    }
}