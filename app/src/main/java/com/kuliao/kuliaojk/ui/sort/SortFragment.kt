package com.kuliao.kuliaojk.ui.sort

import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.databinding.FragmentSortBinding
import com.kuliao.kuliaojk.vm.SortViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/22  16:36
 * Description:
 */
class SortFragment : BaseDBVMFragment<FragmentSortBinding>() {

    private val mViewModel: SortViewModel by viewModel()

    override fun getViewModel() = mViewModel

    override fun initView() {

    }

    override fun getLayoutRes() = R.layout.fragment_sort

}