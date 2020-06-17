package com.kuliao.baselib.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: WangHao
 * Created On: 2020/06/17  15:01
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLayout()
    }

    open fun setContentLayout() {
        setContentView(getLayoutId())
        initView()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()
}