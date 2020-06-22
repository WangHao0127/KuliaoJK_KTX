package com.kuliao.kuliaojk.ui.my

import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.databinding.FragmentMyBinding
import com.kuliao.kuliaojk.vm.MyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/22  16:39
 * Description:
 */
class MyFragment : BaseDBVMFragment<FragmentMyBinding>() {

    private val mViewModel: MyViewModel by viewModel()

    override fun getViewModel() = mViewModel

    override fun initView() {

    }

    override fun getLayoutRes() = R.layout.fragment_my

}