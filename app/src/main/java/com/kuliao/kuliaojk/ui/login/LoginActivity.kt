package com.kuliao.kuliaojk.ui.login

import com.kuliao.baselib.base.activity.BaseDBVMActivity
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.databinding.ActivityLoginBinding
import com.kuliao.kuliaojk.vm.LoginViewModel
import com.kuliao.kuliaojk.vm.MyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/24  14:24
 * Description:
 */
class LoginActivity : BaseDBVMActivity<ActivityLoginBinding>() {

    override fun getViewModel() = mViewModel

    private val mViewModel: LoginViewModel by viewModel()

    override fun getLayoutId() = R.layout.activity_login

    override fun initView() {

    }

    override fun initData() {

    }
}