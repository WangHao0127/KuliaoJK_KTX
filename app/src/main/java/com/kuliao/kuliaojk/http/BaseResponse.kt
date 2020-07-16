package com.kuliao.kuliaojk.http

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Author: WangHao
 * Created On: 2020/07/15  9:41
 * Description:
 */
data class BaseResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("statusDescription")
    val statusDescription: String
) : Serializable {

    fun isSuccessful() = statusCode == StatusCodeEnum.SUCCESSFUL.code

    fun isNoData() = statusCode ==  StatusCodeEnum.NO_DATA.code

}

