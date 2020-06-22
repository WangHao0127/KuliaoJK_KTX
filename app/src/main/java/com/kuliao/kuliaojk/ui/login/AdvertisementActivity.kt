package com.kuliao.kuliaojk.ui.login

import com.kuliao.baselib.base.activity.BaseDBActivity
import com.kuliao.baselib.ext.go
import com.kuliao.baselib.ext.goAndFinish
import com.kuliao.kuliaojk.MainActivity
import com.kuliao.kuliaojk.R.layout
import com.kuliao.kuliaojk.databinding.ActivityAdvertisementBinding
import com.kuliao.kuliaojk.weight.CircleProgressbar

/**
 * Author: WangHao
 * Created On: 2020/06/19  17:08
 * Description:
 */
class AdvertisementActivity : BaseDBActivity<ActivityAdvertisementBinding>() {

    override fun getLayoutId() = layout.activity_advertisement

    override fun initView() {

    }

    override fun initData() {
        mBinding.cbBar.setCountdownProgressListener(
            0,
            object : CircleProgressbar.OnCountdownProgressListener {
                override fun onProgress(what: Int, progress: Int) {
                    if (progress <= 0) {
                        go<MainActivity>()
                    }
                }
            })
        mBinding.cbBar.start()
        mBinding.setClick { goAndFinish<MainActivity>() }
    }

    override fun isStatusBarOverlap() = true
}