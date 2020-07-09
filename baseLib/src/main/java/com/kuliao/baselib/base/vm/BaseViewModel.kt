package com.kuliao.baselib.base.vm

import androidx.lifecycle.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

/**
 * Author: WangHao
 * Created On: 2020/06/17  15:21
 * Description:
 */

//类型别名
typealias LaunchBlock = suspend CoroutineScope.() -> Unit

typealias EmitBlock<T> = suspend LiveDataScope<T>.() -> T

typealias Cancel = (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {

    //通用事件模型驱动(如：显示对话框、取消对话框、错误提示)
    val mStateLiveData = MutableLiveData<StateActionEvent>()

    fun launch(cancel: Cancel, block: LaunchBlock) {//使用协程封装统一的网络请求处理
        viewModelScope.launch {
            //ViewModel自带的viewModelScope.launch,会在页面销毁的时候自动取消请求,有效封装内存泄露
            try {
                mStateLiveData.value = LoadState
                block
                mStateLiveData.value = SuccessState
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> cancel.invoke(e)
                    else -> mStateLiveData.value = ErrorState(e.message)
                }
            }
        }
    }

    fun <T> emit(cancel: Cancel?=null, block: EmitBlock<T>): LiveData<T> = liveData {
        try {
            mStateLiveData.value = LoadState
            emit(block())
            mStateLiveData.value = SuccessState
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> cancel?.invoke(e)
                else -> mStateLiveData.value = ErrorState(e.message)
            }
        }
    }
}