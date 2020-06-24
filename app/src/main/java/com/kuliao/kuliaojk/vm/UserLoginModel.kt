package com.kuliao.kuliaojk.vm

import androidx.databinding.ObservableField

/**
 * Author: WangHao
 * Created On: 2020/06/24  17:08
 * Description:
 */
data class UserLoginModel (
    val userName: ObservableField<String> =ObservableField(),
    val password: ObservableField<String> =ObservableField()

)