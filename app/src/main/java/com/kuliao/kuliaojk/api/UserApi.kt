package com.kuliao.kuliaojk.api

import com.kuliao.kuliaojk.data.User
import com.kuliao.kuliaojk.net.APIPath
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:02
 * Description:
 */
interface UserApi{

     @POST(APIPath.Basic.LOGIN)
     @FormUrlEncoded
     suspend fun login():User
}