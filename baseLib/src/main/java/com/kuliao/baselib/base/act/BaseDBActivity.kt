package com.kuliao.baselib.base.act

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Author: WangHao
 * Created On: 2020/07/09  13:43
 * Description:
 */
abstract class BaseDBActivity<DB : ViewDataBinding> : BaseActivity() {

    lateinit var mBinding: DB

    override fun initDBorVM() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}