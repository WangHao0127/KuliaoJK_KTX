package com.kuliao.baselib.base.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author: WangHao
 * Created On: 2020/06/17  15:21
 * Description:
 */
class BaseViewModel : ViewModel() {

    //通用事件模型驱动(如：显示对话框、取消对话框、错误提示)
    val mStateLiveData = MutableLiveData<StateActionEvent>()

    fun launch(){

    }
}