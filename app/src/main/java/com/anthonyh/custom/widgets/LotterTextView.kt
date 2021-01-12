package com.anthonyh.custom.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.anthonyh.lotteryproject.R

/**
created by AnthonyH
createDate: 2020/9/30 0030
 */
class LotterTextView : AppCompatTextView {

    private val TAG = "LotterTextView"
    var backHightLightColor: Int = Color.YELLOW
        set(value) {
            backPaint.color = value
        }

    private var backPaint: Paint

    private var textHightLightColor: Int = Color.WHITE

    private var textGrayColor: Int = Color.GRAY

    private val SIZE_INTERVAL = 5

    private val TEXT_SIZE_STEP = 1f

    /**
     *textSize，单位px。
     */
    private var realTextSize: Float = 10f

    /**
     * 背景圆的半径大小。单位px
     */
    private var backCircleR: Float = 20f

    private var totalTextParentLength = 0f

    /**
     * 标记是否被选中
     */
    var isBackHightLight: Boolean = false

    /**
     * 因为最大是两位数显示，所以直接拿一个特定的两位数测量，计算合适的textSize以避免一位和两位显示字号有差别
     */
    private val TEXT_SAIMPLE = "88"


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        //获取自定义属性
        //获取默认textSize，单位px
        realTextSize = textSize
        backPaint = Paint()
        backPaint.isAntiAlias = true
        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.LotterTextViewP)
        typeArray?.run {
            textGrayColor = getColor(R.styleable.LotterTextViewP_text_gray, textGrayColor)
            recycle()
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calBackCircleR()
        setTextSize(TypedValue.COMPLEX_UNIT_PX, calTextSize(TEXT_SAIMPLE))
    }

    override fun onDraw(canvas: Canvas?) {
        //在super的绘制前先绘制一个圆形的背景底色，圆形直径为文字内容的1.2倍
        if (isBackHightLight) {
            drawBackCircle(canvas)
            //选中的颜色
            setTextColor(textHightLightColor)
        } else {
            setTextColor(textGrayColor)
        }
        super.onDraw(canvas)
    }

    private fun drawBackCircle(canvas: Canvas?) {
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), backCircleR, backPaint)
    }

    /**
     * 文字总长度为背景圆的5/7
     */
    private fun calTextSize(text: String): Float {
        //这是文字一共可以占用的长度
        if (paint.measureText(text) > totalTextParentLength) {
            //循环取出一个合适的文字size
            while (paint.measureText(text) - totalTextParentLength > SIZE_INTERVAL) {
                realTextSize -= TEXT_SIZE_STEP
                paint.textSize = realTextSize
            }
        } else if (paint.measureText(text) < totalTextParentLength) {
            while (totalTextParentLength - paint.measureText(text) > SIZE_INTERVAL) {
                realTextSize += TEXT_SIZE_STEP
                paint.textSize = realTextSize
            }
        }
        return realTextSize
    }

    /**
     * 圆的直径大概占总长度的5/6
     */
    private fun calBackCircleR() {
        backCircleR = width.toFloat() * 5 / 8 / 2
        totalTextParentLength = 2 * backCircleR * 5 / 7
    }

}