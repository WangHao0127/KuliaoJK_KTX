package com.kuliao.baselib.valid

import java.util.*

/**
 * Author: WangHao
 * Created On: 2020/06/23  16:31
 * Description:一个执行单元。包括一个执行目标体和一个检验队列。检验队列用来保证所有的前置条件。
 *              当所有的前置条件都通过后，才能进行执行单元。
 */
class CallUnit(action: Action?) {

    constructor() : this(null)

    //目标行为
    var mAction: Action? = action

    //先进先出验证模型
    val validQueue: Queue<Valid> = ArrayDeque()

    //上一个执行的valid
    var lastValid: Valid? = null

    //检查valid.如果已经满足要求，则移出来队列
    fun check() {
        for (valid in validQueue) {
            if (valid.check()) {
                validQueue.remove(valid)
            }
        }
    }

    /**
     * start
     */
    fun doCall() {
        ActionManager.get().postCallUnit(this)
    }

    fun addValid(valid: Valid): CallUnit {
        validQueue.add(valid)
        return this
    }
}