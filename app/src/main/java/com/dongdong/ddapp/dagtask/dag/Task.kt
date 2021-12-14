package com.dongdong.ddapp.dagtask.dag

class Task(
    val id: Long,
    val name: String,
    var state: State = State.UNEXECUTE
) : Executor {

    override fun execute(): Boolean {
        println("Task id : [$id] Task name : [$name] is running")
        state = State.EXECUTED
        return true
    }

    fun hasExecuted(): Boolean {
        return state == State.EXECUTED
    }
}

enum class State {
    UNEXECUTE,
    EXECUTED
}