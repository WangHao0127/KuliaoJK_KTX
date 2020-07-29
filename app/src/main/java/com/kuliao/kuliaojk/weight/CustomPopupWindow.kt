package com.kuliao.kuliaojk.weight

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.*
import android.view.View.OnTouchListener
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.kuliao.baselib.ext.otherwise
import com.kuliao.baselib.ext.yes
import com.kuliao.kuliaojk.R

/**
 * Author: WangHao
 * Created On: 2020/07/27  14:22
 * Description:
 */
class CustomPopupWindow(val context: Context) : PopupWindow.OnDismissListener {

    var mPopupWindow: PopupWindow? = null
    var mContentView: View? = null
    private var mResLayoutId = -1
    private var isAdaptiveHeight = false
    var mOnClosePopClickListener: View.OnClickListener? = null
    private var mIsBackgroundDark = true
    private var mBackgroundDarkValue = 0.5f
    private val DEFAULT_ALPHA = 0.4f
    private var mWindow: Window? = null
    private var mWidth: Int? = 0
    private var mHeight: Int? = 0
    private var mAnimationStyle = -1
    private var mClippingEnable = true
    private var mIgnoreCheekPress = false
    private var mInputMethodMode = -1
    private var mSoftInputMode = -1
    private var mOnDismissListener: PopupWindow.OnDismissListener? = null
    private var mOnTouchListener: OnTouchListener? = null
    private var mTouchable = true
    private var enableOutsideTouchDisMiss = true
    private var mIsFocusable = true
    private var backgroundDrawable: Drawable? = null
    private var mIsOutsideTouchable = true
    private var reserveBackground = false

    /**
     * 自定义PopupWindow弹出的地点
     * @param parent 点击后弹出PopupWindow的控件
     * @param gravity 对齐方式
     * @param x 水平偏移量
     * @param y 垂直偏移量
     * @return 构建完毕的PopupWindow
     */
    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int): CustomPopupWindow {
        mPopupWindow?.showAtLocation(parent, gravity, x, y)
        return this
    }

    /**
     * 默认显示在底部，水平偏移量和垂直偏移量为0
     * @param parent 点击后弹出PopupWindow的控件
     * @return 构建完毕的PopupWindow
     */
    fun showAtLocation(parent: View): CustomPopupWindow {
        return showAtLocation(parent, Gravity.BOTTOM, 0, 0)
    }

    /**
     * 显示在点击的控件下方
     * @param anchor 点击的控件
     * @return 构建完毕的PopupWindow
     */
    fun showAsDropDown(anchor: View): CustomPopupWindow {
        mPopupWindow?.showAsDropDown(anchor)
        return this
    }

    /**
     * 显示在点击的控件下方
     * @param anchor 点击的控件
     * @param x 水平偏移量
     * @param y 垂直偏移量
     * @return 构建完毕的PopupWindow
     */
    fun showAsDropDown(anchor: View, x: Int, y: Int): CustomPopupWindow {
        mPopupWindow?.showAsDropDown(anchor, x, y)
        return this
    }

    /**
     * 显示在点击的控件下方
     * @param anchor 点击的控件
     * @param x 水平偏移量
     * @param y 垂直偏移量
     * @param gravity 对齐方式
     * @return 构建完毕的PopupWindow
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun showAsDropDown(anchor: View, x: Int, y: Int, gravity: Int): CustomPopupWindow {
        mPopupWindow?.showAsDropDown(anchor, x, y, gravity)
        return this
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1).yes {
            wm.defaultDisplay.getRealSize(point)
        }.otherwise {
            wm.defaultDisplay.getSize(point)
        }
        return point.y
    }

    @SuppressLint("InflateParams", "ClickableViewAccessibility")
    private fun build() {
        var parentView: View? = null
        var contentView: View? = null
        (mContentView != null).yes {
            contentView = mContentView
        }.otherwise {
            contentView = LayoutInflater.from(context).inflate(mResLayoutId, null)
        }
        // 直接将传入的ContentView设为PopupWindow的内容视图
        isAdaptiveHeight.yes {
            parentView = contentView
        }.otherwise {
            val parentView = LayoutInflater.from(context).inflate(R.layout.custom_popupwindow, null)
            val flBackground = parentView.findViewById<FrameLayout>(R.id.fl_background)
            val rlClosePop = parentView.findViewById<RelativeLayout>(R.id.rl_close_pop)
            (mOnClosePopClickListener != null).yes {
                rlClosePop.setOnClickListener(mOnClosePopClickListener)
            }.otherwise {
                rlClosePop.setOnClickListener { dismissPopupWindow() }
            }
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // 将传入的contentView的布局参数设为MATCH_PARENT，铺满整个FrameLayout，addView时将其index设为0，
            // rlClosePop浮在contentView之上，rlClosePop方可点击
            flBackground.addView(contentView, 0, params)
        }

        // 获取当前Activity的窗口
        val activity = parentView?.context as Activity
        (activity != null && mIsBackgroundDark).yes {
            // 如果设置的值在0-1的范围内，用设置的值，否则用默认值
            @Suppress("DEPRECATION") val alpha =
                if (mBackgroundDarkValue in 1..0) mBackgroundDarkValue else DEFAULT_ALPHA
            mWindow = activity.window
            val params = mWindow?.attributes
            // 不透明度
            params?.alpha = alpha
            // 在某些手机（比如华为）上，PopupWindow背景变暗可能无效，需要使用下面一行代码
            mWindow?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            mWindow?.setAttributes(params)
        }

        (mWidth != 0 && mHeight != 0).yes {
            mPopupWindow = PopupWindow(parentView, mWidth!!, mHeight!!)
        }.otherwise {
            // 宽度固定，高度自适应
            isAdaptiveHeight.yes {
                mPopupWindow = PopupWindow(
                    parentView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }.otherwise {
                // 高度固定，占屏幕高度的3/2
                mPopupWindow = PopupWindow(
                    parentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    getScreenHeight() * 2 / 3
                )
            }
        }

        (mWidth == 0 || mHeight == 0).yes {
            mPopupWindow?.contentView?.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
            )
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow?.contentView?.measuredWidth
            mHeight = mPopupWindow?.contentView?.measuredHeight
        }

        (mAnimationStyle != -2).yes {
            (mAnimationStyle == -1).yes {
                mPopupWindow?.animationStyle = R.style.pop_up_window_anim
            }.otherwise {
                mPopupWindow?.animationStyle = mAnimationStyle
            }
        }

        mPopupWindow?.isClippingEnabled = mClippingEnable

        mIgnoreCheekPress.yes {
            mPopupWindow?.setIgnoreCheekPress()
        }

        (mInputMethodMode != -1).yes {
            mPopupWindow?.inputMethodMode = mInputMethodMode
        }

        (mSoftInputMode != -1).yes {
            mPopupWindow?.softInputMode = mSoftInputMode
        }

        (mOnDismissListener != null).yes {
            mPopupWindow?.setOnDismissListener(mOnDismissListener)
        }
        (mOnTouchListener != null).yes {
            mPopupWindow?.setTouchInterceptor(mOnTouchListener)
        }
        mPopupWindow?.isTouchable = mTouchable

        // 添加Dismiss监听
        mPopupWindow?.setOnDismissListener(this)

        // 判断是否点击PopupWindow之外的地方关闭PopupWindow
        // TODO(bichanggen) 现默认开启，关闭的情况下可能会导致页面无法点击
        (!enableOutsideTouchDisMiss).yes {
            // 三个属性必须同时设置，不然不能dismiss，
            // 在Android 4.4上可以，Android6.0以上不行
            mPopupWindow?.isFocusable = true
            mPopupWindow?.isOutsideTouchable = false
            mPopupWindow?.setBackgroundDrawable(null)
            // 注意下面这三个是contentView 不是PopupWindow
            mPopupWindow?.contentView?.isFocusable = true
            mPopupWindow?.contentView?.isFocusableInTouchMode = true
            mPopupWindow?.contentView?.setOnKeyListener { _, keyCode, _ ->
                (keyCode == KeyEvent.KEYCODE_BACK).yes {
                    mPopupWindow?.dismiss()
                    true
                }
                false
            }

            // 在Android 6.0以上 ，只能通过拦截事件来解决
            mPopupWindow?.setTouchInterceptor { v: View?, event: MotionEvent ->
                val x = event.x.toInt()
                val y = event.y.toInt()

                if (event.action == MotionEvent.ACTION_DOWN
                    && (x < 0 || x >= mWidth!! || y < 0 || y >= mHeight!!)
                ) {
                    return@setTouchInterceptor true
                } else if (event.action == MotionEvent.ACTION_OUTSIDE) {
                    return@setTouchInterceptor true
                }
                false
            }
        }.otherwise {
            mPopupWindow?.isFocusable = mIsFocusable
            (backgroundDrawable != null).yes {
                mPopupWindow?.setBackgroundDrawable(backgroundDrawable)
            }.otherwise {
                mPopupWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            mPopupWindow?.isOutsideTouchable = mIsOutsideTouchable
            return
        }
        mPopupWindow?.update()
    }


    override fun onDismiss() {
        dismissPopupWindow()
    }

    /**
     * 设置透明
     */
    fun setBg() {
        // 获取当前Activity的窗口
        val activity = mContentView!!.context as Activity
        if (activity != null && mIsBackgroundDark) {
            // 如果设置的值在0-1的范围内，用设置的值，否则用默认值
            @Suppress("DEPRECATION")
            val alpha =
                if (mBackgroundDarkValue in 1..0) mBackgroundDarkValue else DEFAULT_ALPHA
            mWindow = activity.window
            val params = mWindow?.getAttributes()
            // 不透明度
            params?.alpha = alpha
            // 在某些手机（比如华为）上，PopupWindow背景变暗可能无效，需要使用下面一行代码
            mWindow?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            mWindow?.setAttributes(params)
        }
    }

    /**
     * 关闭PopupWindow
     */
    private fun dismissPopupWindow() {
        mOnDismissListener?.onDismiss()
        (!reserveBackground).yes {
            //如果设置了背景变暗，Dismiss的时候需要还原
            (mWindow != null).yes {
                val params = mWindow?.attributes
                params?.alpha = 1.0f
                mWindow?.attributes = params
            }
        }
        (mPopupWindow != null && mPopupWindow!!.isShowing).yes {
            mPopupWindow?.dismiss()
        }
    }

    fun getPopupWindow(): PopupWindow? {
        return mPopupWindow
    }

    fun isShowing(): Boolean {
        return if (mPopupWindow != null) {
            mPopupWindow!!.isShowing
        } else false
    }

    class PopupWindowBuilder(context: Context?) {
        private val mCustomPopWindow: CustomPopupWindow

        /**
         * 设置PopupWindow的宽高，一般不用设置
         */
        fun size(width: Int, height: Int): PopupWindowBuilder {
            mCustomPopWindow.mWidth = width
            mCustomPopWindow.mHeight = height
            return this
        }

        /**
         * 设置是否获得焦点，默认为true
         */
        fun setFocusable(focusable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsFocusable = focusable
            return this
        }

        /**
         * 传入Layout布局Id，适合不需要处理View中的内容，只展示内容的情况
         */
        fun setView(resLayoutId: Int): PopupWindowBuilder {
            mCustomPopWindow.mResLayoutId = resLayoutId
            return this
        }

        /**
         * 传入View，需要处理View中的内容
         *
         * isAdaptiveHeight，true:表示将传入的View直接设为PopupWindow的ContentView
         * 默认为false，会将传入的View添加到基本视图中
         *
         * 如果isAdaptiveHeight为false，传入的View的XML视图，根布局不要设置background背景颜色
         */
        fun setView(
            view: View?,
            isAdaptiveHeight: Boolean
        ): PopupWindowBuilder {
            mCustomPopWindow.mContentView = view
            mCustomPopWindow.isAdaptiveHeight = isAdaptiveHeight
            return this
        }

        /**
         * 设置当前PopupWindow的外界触碰是否有响应, 默认为true
         */
        fun setOutsideTouchable(outsideTouchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsOutsideTouchable = outsideTouchable
            return this
        }

        /**
         * PopupWindow出现或消失的动画类型，默认有动画效果
         *
         * 设置 -2，则不加入动画效果
         */
        fun setAnimationStyle(animationStyle: Int): PopupWindowBuilder {
            mCustomPopWindow.mAnimationStyle = animationStyle
            return this
        }

        /**
         * 允许弹出窗口超出屏幕范围。默认为允许，窗口会被夹到屏幕边界
         * 设置为false将允许Windows精确定位。
         * 如果这个PopupWindow显示区域不足或者层叠将导致绘制位置计算不准确，需要手动设置为false
         */
        fun setClippingEnable(enable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mClippingEnable = enable
            return this
        }

        /**
         * 设置是否忽略“脸颊触碰”，默认为false，即不忽略
         * 当Events的大小比手指尺寸大时即为CheekPress，常用于打电话时脸颊碰到屏幕的情况
         */
        fun setIgnoreCheekPress(ignoreCheekPress: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIgnoreCheekPress = ignoreCheekPress
            return this
        }

        /**
         * 设置输入法的模式，参数为：INPUT_METHOD_FROM_FOCUSABLE(根据是否可以获得焦点决定),
         * INPUT_METHOD_NEEDED(允许输入法), or INPUT_METHOD_NOT_NEEDED(不允许输入法)。
         *
         * 当PopupWindow中包含EditText时可能会涉及这方面，可能是出现输入法显示在popup window后面，
         * 导致无法输入的问题，可以修改焦点和这里
         */
        fun setInputMethodMode(mode: Int): PopupWindowBuilder {
            mCustomPopWindow.mInputMethodMode = mode
            return this
        }

        /**
         * 一般不需要设置
         */
        fun setOnDissmissListener(
            onDismissListener: PopupWindow.OnDismissListener?
        ): PopupWindowBuilder {
            mCustomPopWindow.mOnDismissListener = onDismissListener
            return this
        }

        /**
         * 设置输入法的操作模式
         */
        fun setSoftInputMode(softInputMode: Int): PopupWindowBuilder {
            mCustomPopWindow.mSoftInputMode = softInputMode
            return this
        }

        /**
         * 设置PopupWindow是否可被触碰，默认可以
         */
        fun setTouchable(touchable: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mTouchable = touchable
            return this
        }

        /**
         * 捕获触摸事件
         */
        fun setTouchIntercepter(touchIntercepter: OnTouchListener?): PopupWindowBuilder {
            mCustomPopWindow.mOnTouchListener = touchIntercepter
            return this
        }

        /**
         * 自定义关闭PopupWindow按钮的点击响应，默认的效果是关闭PopupWindow
         */
        fun setOnClosePopClickListener(closePopClickListener: View.OnClickListener?): PopupWindowBuilder {
            mCustomPopWindow.mOnClosePopClickListener = closePopClickListener
            return this
        }

        /**
         * 弹出PopWindow时，是否允许背景(注意是铺满全屏的透明阴影)是否变暗，默认会变暗
         */
        fun enableBackgroundDark(isDark: Boolean): PopupWindowBuilder {
            mCustomPopWindow.mIsBackgroundDark = isDark
            return this
        }

        /**
         * 设置背景变暗的值, 0-1
         */
        fun setBackgroundDarkValue(darkValue: Float): PopupWindowBuilder {
            mCustomPopWindow.mBackgroundDarkValue = darkValue
            return this
        }

        /**
         * 设置是否可以点击 PopupWindow之外的地方来关闭PopupWindow，默认为true
         * TODO(bichanggen) 有问题，暂时不要设置为false
         */
        fun enableOutSideTouchDismiss(disMiss: Boolean): PopupWindowBuilder {
            mCustomPopWindow.enableOutsideTouchDisMiss = disMiss
            return this
        }

        /**
         * 弹出第二个PopupWindow专用，为了保留第一个PopupWindow的阴影背景，如无必要，不需要设置
         */
        fun reserveBackground(reserve: Boolean): PopupWindowBuilder {
            mCustomPopWindow.reserveBackground = reserve
            return this
        }

        /**
         * 弹出第二个PopupWindow专用，为了设置第二个PopupWindow的阴影效果，如无必要，不需要设置
         */
        fun setBackgroundDrawable(backgroundDrawable: Drawable?): PopupWindowBuilder {
            mCustomPopWindow.backgroundDrawable = backgroundDrawable
            return this
        }

        /**
         * 创建并显示PopupWindow
         */
        fun create(): CustomPopupWindow {
            mCustomPopWindow.build()
            return mCustomPopWindow
        }

        init {
            mCustomPopWindow = CustomPopupWindow(context!!)
        }
    }
}