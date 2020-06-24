package com.kuliao.baselib.ext

import android.content.Context
import android.content.Intent
import com.kuliao.baselib.base.activity.BaseActivity

/**
 * Author: WangHao
 * Created On: 2020/06/22  10:49
 * Description:
 */
inline fun <reified T : BaseActivity> BaseActivity.go() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : BaseActivity> BaseActivity.goAndFinish() {
    startActivity(Intent(this, T::class.java))
    finish()
}

inline fun <reified T : BaseActivity> go(context: Context) {
    context.startActivity(Intent(context, T::class.java))
}
