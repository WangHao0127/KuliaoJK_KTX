package com.kuliao.baselib.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Author: WangHao
 * Created On: 2020/06/19  14:29
 * Description:封装带有协程基类(DataBinding + ViewModel),使用代理类完成
 */
abstract class BaseDBVMActivity<DB : ViewDataBinding> : BaseVMActivity() {

    lateinit var mBinding: DB

    override fun setContentLayout() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
        initViewModelAction()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}