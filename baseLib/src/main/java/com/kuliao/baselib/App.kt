package com.kuliao.baselib

import android.app.Application
import android.content.ContextWrapper

/**
 * Author: WangHao
 * Created On: 2020/06/19  14:22
 * Description:
 */
lateinit var mApplication: Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

}

object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)