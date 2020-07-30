package com.kuliao.kuliaojk.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.Charset

/**
 * Author: WangHao
 * Created On: 2020/07/15  10:55
 * Description:
 */
class BaseGoonRequestBodyConverter<T>(private val goon: Gson, private val adapter: TypeAdapter<T>) :
    Converter<T, RequestBody> {

    private val mediaType = "application/json; charset=UTF-8".toMediaTypeOrNull()
    private val charset = Charset.forName("UTF-8")

    override fun convert(value: T): RequestBody? {
        try {
            val buffer = Buffer()
            val writer: Writer = OutputStreamWriter(
                buffer.outputStream(), charset
            )
            val jsonWriter = goon.newJsonWriter(writer)
            adapter.write(jsonWriter, value)
            jsonWriter.close()
            return buffer.readByteString()
                .toRequestBody(mediaType)
        } catch (e: IOException) {
            throw e
        }
    }
}