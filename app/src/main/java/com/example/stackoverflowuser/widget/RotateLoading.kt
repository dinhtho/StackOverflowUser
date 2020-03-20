package com.example.stackoverflowuser.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.stackoverflowuser.R

class RotateLoading : View {

    private var mPaint: Paint? = null

    private var loadingRectF: RectF? = null
    private var shadowRectF: RectF? = null

    private var topDegree = 10
    private var bottomDegree = 190

    private var arc: Float = 0.toFloat()

    private var viewWidth: Int = 0

    private var changeBigger = true

    private var shadowPosition: Int = 0

    var isStart = false
        private set

    private var color: Int = 0

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        color = Color.WHITE
        viewWidth = dpToPx(context, DEFAULT_WIDTH.toFloat())
        shadowPosition = dpToPx(getContext(), DEFAULT_SHADOW_POSITION.toFloat())

        if (null != attrs) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateLoading)
            color = typedArray.getColor(R.styleable.RotateLoading_loading_color, Color.WHITE)
            viewWidth = typedArray
                .getDimensionPixelSize(
                    R.styleable.RotateLoading_loading_width,
                    dpToPx(context, DEFAULT_WIDTH.toFloat())
                )
            shadowPosition = typedArray.getInt(R.styleable.RotateLoading_shadow_position, DEFAULT_SHADOW_POSITION)
            typedArray.recycle()
        }

        mPaint = Paint()
        mPaint!!.color = color
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = viewWidth.toFloat()
        mPaint!!.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        arc = 10f

        loadingRectF =
            RectF((2 * viewWidth).toFloat(), (2 * viewWidth).toFloat(), (w - 2 * viewWidth).toFloat(), (h - 2 * viewWidth).toFloat())
        shadowRectF = RectF(
            (2 * viewWidth + shadowPosition).toFloat(),
            (2 * viewWidth + shadowPosition).toFloat(),
            (w - 2 * viewWidth + shadowPosition).toFloat(),
            (h - 2 * viewWidth + shadowPosition).toFloat()
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!isStart) {
            return
        }

        mPaint!!.color = Color.parseColor("#1a000000")
        canvas.drawArc(shadowRectF!!, topDegree.toFloat(), arc, false, mPaint!!)
        canvas.drawArc(shadowRectF!!, bottomDegree.toFloat(), arc, false, mPaint!!)

        mPaint!!.color = color
        canvas.drawArc(loadingRectF!!, topDegree.toFloat(), arc, false, mPaint!!)
        canvas.drawArc(loadingRectF!!, bottomDegree.toFloat(), arc, false, mPaint!!)

        topDegree += 10
        bottomDegree += 10
        if (topDegree > 360) {
            topDegree -= 360
        }
        if (bottomDegree > 360) {
            bottomDegree -= 360
        }

        if (changeBigger) {
            if (arc < 160) {
                arc += 2.5f
                invalidate()
            }
        } else {
            if (arc > 10) {
                arc -= 5f
                invalidate()
            }
        }
        if (arc == 160f || arc == 10f) {
            changeBigger = !changeBigger
            invalidate()
        }
    }

    fun start() {
        startAnimator()
        isStart = true
        invalidate()
    }

    fun stop() {
        stopAnimator()
        invalidate()
    }

    private fun startAnimator() {
        val scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0.0f, 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0.0f, 1f)
        scaleXAnimator.duration = 300
        scaleXAnimator.interpolator = LinearInterpolator()
        scaleYAnimator.duration = 300
        scaleYAnimator.interpolator = LinearInterpolator()
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator)
        animatorSet.start()
    }

    private fun stopAnimator() {
        val scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0f)
        val scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0f)
        scaleXAnimator.duration = 300
        scaleXAnimator.interpolator = LinearInterpolator()
        scaleYAnimator.duration = 300
        scaleYAnimator.interpolator = LinearInterpolator()
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                isStart = false
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
        animatorSet.start()
    }


    private fun dpToPx(context: Context, dpVal: Float): Int {
        return TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
    }

    companion object {

        private const val DEFAULT_WIDTH = 6
        private const val DEFAULT_SHADOW_POSITION = 2
    }

}
