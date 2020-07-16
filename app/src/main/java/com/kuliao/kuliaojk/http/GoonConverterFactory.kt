package com.kuliao.kuliaojk.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Author: WangHao
 * Created On: 2020/07/15  10:09
 * Description:
 */
class GoonConverterFactory(private val goon: Gson) : Converter.Factory() {

    init {
        if (goon == null) {
            throw NullPointerException("gson == null")
        }
    }

    companion object{

        fun create(): GoonConverterFactory {
            return create(Gson())
        }

        fun create(gain: Gson): GoonConverterFactory {
            return GoonConverterFactory(gain)
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter = goon.getAdapter(TypeToken.get(type))
        return BaseGoonResponseBodyConverter(goon, adapter)
    }


    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter = goon.getAdapter(TypeToken.get(type))
        return BaseGoonRequestBodyConverter(goon, adapter)
    }

}