package com.kuliao.kuliaojk.weight

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.kuliao.kuliaojk.R
import kotlin.properties.Delegates

/**
 * Author: WangHao
 * Created On: 2020/06/19  17:21
 * Description:
 */
class CircleProgressbar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    //外部轮廓的颜色
    private var outLineColor = Color.WHITE

    //外部轮廓的宽度
    private var outLineWidth = 4

    //进度条的宽度
    private var progressLineWidth = 8

    //进度条的颜色
    private var progressLineColor = Color.BLUE

    //进度
    private var progress = 100

    //画笔
    private val mPaint = Paint()

    //进度条的矩形区域
    private val mArcRect = RectF()

    //内部圆的颜色
    private var inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT)

    //中心圆的颜色
    private var circleColor by Delegates.notNull<Int>()

    //进度条类型
    private var mProgressType: ProgressType = ProgressType.COUNT_BACK

    //进度倒计时时间
    private var timeMillis: Long = 3000

    //进度条通知。
    private var mCountdownProgressListener: OnCountdownProgressListener? = null
    private var listenerWhat = 0

    //View的显示区域。
    private val bounds = Rect()

    constructor(context: Context) : this(context, attrs = null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, defStyleAttr = 0)

    init {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        progressLineColor = ContextCompat.getColor(context, R.color.hot_search)
        mPaint.isAntiAlias = true
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SplashCircleProgress)
        val typedArrayValue = typedArray.hasValue(R.styleable.SplashCircleProgress_circle_color)
        inCircleColors = if (typedArrayValue) {
            typedArray.getColorStateList(R.styleable.SplashCircleProgress_circle_color)!!
        } else {
            ColorStateList.valueOf(Color.TRANSPARENT)
        }
        circleColor = inCircleColors.getColorForState(drawableState, Color.TRANSPARENT)
        typedArray.recycle()
    }

    /**
     * 设置外部轮廓圆的颜色
     */
    fun setOutLineColor(@ColorInt outLineColor: Int) {
        this.outLineColor = outLineColor
        invalidate()
    }

    /**
     * 设置外部轮廓圆的宽度
     */
    fun setOutLineWidth(@ColorInt outLineWidth: Int) {
        this.outLineWidth = outLineWidth
        invalidate()
    }

    /**
     * 设置中心圆的颜色
     */
    fun setInCircleColor(@ColorInt inCircleColor: Int) {
        this.inCircleColors = ColorStateList.valueOf(inCircleColor)
        invalidate()
    }

    private fun validateCircleColor() {
        val circleColorTemp = inCircleColors.getColorForState(drawableState, Color.TRANSPARENT)
        if (circleColor != circleColorTemp) {
            circleColor = circleColorTemp
            invalidate()
        }
    }

    /**
     * 设置圆形进度条颜色
     */
    fun setProgressColor(@ColorInt progressLineColor: Int) {
        this.progressLineColor = progressLineColor
        invalidate()
    }

    /**
     * 设置圆形进度条宽度
     */
    fun setProgressLineWidth(progressLineWidth: Int) {
        this.progressLineWidth = progressLineWidth
        invalidate()
    }

    /**
     * 设置进度条值
     */
    fun setProgress(progress: Int) {
        this.progress = validateProgress(progress)
        invalidate()
    }

    private fun validateProgress(progress: Int) = when (progress > 100) {
        true -> 100
        else -> if (progress < 0) {
            0
        } else {
            progress
        }
    }

    /**
     * 获取进度值
     */
    fun getProgress(): Int {
        return progress
    }

    /**
     * 设置倒计时时间
     */
    fun setTimeMillis(timeMillis: Long) {
        this.timeMillis = timeMillis
        invalidate()
    }

    /**
     * 获取倒计时时间
     */
    fun getTimeMillis(): Long {
        return this.timeMillis
    }

    /**
     * 设置进度条类型  是0-100 还是100_0
     */
    fun setProgressType(progressType: ProgressType) {
        this.mProgressType = progressType
        resetProgress()
        invalidate()
    }

    private fun resetProgress() {
        progress = when (mProgressType) {
            ProgressType.COUNT -> 0
            ProgressType.COUNT_BACK -> 100
        }
    }

    /**
     * 获取进度条类型
     */
    fun getProgressType(): ProgressType? {
        return mProgressType
    }

    /**
     * 设置进度监听
     */
    fun setCountdownProgressListener(
        what: Int,
        mCountdownProgressListener: OnCountdownProgressListener
    ) {
        this.listenerWhat = what
        this.mCountdownProgressListener = mCountdownProgressListener
    }

    fun start() {
        stop()
        post(progressChangeTask)
    }

    /**
     * 开始旋转倒计时
     */
    fun reStart() {
        resetProgress()
        start()
    }

    private fun stop() {
        removeCallbacks(progressChangeTask)
    }


    private val progressChangeTask: Runnable = object : Runnable {
        override fun run() {
            removeCallbacks(this)
            when (mProgressType) {
                ProgressType.COUNT -> progress += 1
                ProgressType.COUNT_BACK -> progress -= 1
            }
            when (progress in 0..100) {
                true -> {
                    mCountdownProgressListener?.onProgress(listenerWhat, progress)
                    invalidate()
                    postDelayed(this, timeMillis / 100)
                }
                else -> progress = validateProgress(progress)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        //获取view的边界
        getDrawingRect(bounds)
        val size: Int = if (bounds.height() > bounds.width()) bounds.width() else bounds.height()
        val outerRadius = size / 2.toFloat()

        //画内部背景
        val circleColor = inCircleColors.getColorForState(drawableState, 0)

        mPaint.style = Paint.Style.FILL
        mPaint.color = circleColor
        canvas.drawCircle(
            bounds.centerX().toFloat(),
            bounds.centerY().toFloat(),
            outerRadius - outLineWidth,
            mPaint
        )

        //画边框圆
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = outLineWidth.toFloat()
        mPaint.color = outLineColor
        canvas.drawCircle(
            bounds.centerX().toFloat(), bounds.centerY().toFloat(), outerRadius - outLineWidth / 2,
            mPaint
        )

        //画字
        val paint: Paint = paint
        paint.color = currentTextColor
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.CENTER
        val textY: Float = bounds.centerY() - (paint.descent() + paint.ascent()) / 2
        canvas.drawText(text.toString(), bounds.centerX().toFloat(), textY, paint)

        //画进度条
        mPaint.color = progressLineColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = progressLineWidth.toFloat()
        // mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.isAntiAlias = true
        val deleteWidth = progressLineWidth
        mArcRect[bounds.left + deleteWidth / 2.toFloat(), bounds.top + deleteWidth / 2.toFloat(), bounds.right - deleteWidth / 2.toFloat()] =
            bounds.bottom - deleteWidth / 2.toFloat()
        canvas.drawArc(mArcRect, -90f, -360 * progress / 100.toFloat(), false, mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val lineWidth = 4 * (outLineWidth + progressLineWidth)
        val width = measuredWidth
        val height = measuredHeight
        val size = (if (width > height) width else height) + lineWidth
        setMeasuredDimension(size, size)
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        validateCircleColor()
    }

    enum class ProgressType {
        /**
         * 顺数进度条，从0-100；
         */
        COUNT,

        /**
         * 倒数进度条，从100-0；
         */
        COUNT_BACK
    }

    interface OnCountdownProgressListener {
        fun onProgress(what: Int, progress: Int)
    }
}



