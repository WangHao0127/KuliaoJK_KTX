package com.kuliao.kuliaojk.ui.home

import com.blankj.utilcode.util.LogUtils
import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.databinding.FragmentHomeBinding
import com.kuliao.kuliaojk.vm.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/22  14:30
 * Description:
 */
class HomeFragment : BaseDBVMFragment<FragmentHomeBinding>() {

    private val mViewModel: HomeViewModel by viewModel()

    override fun getViewModel() = mViewModel

    override fun getLayoutRes()= R.layout.fragment_home

    override fun initView() {

    }

    override fun initData() {
        LogUtils.d("HomeFragment")
    }

}