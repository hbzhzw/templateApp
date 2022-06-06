package io.github.hbzhzw.utils.system

import io.github.hbzhzw.utils.AppContext

object ScreenUtil {
    fun dpToPx(dp: Int): Int {
        return (AppContext.context.resources.displayMetrics.density * dp + .5f).toInt()
    }

    fun pxToDp(pixel: Int): Int {
        return ((pixel.toFloat() / AppContext.context.resources.displayMetrics.density) + 0.5f).toInt()
    }
}