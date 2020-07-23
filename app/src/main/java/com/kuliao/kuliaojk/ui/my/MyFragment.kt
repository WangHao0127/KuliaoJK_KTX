package com.kuliao.kuliaojk.ui.my

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.baselib.valid.Action
import com.kuliao.baselib.valid.SingleCall
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.dao.UserDao
import com.kuliao.kuliaojk.databinding.FragmentMyBinding
import com.kuliao.kuliaojk.valid.LoginValid
import com.kuliao.kuliaojk.vm.MyViewModel
import com.kuliao.kuliaojk.vm.UserViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/22  16:39
 * Description:
 */
class MyFragment : BaseDBVMFragment<FragmentMyBinding>(), Action {

    //private val mViewModel: MyViewModel by viewModel()
    private val mViewModel: UserViewModel by viewModel()

    override fun getViewModel() = mViewModel

    override fun initView() {
        mBinding.refreshView?.apply {
            setColorSchemeResources(R.color.fontColor_orange)
            setOnRefreshListener {
                isRefreshing = false
            }
        }

        mBinding.username.setOnClickListener {
            SingleCall.get().addAction(this)
                .addValid(LoginValid(requireActivity()))
                .doCall()
        }

        mBinding.logoutTv.setOnClickListener {
            mViewModel.getLocalUserInfo()
        }
        mViewModel.mUserInfoModel.observe(this, Observer {
            ToastUtils.showShort(it.nickName)
        })
    }

    override fun getLayoutRes() = R.layout.fragment_my

    override fun initData() {
        LogUtils.d("MyFragment")
    }

    override fun call() {
        ToastUtils.showShort("苦瓜")
    }
}