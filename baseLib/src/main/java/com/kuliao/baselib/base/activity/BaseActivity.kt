package com.kuliao.baselib.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * Author: WangHao
 * Created On: 2020/06/17  15:01
 * Description: 其他三个类的基类，用于初始化相关
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mImmersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* 判断是否使用沉浸式状态栏 */
        if (isImmersionBarEnabled()) {
            initImmersionBar()
        }
        setContentLayout()
    }

    open fun setContentLayout() {
        setContentView(getLayoutId())
        initView()
        initData()
    }

    private fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            mImmersionBar.statusBarDarkFont(true).keyboardEnable(true).init()
        } else { //如果手机支不支持状态栏字体变色
            mImmersionBar.statusBarDarkFont(true, 0.2f).keyboardEnable(true).init()
        }
    }

    /*是否可以使用沉浸式*/
    fun isImmersionBarEnabled() = true

    /*是否和状态栏重叠：默认不重叠*/
    fun isStatusBarOverlap() = false

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()
}