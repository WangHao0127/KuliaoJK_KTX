package com.kuliao.kuliaojk.ui.my

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.kuliao.baselib.base.fragment.BaseDBVMFragment
import com.kuliao.baselib.ext.otherwise
import com.kuliao.baselib.ext.yes
import com.kuliao.baselib.valid.Action
import com.kuliao.baselib.valid.SingleCall
import com.kuliao.dialog.CommonDialog
import com.kuliao.dialog.extensions.bindingListenerFun
import com.kuliao.dialog.extensions.convertListenerFun
import com.kuliao.dialog.extensions.createDialog
import com.kuliao.dialog.extensions.dataConvertListenerFun
import com.kuliao.dialog.other.DialogGravity
import com.kuliao.kuliaojk.R
import com.kuliao.kuliaojk.config.Settings
import com.kuliao.kuliaojk.databinding.FragmentMyBinding
import com.kuliao.kuliaojk.databinding.PopLogoutBinding
import com.kuliao.kuliaojk.valid.LoginValid
import com.kuliao.kuliaojk.vm.UserViewModel
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

    private val dialog: CommonDialog by lazy {
        createDialog {
            layoutId = R.layout.pop_logout
            isFullHorizontal = true
            convertListenerFun { holder, dialog ->
                holder.setOnClickListener(R.id.btnCancel) {
                    dialog.dismiss()
                }
                holder.setOnClickListener(R.id.btnConfirm) {
                    mViewModel.logout().observe(this@MyFragment, Observer {
                        mViewModel.isLogged.value = false
                    })
                    dialog.dismiss()
                }
            }
        }
    }

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
                        dialog.showOnWindow(
                            requireActivity().supportFragmentManager,
                            DialogGravity.CENTER_BOTTOM,
                            R.style.BottomTransAlphaAcceAnimation
                        )
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
        val showName = Settings.Account.nickname
        (showName!!.isEmpty()).yes {
            mBinding.username.text = resources.getString(R.string.login_status_none)
        }.otherwise {
            mBinding.username.text = showName
        }
    }


    override fun getLayoutRes() = R.layout.fragment_my

    override fun initData() {
        mViewModel.getLocalUserInfo()
    }

    override fun call() {
        ToastUtils.showShort("苦瓜")
    }
}