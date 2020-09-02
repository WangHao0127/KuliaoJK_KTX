package com.kuliao.kuliaojk

import android.os.Bundle
import com.kuliao.baselib.base.act.BaseDBActivity
import com.kuliao.kuliaojk.databinding.ActivityDemoBinding

/**
 * Author: WangHao
 * Created On: 2020/09/02  10:27
 * Description:
 */
class DemoActivity: BaseDBActivity<ActivityDemoBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
    }

    override fun getLayoutId()= R.layout.activity_demo

    override fun initViewsAndEvents() {

    }
}