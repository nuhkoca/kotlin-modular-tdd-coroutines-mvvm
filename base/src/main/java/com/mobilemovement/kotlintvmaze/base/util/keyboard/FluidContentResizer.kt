package com.mobilemovement.kotlintvmaze.base.util.keyboard

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

object FluidContentResizer {

    private const val DURATION_DEFAULT = 300L
    private var heightAnimator: ValueAnimator = ObjectAnimator()

    fun listen(activity: Activity) {
        val viewHolder = ActivityViewHolder.createFrom(activity)

        KeyboardVisibilityDetector.listen(viewHolder) {
            animateHeight(viewHolder, it)
        }
        viewHolder.onDetach {
            heightAnimator.cancel()
            heightAnimator.removeAllUpdateListeners()
        }
    }

    private fun animateHeight(viewHolder: ActivityViewHolder, event: KeyboardVisibilityChanged) {
        val contentView = viewHolder.contentView
        contentView.setHeight(event.contentHeightBeforeResize)

        heightAnimator.cancel()

        heightAnimator =
            ObjectAnimator.ofInt(event.contentHeightBeforeResize, event.contentHeight).apply {
                interpolator = FastOutSlowInInterpolator()
                duration = DURATION_DEFAULT
            }
        heightAnimator.addUpdateListener { contentView.setHeight(it.animatedValue as Int) }
        heightAnimator.start()
    }

    private fun View.setHeight(height: Int) {
        val params = layoutParams
        params.height = height
        layoutParams = params
    }
}
