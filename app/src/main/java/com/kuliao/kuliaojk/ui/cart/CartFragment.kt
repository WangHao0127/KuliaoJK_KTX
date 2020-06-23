package com.kuliao.kuliaojk.ui.cart

import com.blankj.utilcode.util.LogUtils
import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.databinding.FragmentCartBinding
import com.kuliao.kuliaojk.databinding.FragmentHomeBinding
import com.kuliao.kuliaojk.vm.CartViewModel
import com.kuliao.kuliaojk.vm.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author: WangHao
 * Created On: 2020/06/22  14:30
 * Description:
 */
class CartFragment : BaseDBVMFragment<FragmentCartBinding>() {

    private val mViewModel: CartViewModel by viewModel()

    override fun getViewModel() = mViewModel

    override fun getLayoutRes()= R.layout.fragment_cart

    override fun initView() {

    }

    override fun initData() {
        LogUtils.d("CartFragment")
    }
}