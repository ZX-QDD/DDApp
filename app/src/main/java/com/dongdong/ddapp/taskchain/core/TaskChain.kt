package com.dongdong.ddapp.taskchain.core

interface TaskChain<In, Out> {
    fun getTask(): Task<In, Out>
}