package com.kuliao.baselib.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Author: WangHao
 * Created On: 2020/06/17  15:03
 * Description:包含DataBinding的activity基类
 */
abstract class BaseDBActivity<DB : ViewDataBinding> : BaseActivity() {

    lateinit var mBinding: DB

    override fun setContentLayout() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}