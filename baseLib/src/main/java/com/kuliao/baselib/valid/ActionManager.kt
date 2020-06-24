package com.kuliao.baselib.valid

import java.util.*

/**
 * Author: WangHao
 * Created On: 2020/06/24  10:31
 * Description:
 */
class ActionManager private constructor() {

    private val delaysActions: Stack<CallUnit> = Stack()

    companion object {
        fun get(): ActionManager {
            return Inner.single
        }
    }

    private object Inner {
        val single = ActionManager()
    }

    /**
     * 根据条件判断，是否要执行一个action
     */
    fun postCallUnit(callUnit: CallUnit) {
        //清除所有的actions
        delaysActions.clear()
        //执行check
        callUnit.check()
        //如果全部满足，则直接跳转目标方法

        callUnit.let {
            if (it.validQueue.size == 0) {
                it.mAction?.call()
            } else {
                //加入到延迟执行体中来
                delaysActions.push(callUnit)

                val valid = callUnit.validQueue.peek()
                it.lastValid = valid
                //是否会有后置任务
                valid.doValid()
            }
        }
    }


    /**
     * 通过反射注解来组装(但是这个前提是无参的构造方法才行)
     */
    fun postCallUnit(action: Action) {
        val clz = action.javaClass

        try {
            val method = clz.getMethod("call")
            val interceptor = method.getAnnotation(Interceptor::class.java)
            val clzArray = interceptor.value
            val callUnit = CallUnit(action)
            for (cla in clzArray) {
                callUnit.addValid(cla.objectInstance as Valid)
            }
            postCallUnit(callUnit)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * 重新检查
     */
    fun checkValid() {
        if (delaysActions.size > 0) {
            delaysActions.peek()?.let { its ->
                if (its.lastValid?.check() == false) {
                    throw ValidException(
                        String.format(
                            "you must pass through the %s,and then reCall()",
                            its.lastValid!!::class.java.toString()
                        )
                    )
                }
                its.validQueue.let { ait ->
                    ait.remove(its.lastValid)
                    //valid已经执行完了，则表示此delay已经检验完了--执行目标方法
                    if (ait.size == 0) {
                        its.mAction?.call()
                        //把这个任务移出
                        delaysActions.remove(its)
                    } else {
                        its.validQueue.peek().let {
                            its.lastValid = it
                            it.doValid()
                        }
                    }
                }
            }
        }
    }

}