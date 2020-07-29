package com.kuliao.kuliaojk.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.kuliaojk.config.Settings
import com.kuliao.kuliaojk.data.User
import com.kuliao.kuliaojk.reposotory.UserRepository

/**
 * Author: WangHao
 * Created On: 2020/06/22  14:38
 * Description:
 */
class UserViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {

    val mUserInfoModel = MutableLiveData<User>()

    val isLogged = MutableLiveData<Boolean>() //false:未登录状态，true：已登录状态

    /**
     * 登录获取用户信息
     */
    fun getUser(etName: String, pwd: String): LiveData<User> = emit {
        mUserRepository.getUserInfo(etName, pwd)
    }

    /**
     * 将登录后的用户信息保存在本地数据库
     */
    fun saveLocalUser(user: User) {
        //保存在Preference中的信息，便于信息及时提取和应用
        Settings.Account.nickname = user.nickName
        Settings.Account.head_photo = user.headPhoto
        //用户信息保存在本地数据库
        launch {
            mUserRepository.deleteUser()
            mUserRepository.saveLocalUser(user)
        }
    }

    /**
     * 获取本地数据库存储的用户信息
     */
    fun getLocalUserInfo() {
        launch {
            mUserInfoModel.value = mUserRepository.getLocalUserInfo()
        }
    }

    /**
     * 设置用户登录状态
     */
    fun isLogged() {
        isLogged.value = !Settings.Account.nickname.isNullOrBlank()
    }
}