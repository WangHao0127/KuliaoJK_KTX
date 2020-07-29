package com.kuliao.kuliaojk.ui.login

import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.act.BaseVMActivity
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.baselib.ext.otherwise
import com.kuliao.baselib.ext.yes
import com.kuliao.kuliaojk.BuildConfig
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.data.User
import com.kuliao.kuliaojk.databinding.ActivityLoginBinding
import com.kuliao.kuliaojk.net.APIPath
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

    override fun getLayoutId() = R.layout.activity_login

    override fun initViewsAndEvents() {
        mBinding.loginBtn.setOnClickListener {

            val name = mBinding.etName.text.toString()
            val password = mBinding.etPassword.text.toString()

            name.isNotBlank().yes {
                password.isNotBlank().yes {
                    mViewModel.getUser(name, password).observe(this, Observer<User> {
                        mViewModel.mUserInfoModel.value = it
                        mViewModel.saveLocalUser(it)
                        finish()
                    })
                }.otherwise { ToastUtils.showShort(resources.getString(R.string.login_info_null)) }
            }.otherwise { ToastUtils.showShort(resources.getString(R.string.login_info_null)) }
        }

        mBinding.closeImg.setOnClickListener {
            finish()
        }

        mBinding.forgetPwdTv.setOnClickListener {

            val name = mBinding.etName.text.toString()

            name.isNotBlank().yes {
                val url = with(StringBuilder()) {
                    append(BuildConfig.FORGET_PWD_URL)
                    append(APIPath.Account.GET_BACK_LOGIN_PASSWORD)
                    append("?userName=$name&appName=kuliao&type=")
                }
                ToastUtils.showShort(url)
            }.otherwise { ToastUtils.showShort(resources.getString(R.string.login_username_hint)) }
        }
    }
}