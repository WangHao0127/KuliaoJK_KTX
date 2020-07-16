package com.kuliao.kuliaojk.http

import android.util.Log
import com.google.gson.GsonBuilder
import com.kuliao.kuliaojk.BuildConfig
import com.kuliao.kuliaojk.BuildConfig.BASE_URL
import com.kuliao.kuliaojk.api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 60L
private const val TAG = "Kuliao"

val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.e(TAG, message)
    }
}).also {
    it.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}

val okHttpClient = OkHttpClient.Builder()
//    .addInterceptor(AuthorizationInterceptor())
    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
    .addInterceptor(HttpLoggingInterceptor())
    .readTimeout(TIME_OUT, TimeUnit.SECONDS).also {
        if (BuildConfig.DEBUG) {
            it.addInterceptor(httpLoggingInterceptor)
        }
    }.build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GoonConverterFactory.create(GsonBuilder().serializeNulls().create()))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

object UserService : UserApi by retrofit.create(UserApi::class.java)

