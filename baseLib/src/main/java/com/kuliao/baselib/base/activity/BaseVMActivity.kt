package com.kuliao.baselib.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gyf.immersionbar.ImmersionBar
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.baselib.base.vm.ErrorState
import com.kuliao.baselib.base.vm.LoadState
import com.kuliao.baselib.base.vm.SuccessState
import com.kuliao.baselib.ext.errorToast

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
                            errorToast(this)
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