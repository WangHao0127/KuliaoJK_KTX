package com.kuliao.kuliaojk.weight

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Author: WangHao
 * Created On: 2020/06/23  9:44
 * Description:
 */
class SwipeRefreshView(context: Context, attrs: AttributeSet?) :
    SwipeRefreshLayout(context, attrs) {

    private var mZoomView: View? = null
    private var mZoomViewWidth = 0
    private var mZoomViewHeight = 0

    private var firstPosition = 0.0F
    private var isScrolling = false
    private val mScrollRate = 0.7F //缩放系数，缩放系数越大，变化的越大
    private val mReplyRate = 0.5F//回调系数，越大，回调越慢

    private var mYDown: Int = 0
    private var mLastY = 0

    constructor(context: Context) : this(context, attrs = null)

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    private fun init() {
        overScrollMode = View.OVER_SCROLL_NEVER
        getChildAt(1)?.let {
            (getChildAt(1) as ViewGroup).getChildAt(0)?.let {
                mZoomView = it
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            ACTION_DOWN ->
                mYDown = ev.rawY.toInt()
            ACTION_MOVE -> {
                mLastY = ev.rawY.toInt()
                if (mYDown > mLastY) {
                    return false
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        super.onTouchEvent(ev)
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            mZoomViewWidth = mZoomView!!.measuredWidth
            mZoomViewHeight = mZoomView!!.measuredHeight
        }

        when (ev.action) {
            ACTION_UP -> {
                //手指离开后恢复图片
                isScrolling = false
                replyImage()
            }
            ACTION_MOVE -> {
                if (!isScrolling) {
                    firstPosition = if (scrollY == 0) {
                        ev.y // 滚动到顶部时记录位置，否则正常返回
                    } else {
                        0.0F
                    }
                }
                val distance =
                    (ev.y - firstPosition) * mScrollRate// 滚动距离乘以一个系数
                // 处理放大
                isScrolling = true
                setZoom(distance)
                return true // 返回true表示已经完成触摸事件，不再处理
            }
        }
        return true
    }

    //回弹动画
    private fun replyImage() {
        val distance = with(mZoomView) {
            this?.let {
                it.measuredWidth.minus(mZoomViewWidth).toFloat()
            }
        }
        val valueAnimator = distance?.let {
            ValueAnimator.ofFloat(it, 0f).setDuration((it.times(mReplyRate)).toLong())
        }
        valueAnimator?.let { it ->
            it.addUpdateListener {
                setZoom(it.animatedValue as Float)
            }
            it.start()
        }

    }

    private fun setZoom(zoom: Float) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            return
        }
        mZoomView?.layoutParams?.let {
            it.width = mZoomViewWidth.plus(zoom).toInt()
            it.height =
                mZoomViewHeight.times(((mZoomViewWidth.plus(zoom)).div(mZoomViewWidth))).toInt()
            (it as MarginLayoutParams)
                .setMargins(-(it.width.minus(mZoomViewWidth)).div(2), 0, 0, 0)
            mZoomView?.layoutParams = it
        }

    }
}