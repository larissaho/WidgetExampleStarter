package edu.washington.widgetexample

import android.view.View
import android.view.WindowManager

class WidgetTracker(val widget: View, val windowManager: WindowManager) {
    var initialTouchX = 0.0f
    var initialTouchY = 0.0f
    var initialPosition = intArrayOf(0, 0)
    var layoutParams = widget.layoutParams as WindowManager.LayoutParams

    init {
        widget.getLocationOnScreen(initialPosition)
    }

    fun processTouchDown(x: Float, y: Float) {
        initialPosition[0] = layoutParams.x
        initialPosition[1] = layoutParams.y
        initialTouchX = x
        initialTouchY = y
    }

    fun processMove(x: Float, y: Float) {
        layoutParams.x = initialPosition[0] + (x - initialTouchX).toInt()
        layoutParams.y = initialPosition[1] + (y - initialTouchY).toInt()
        windowManager.updateViewLayout(widget, layoutParams)
    }

    fun wasTapped(x: Float, y: Float): Boolean {
        val delta = 10
        return ((x - initialTouchX).toInt() < delta && (y - initialTouchY).toInt() < delta)
    }
}