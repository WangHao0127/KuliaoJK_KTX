package com.kuliao.kuliaojk.ui.login

import androidx.lifecycle.lifecycleScope
import com.afollestad.assent.AssentResult
import com.afollestad.assent.Callback
import com.afollestad.assent.Permission
import com.kuliao.baselib.base.act.BaseDBActivity
import com.kuliao.baselib.ext.*
import com.kuliao.kuliaojk.MainActivity
import com.kuliao.kuliaojk.R.layout
import com.kuliao.kuliaojk.config.Settings
import com.kuliao.kuliaojk.dao.UserDao
import com.kuliao.kuliaojk.databinding.ActivityAdvertisementBinding
import com.kuliao.kuliaojk.weight.CircleProgressbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Author: WangHao
 * Created On: 2020/06/19  17:08
 * Description:
 */
class AdvertisementActivity : BaseDBActivity<ActivityAdvertisementBinding>() {

    private val mUserDao: UserDao by inject()

    override fun getLayoutId() = layout.activity_advertisement

    override fun initViewsAndEvents() {
        askForPermission()
    }

    private fun askForPermission() {
        runWithPermissions(
            Permission.READ_PHONE_STATE, Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE, granted = object : Callback {
                override fun invoke(result: AssentResult) {
                    checkIsLogin()
                }
            }, denied = object : Callback {
                override fun invoke(result: AssentResult) {
                    finish()
                }
            })
    }

    private fun checkIsLogin() {
        lifecycleScope.launch {
            val user = mUserDao.getUserInfo()
                (user?.nickName.isNullOrBlank()).no {
                    Settings.Account.let { account ->
                        user.userId.let {
                            account.userId = it
                        }
                        user.headPhoto?.let {
                            account.head_photo = it
                        }
                        user.nickName?.let {
                            account.nickname = it
                        }
                        user.zgha?.let {
                            account.zgha = it
                        }
                        user.authToken?.let {
                            account.token = it
                        }
                    }
            }

            initView()
        }
    }

    private fun initView() {
        mBinding.cbBar.apply {
            setCountdownProgressListener(
                0,
                object : CircleProgressbar.OnCountdownProgressListener {
                    override fun onProgress(what: Int, progress: Int) {
                        if (progress <= 0) {
                            goAndFinish<MainActivity>()
                        }
                    }
                })
            start()
            setOnClickListener { goAndFinish<MainActivity>() }
        }
    }

    override fun isStatusBarOverlap() = true
}