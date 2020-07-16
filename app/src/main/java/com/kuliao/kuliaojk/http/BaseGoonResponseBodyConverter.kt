package com.kuliao.kuliaojk.http

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * Author: WangHao
 * Created On: 2020/07/15  10:14
 * Description:
 */
class BaseGoonResponseBodyConverter<T>(private val goon: Gson, private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? {
        val jsonReader = goon.newJsonReader(value.charStream())

        val jsonObject = JsonParser().parse(jsonReader).asJsonObject
        value.close()

        val response = goon.fromJson(jsonObject, BaseResponse::class.java)

        var element = jsonObject.get("dataObject")

        if (response != null && !response.isSuccessful()) {
            val errorInfo = try {
                goon.fromJson(element, String::class.java)
            } catch (e: JsonSyntaxException) {
                LogUtils.d(e)
                element?.let { toString() }
            }

            val e = ErrorResponseException(response, errorInfo.toString())
            throw e
        }

        if (response.isSuccessful() && element == null) {
            // 返回码为成功而数据部分为空时，默认返回成功的boolean值true
            element = JsonParser().parse("{\"dataObject\":\"true\"}").asJsonObject.get("dataObject")
        }

        try {
            return adapter.fromJsonTree(element)
        } catch (e: Exception) {
            LogUtils.d(e)
            LogUtils.d(element.toString())

            throw e
        }

    }
}