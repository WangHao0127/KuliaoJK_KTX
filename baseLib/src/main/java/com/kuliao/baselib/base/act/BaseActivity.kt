package com.kuliao.baselib.base.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar

/**
 * Author: WangHao
 * Created On: 2020/07/09  10:13
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* 判断是否使用沉浸式状态栏 */
        if (isImmersionBarEnabled()) {
            initImmersionBar()
        }

        initDBorVM()
        initViewsAndEvents()
    }

    private fun initImmersionBar() {
        immersionBar {
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                statusBarDarkFont(true)
            } else {//如果手机支不支持状态栏字体变色
                statusBarDarkFont(true, 0.2f)
            }
            keyboardEnable(true)
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initViewsAndEvents()

    /*是否可以使用沉浸式*/
    fun isImmersionBarEnabled() = true

    /*是否和状态栏重叠：默认不重叠*/
    open fun isStatusBarOverlap() = false

    abstract fun initDBorVM()

}