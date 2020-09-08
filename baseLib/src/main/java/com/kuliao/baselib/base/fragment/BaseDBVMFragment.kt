package com.kuliao.baselib.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.baselib.base.vm.ErrorState
import com.kuliao.baselib.base.vm.LoadState
import com.kuliao.baselib.base.vm.SuccessState
import com.kuliao.baselib.ext.yes

/**
 * Author: WangHao
 * Created On: 2020/06/22  14:21
 * Description:
 */
abstract class BaseDBVMFragment<DB : ViewDataBinding> : BaseFragment() {

    lateinit var mBinding: DB
    private var isFirstLoad = true//是否是第一次加载

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
            // 让xml内绑定的LiveData和Observer建立连接，让LiveData能感知Activity的生命周期.
            mBinding.lifecycleOwner = this
            mRootView = mBinding.root
        }
        return mRootView
    }

    override fun setContentLayout() {
        initViewModelAction()
    }

    override fun onResume() {
        super.onResume()
        (isFirstLoad).yes {
            initView()
            initData()
            isFirstLoad = false
        }
    }

    abstract fun getViewModel(): BaseViewModel

    private fun initViewModelAction() {
        this.getViewModel().let { baseViewModel ->
            baseViewModel.mStateLiveData.observe(this, Observer { stateActionState ->
                when (stateActionState) {
                    LoadState -> showLoading()
                    SuccessState -> dismissLoading()
                    is ErrorState -> {
                        dismissLoading()
                        stateActionState.message?.apply {
                            ToastUtils.showShort(this)
                            handleError()
                        }
                    }
                }
            })
        }
    }

    abstract fun initView()

    abstract fun initData()

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