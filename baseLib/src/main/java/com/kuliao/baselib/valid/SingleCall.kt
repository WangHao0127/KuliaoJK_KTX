package com.kuliao.baselib.valid

/**
 * Author: WangHao
 * Created On: 2020/06/24  14:08
 * Description:
 */
class SingleCall {

    private val callUnit = CallUnit()

    companion object {
        fun get(): SingleCall {
            return Inner.single
        }
    }

    private object Inner {
        val single = SingleCall()
    }

    fun addAction(action: Action): SingleCall {
        clear()
        callUnit.mAction = action
        return this
    }

    fun addValid(valid: Valid): SingleCall {
        //只添加无效的，验证不通过的。
        if (valid.check()) {
            return this
        }
        callUnit.addValid(valid)
        return this
    }

    fun doCall() {
        //如果上一条valid难没有通过，是不允许再发起call的
        if (callUnit.lastValid != null && callUnit.lastValid!!.check()) {
            return
        }

        //执行action
        if (callUnit.validQueue.size === 0 && callUnit.mAction != null) {
            callUnit.mAction!!.call()
            //清空
            clear()
        } else {
            //执行验证。
            val valid = callUnit.validQueue.poll()
            callUnit.lastValid = valid
            valid.doValid()
        }

    }

    private fun clear() {
        callUnit?.let {
            it.validQueue.clear()
            it.mAction = null
            it.lastValid = null
        }
    }

}