package com.kuliao.kuliaojk.ui.my

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.baselib.valid.Action
import com.kuliao.baselib.valid.SingleCall
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.databinding.FragmentMyBinding
import com.kuliao.kuliaojk.valid.LoginValid
import com.kuliao.kuliaojk.vm.MyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/22  16:39
 * Description:
 */
class MyFragment : BaseDBVMFragment<FragmentMyBinding>(), Action {

    private val mViewModel: MyViewModel by viewModel()

    override fun getViewModel() = mViewModel

    override fun initView() {
        mBinding.refreshView?.let {
            it.setColorSchemeResources(R.color.fontColor_orange)
            it.setOnRefreshListener {
                it.isRefreshing = false
            }
        }

        mBinding.username.setOnClickListener {
            SingleCall.get().addAction(this)
                .addValid(LoginValid(requireActivity()))
                .doCall()
        }
    }

    override fun getLayoutRes() = R.layout.fragment_my

    override fun initData() {
        LogUtils.d("MyFragment")
    }

    override fun call() {
        ToastUtils.showShort("苦瓜")
    }
}