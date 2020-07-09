package com.kuliao.kuliaojk.ui.login

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kuliao.baselib.base.act.BaseDBActivity
import com.kuliao.baselib.ext.goAndFinish
import com.kuliao.kuliaojk.R.anim
import com.kuliao.kuliaojk.R.layout
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
    override fun initViewsAndEvents() {
        mAnimation = AnimationUtils.loadAnimation(this, anim.splash_alpha)
        mAnimation.setAnimationListener(this)

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