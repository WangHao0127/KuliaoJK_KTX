package com.kuliao.baselib.base.act

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.kuliao.baselib.R
import com.kuliao.baselib.base.vm.BaseViewModel
import com.kuliao.baselib.base.vm.ErrorState
import com.kuliao.baselib.base.vm.LoadState
import com.kuliao.baselib.base.vm.SuccessState
import kotlinx.android.synthetic.main.dialog_default_loading.*

/**
 * Author: WangHao
 * Created On: 2020/07/09  13:41
 * Description:
 */
abstract class BaseVMActivity<DB : ViewDataBinding> : BaseActivity() {

    lateinit var mBinding: DB
    private val loadingPop: Dialog by lazy {
        Dialog(this, R.style.NoTitleDialogStyle).apply {
            setContentView(R.layout.dialog_default_loading)
            Glide.with(this.context).asGif().load(R.drawable.dialog_loading).into(this.ivLoading)
            this.tvLoading.text=resources.getText(R.string.loading)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun initDBorVM() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
        initViewModelAction()
    }

    private fun initViewModelAction() {
        getViewModel().let {
            it.mStateLiveData.observe(this, Observer { stateActionEvent ->
                when (stateActionEvent) {
                    is LoadState -> showLoading()
                    SuccessState -> dismissLoading()
                    is ErrorState -> {
                        dismissLoading()
                        stateActionEvent.message?.apply {
                            ToastUtils.showShort(this)
                            handleError()
                        }
                    }
                }
            })
        }
    }

    abstract fun getViewModel(): BaseViewModel

    open fun showLoading() {
        loadingPop.show()
    }

    open fun dismissLoading() {
        loadingPop.dismiss()
    }

    open fun handleError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}