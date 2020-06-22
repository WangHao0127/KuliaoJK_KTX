package com.kuliao.baselib.base.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.baselib.base.vm.ErrorState
import com.kuliao.baselib.base.vm.LoadState
import com.kuliao.baselib.base.vm.SuccessState

/**
 * Author: WangHao
 * Created On: 2020/06/17  15:14
 * Description: 让BaseDBVMActivity继承的类
 */
abstract class BaseVMActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLayout()
    }

    protected fun initViewModelAction() {
        getViewModel().let {
            it.mStateLiveData.observe(this, Observer { stateActionEvent ->
                when (stateActionEvent) {
                    is LoadState -> showLoading()
                    SuccessState -> dismissLoading()
                    is ErrorState -> {
                        dismissLoading()
                        stateActionEvent.message?.apply {
                            ToastUtils.showShort(this)
                            handleError()
                        }
                    }
                }
            })
        }
    }


    abstract fun getViewModel(): BaseViewModel

    override fun setContentLayout() {
        setContentView(getLayoutId())
        initViewModelAction()
        initView()
        initData()
    }

    open fun showLoading() {

    }

    open fun dismissLoading() {

    }

    open fun handleError() {

    }
}