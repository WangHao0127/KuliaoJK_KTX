package com.kuliao.baselib.base.vm

/**
 * Author: WangHao
 * Created On: 2020/06/17  16:13
 * Description:定义网络请求状态(密封类扩展性更好)
 */
sealed class StateActionEvent {
    //加载中的状态
    object LoadState : StateActionEvent()

    //成功的状态
    object SuccessState : StateActionEvent()

    //出错的状态
    class ErrorState(val message: String?) : StateActionEvent()
}