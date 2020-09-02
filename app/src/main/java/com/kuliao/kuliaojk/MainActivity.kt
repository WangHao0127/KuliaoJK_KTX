package com.kuliao.kuliaojk

import android.content.res.Resources
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.utilcode.util.AdaptScreenUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.kuliao.baselib.base.act.BaseDBActivity
import com.kuliao.kuliaojk.databinding.ActivityMainBinding
import com.kuliao.kuliaojk.ui.cart.CartFragment
import com.kuliao.kuliaojk.ui.home.HomeFragment
import com.kuliao.kuliaojk.ui.my.MyFragment
import com.kuliao.kuliaojk.ui.sort.SortFragment
import kotlinx.android.synthetic.main.tab_text_icon.view.*

class MainActivity : BaseDBActivity<ActivityMainBinding>() {

    private val titles =
        arrayOf(R.string.home_main, R.string.sort_main, R.string.cart_main, R.string.my_main)
    private val pics = arrayOf(
        R.drawable.tab_home_selector, R.drawable.tab_sort_selector, R.drawable.tab_cart_selector,
        R.drawable.tab_home_selector
    )

    private val mMediator: TabLayoutMediator by lazy {
        TabLayoutMediator(mBinding.tabLayout, mBinding.vp) { tab, position ->
            val tabView = View.inflate(this@MainActivity, R.layout.tab_text_icon, null)
            tabView.tv.text = getString(titles[position])
            tabView.img.setImageResource(pics[position])
            tab.customView = tabView
        }
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewsAndEvents() {
        mBinding.vp.run {
            isUserInputEnabled = false
            offscreenPageLimit = titles.size //让ViewPager2预先创建出所有的Fragment，防止切换造成的频繁销毁和创建
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount() = titles.size

                override fun createFragment(position: Int): Fragment =
                    when (position) {
                        0 -> HomeFragment()
                        1 -> SortFragment()
                        2 -> CartFragment()
                        else -> MyFragment()
                    }
            }
        }
        mMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediator.detach()
    }
}