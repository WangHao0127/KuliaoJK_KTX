package com.kuliao.kuliaojk

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.kuliao.baselib.base.activity.BaseDBActivity
import com.kuliao.kuliaojk.databinding.ActivityMainBinding
import com.kuliao.kuliaojk.ui.home.HomeFragment
import kotlinx.android.synthetic.main.tab_text_icon.*
import kotlinx.android.synthetic.main.tab_text_icon.view.*

class MainActivity: BaseDBActivity<ActivityMainBinding>() {

    val titles =
        arrayOf(R.string.home_main, R.string.sort_main, R.string.cart_main, R.string.my_main)
    val pics = arrayOf(
        R.drawable.tab_home_selector, R.drawable.tab_sort_selector, R.drawable.tab_cart_selector,
        R.drawable.tab_home_selector
    )

    private lateinit var mMediator: TabLayoutMediator

    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        mBinding.vp.isUserInputEnabled = false
        mBinding.vp.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = titles.size

            override fun createFragment(position: Int): Fragment =
                when (position) {
                    0 -> HomeFragment()
                    1 -> HomeFragment()
                    2 -> HomeFragment()
                    else -> HomeFragment()
                }

        }
        mMediator = TabLayoutMediator(mBinding.tabLayout, mBinding.vp,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.customView = makeTabView(position)

            })
        mMediator.attach()
    }


    override fun initData() {

    }

    private fun makeTabView(position: Int): View {
        val tabView = View.inflate(this, R.layout.tab_text_icon, null)

        tabView.tv.text = getString(titles[position])
        tabView.img.setImageResource(pics[position])

        return tabView

    }

    override fun onDestroy() {
        super.onDestroy()
        mMediator?.let { it.detach() }
    }
}