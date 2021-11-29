package com.dongdong.ddapp.taskChain.core

interface TaskChain<In, Out> {
    fun getTask(): Task<In, Out>
}