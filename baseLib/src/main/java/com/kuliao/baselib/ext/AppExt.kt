package com.kuliao.baselib.ext

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: WangHao
 * Created On: 2020/06/22  10:49
 * Description:
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.go() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.goAndFinish() {
    startActivity(Intent(this, T::class.java))
    finish()
}

inline fun <reified T : AppCompatActivity> go(context: Context) {
    context.startActivity(Intent(context, T::class.java))
}