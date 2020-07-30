package com.kuliao.kuliaojk.reposotory

import com.kuliao.kuliaojk.api.UserApi
import com.kuliao.kuliaojk.dao.UserDao
import com.kuliao.kuliaojk.data.User
import com.kuliao.kuliaojk.http.BaseResponse

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:02
 * Description:
 */
class UserRepository(private val mUserApi: UserApi, private val mUserDao: UserDao) {

    suspend fun getUserInfo(etName: String, pwd: String): User = mUserApi.login(etName, pwd)

    suspend fun saveLocalUser(user: User) = mUserDao.insert(user)

    suspend fun getLocalUserInfo() = mUserDao.getUserInfo()

    suspend fun deleteUser() = mUserDao.delete()

    suspend fun logout(params: MutableMap<String, Any>):BaseResponse = mUserApi.logout(params)
}