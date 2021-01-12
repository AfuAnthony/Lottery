package com.anthonyh.lotteryproject.common.util

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.widget.TextView

/**
created by AnthonyH
createDate: 2020/9/21 0021
 */
fun Context.dp2px(dp: Int): Int {
    return (dp * resources.displayMetrics.density + 0.5f).toInt()
}


// context.getResources().getDisplayMetrics().scaledDensity
//spValue * fontScale + 0.5f
fun Context.px2sp(px: Int): Int {
    return (px / getResources().getDisplayMetrics().scaledDensity + 0.5f).toInt()
}

fun Context.getDimesion(id: Int): Float {
    return resources.getDimension(id)
}

fun TextView.setProperty(
    textSizeSpDimension: Int,
    backColorId: Int,
    textColorId: Int,
    gravity: Int,
    textId: Int
): TextView {
    return apply {
        this.text = context.resources.getText(textId)
        this.gravity = gravity
        setTextColor(context.getColor(textColorId))
        setBackgroundColor(context.getColor(backColorId))
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.getDimesion(textSizeSpDimension)
        )
    }
}

fun TextView.setProperty(
    textSizeSpDimension: Int,
    backColorId: Int,
    textColorId: Int,
    gravity: Int,
    text: String
): TextView {
    return this.apply {
        this.text = text
        this.gravity = gravity
        setTextColor(context.getColor(textColorId))
        setBackgroundColor(context.getColor(backColorId))
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.getDimesion(textSizeSpDimension)
        )
    }
}

fun Activity.getTag(): Activity? {
    return this?.apply { } ?: null
}