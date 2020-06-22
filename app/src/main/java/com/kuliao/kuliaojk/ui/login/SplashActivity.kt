package com.kuliao.kuliaojk.ui.login

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kuliao.baselib.base.activity.BaseDBActivity
import com.kuliao.baselib.ext.goAndFinish
import com.kuliao.kuliaojk.R.*
import com.kuliao.kuliaojk.databinding.ActivitySplashBinding

/**
 * Author: WangHao
 * Created On: 2020/06/19  14:38
 * Description:
 */
class SplashActivity : BaseDBActivity<ActivitySplashBinding>(),
    Animation.AnimationListener {

    lateinit var mAnimation: Animation

    override fun getLayoutId() = layout.activity_splash

    override fun initView() {
        mAnimation = AnimationUtils.loadAnimation(this, anim.splash_alpha)
        mAnimation.setAnimationListener(this)
    }

    override fun initData() {
        mBinding.rlRoot.animation = mAnimation
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {
        goAndFinish<AdvertisementActivity>()
    }

    override fun onAnimationStart(animation: Animation?) {

    }

}