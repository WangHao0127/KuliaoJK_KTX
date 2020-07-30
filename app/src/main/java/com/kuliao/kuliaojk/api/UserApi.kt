package com.kuliao.kuliaojk.api

import com.kuliao.kuliaojk.data.User
import com.kuliao.kuliaojk.http.BaseResponse
import com.kuliao.kuliaojk.net.APIPath
import retrofit2.http.*

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:02
 * Description:
 */
interface UserApi {

    @POST(APIPath.Basic.LOGIN)
    @FormUrlEncoded
    suspend fun login(@Field("userName") userName: String, @Field("password") pwd: String): User

    @POST(APIPath.Basic.LOGOUT)
    @FormUrlEncoded
    suspend fun logout(@FieldMap params: MutableMap<String, Any>):BaseResponse
}