package com.kuliao.kuliaojk.ui.login

import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.act.BaseVMActivity
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.data.User
import com.kuliao.kuliaojk.databinding.ActivityLoginBinding
import com.kuliao.kuliaojk.vm.UserLoginModel
import com.kuliao.kuliaojk.vm.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/24  14:24
 * Description:
 */
class LoginActivity : BaseVMActivity<ActivityLoginBinding>() {

    override fun getViewModel(): BaseViewModel = mViewModel

    private val mViewModel: UserViewModel by viewModel()

    private val mUserLoginModel: UserLoginModel by lazy { UserLoginModel() }

    override fun getLayoutId() = R.layout.activity_login

    override fun initViewsAndEvents() {
        mBinding.loginBtn.setOnClickListener {

            val name = mBinding.etName.text.toString()
            val password = mBinding.etPassword.text.toString()

            mViewModel.getUser(name, password).observe(this, Observer<User> {
                ToastUtils.showShort(it.userName)
            })

        }
    }
}