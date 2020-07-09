package com.kuliao.baselib.base.act

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.baselib.base.vm.ErrorState
import com.kuliao.baselib.base.vm.LoadState
import com.kuliao.baselib.base.vm.SuccessState

/**
 * Author: WangHao
 * Created On: 2020/07/09  13:41
 * Description:
 */
abstract class BaseVMActivity<DB : ViewDataBinding> : BaseActivity() {

    lateinit var mBinding: DB

    override fun initDBorVM() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
        initViewModelAction()
    }

    private fun initViewModelAction() {
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

    open fun showLoading() {

    }

    open fun dismissLoading() {

    }

    open fun handleError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}