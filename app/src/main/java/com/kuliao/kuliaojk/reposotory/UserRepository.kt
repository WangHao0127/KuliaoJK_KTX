package com.kuliao.kuliaojk.reposotory

import com.kuliao.kuliaojk.api.UserApi
import com.kuliao.kuliaojk.dao.UserDao

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:02
 * Description:
 */
class UserRepository(private val mUserApi: UserApi, val mUserDao: UserDao) {


    suspend fun getUserInfo(etName:String,pwd:String)=mUserApi.login()


}