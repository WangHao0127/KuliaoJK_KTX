package com.kuliao.baselib.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kuliao.baselib.base.vm.BaseViewModel

/**
 * Author: WangHao
 * Created On: 2020/06/17  15:14
 * Description:
 */
abstract class BaseVMActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLayout()
    }


    private fun initViewModelAction() {
        getViewModel()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun getViewModel(): BaseViewModel

    open fun setContentLayout() {
        setContentView(getLayoutId())
        initViewModelAction()
        initView()
        initData()
    }


    open fun initData() {

    }

    open fun showLoading() {

    }

    open fun dismissLoading() {

    }

    open fun handleError() {

    }
}