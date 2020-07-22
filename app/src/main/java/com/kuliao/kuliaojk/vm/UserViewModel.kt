package com.kuliao.kuliaojk.vm

import androidx.lifecycle.LiveData
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.kuliaojk.data.User
import com.kuliao.kuliaojk.reposotory.UserRepository

/**
 * Author: WangHao
 * Created On: 2020/06/22  14:38
 * Description:
 */
class UserViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    fun getUser(etName: String, pwd: String): LiveData<User> = emit {
        mUserRepository.getUserInfo(etName, pwd)
    }

    fun saveLocalUser(user: User) {
        launch {
            mUserRepository.saveLocalUser(user)
        }
    }
}