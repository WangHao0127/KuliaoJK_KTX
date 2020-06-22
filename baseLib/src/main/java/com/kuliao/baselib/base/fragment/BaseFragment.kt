package com.kuliao.baselib.base.fragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.common_app_bar.view.*
import kotlinx.android.synthetic.main.statusbar_view.view.*

/**
 * Author: WangHao
 * Created On: 2020/06/22  13:48
 * Description:
 */
abstract class BaseFragment : Fragment() {

    protected var mRootView: View? = null

    lateinit var mActivity: AppCompatActivity

    protected var statusBarView: View? = null
    protected var toolbar: RelativeLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = View.inflate(container?.context, getLayoutRes(), null)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusBarView = view.status_bar_view
        toolbar = view.title_bar
        fitsLayoutOverlap()

        setContentLayout()
    }

    open abstract fun setContentLayout()

    abstract fun getLayoutRes(): Int

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //旋转屏幕要重新设置布局与状态栏重叠,因为旋转屏幕有可能使状态栏高度不一样，
        // 如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap()
    }

    private fun fitsLayoutOverlap() {
        statusBarView?.let {
            ImmersionBar.setStatusBarView(this, statusBarView)
        }
        toolbar?.let {
            ImmersionBar.setTitleBar(this, toolbar)
        }
    }
}