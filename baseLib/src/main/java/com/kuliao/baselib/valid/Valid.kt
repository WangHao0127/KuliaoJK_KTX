package com.kuliao.baselib.valid

/**
 * Author: WangHao
 * Created On: 2020/06/24  10:12
 * Description:
 */
interface Valid {
    /**
     * 是否满足检验器的要求，如果不满足的话，则执行doAction方法。如果满足，则执行目标action
     * @return
     */
    fun check(): Boolean

    fun doValid()
}