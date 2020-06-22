package com.kuliao.baselib.base.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.common_app_bar.*
import kotlinx.android.synthetic.main.statusbar_view.*

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
        fitsLayoutOverlap()
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


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //旋转屏幕要重新设置布局与状态栏重叠,因为旋转屏幕有可能使状态栏高度不一样，
        // 如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap()
    }

    private fun fitsLayoutOverlap() {
        title_bar?.let {//有标题栏，则不用额外布局填充
            ImmersionBar.setTitleBar(this, title_bar)
        }
        status_bar_view?.let {
            if (!isStatusBarOverlap()) {//用额外布局填充
                ImmersionBar.setStatusBarView(this, status_bar_view)
            }
        }
    }

    /*是否可以使用沉浸式*/
    fun isImmersionBarEnabled() = true

    /*是否和状态栏重叠：默认不重叠*/
    open fun isStatusBarOverlap() = false

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()
}