package com.kuliao.kuliaojk

import android.app.Application
import android.content.ContextWrapper
import android.content.res.Resources
import com.blankj.utilcode.util.AdaptScreenUtils
import com.kuliao.kuliaojk.di.appModule
import org.koin.core.context.startKoin

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
        startKoin { modules(appModule) }
    }
}

object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)