package com.kuliao.kuliaojk.ui.my

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.baselib.ext.otherwise
import com.kuliao.baselib.ext.yes
import com.kuliao.baselib.valid.Action
import com.kuliao.baselib.valid.SingleCall
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.config.Settings
import com.kuliao.kuliaojk.databinding.FragmentMyBinding
import com.kuliao.kuliaojk.valid.LoginValid
import com.kuliao.kuliaojk.vm.UserViewModel
import com.kuliao.kuliaojk.weight.CustomPopupWindow
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

    private lateinit var popLogout: CustomPopupWindow

    override fun onResume() {
        super.onResume()
        mViewModel.isLogged()
    }

    override fun initView() {
        mViewModel.isLogged.observe(this, Observer { its ->

            its.yes {
                mBinding.logoutTv.let { its ->
                    its.visibility = View.VISIBLE
                    its.setOnClickListener {
                        popLogout.showAtLocation(it)
                    }
                }


            }.otherwise {
                mBinding.logoutTv.visibility = View.GONE
                mBinding.username.text = resources.getString(R.string.login_status_none)
            }

        })

        mBinding.refreshView.apply {
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

        mViewModel.mUserInfoModel.observe(this, Observer {
            bindingLayout()
        })
    }

    private fun bindingLayout() {

//                Glide.with(requireActivity())
//                    .load(Settings.Account.head_photo)
//                    .error(R.drawable.default_avatar)
//                    .placeholder(R.drawable.default_avatar)
//                    .into(mBinding.ivAvatar)
//
        mBinding.username.text = Settings.Account.nickname
    }


    override fun getLayoutRes() = R.layout.fragment_my

    override fun initData() {
        mViewModel.getLocalUserInfo()
        initDialog()
    }


    private fun initDialog() {
        val popView = LayoutInflater.from(requireActivity()).inflate(R.layout.pop_logout, null)
        popView.findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            popLogout.onDismiss()

        }
        popView.findViewById<Button>(R.id.btnCancel).setOnClickListener { popLogout.onDismiss() }

        popLogout = CustomPopupWindow.PopupWindowBuilder(context).setView(popView, true)
            .create()
    }

    override fun call() {
        ToastUtils.showShort("苦瓜")
    }
}