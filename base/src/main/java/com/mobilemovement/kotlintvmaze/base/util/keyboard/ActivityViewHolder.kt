package com.mobilemovement.kotlintvmaze.base.util.keyboard

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window

data class ActivityViewHolder(
    val nonResizableLayout: ViewGroup,
    val resizableLayout: ViewGroup,
    val contentView: ViewGroup
) {
    fun onDetach(onDetach: () -> Unit) {
        nonResizableLayout.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                onDetach()
            }

            override fun onViewAttachedToWindow(v: View?) {}
        })
    }

    companion object {
        fun createFrom(activity: Activity): ActivityViewHolder {
            val decorView = activity.window.decorView as ViewGroup
            val contentView = decorView.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
            val actionBarRootLayout = contentView.parent as ViewGroup
            val resizableLayout = actionBarRootLayout.parent as ViewGroup

            return ActivityViewHolder(
                nonResizableLayout = decorView,
                resizableLayout = resizableLayout,
                contentView = contentView
            )
        }
    }
}
