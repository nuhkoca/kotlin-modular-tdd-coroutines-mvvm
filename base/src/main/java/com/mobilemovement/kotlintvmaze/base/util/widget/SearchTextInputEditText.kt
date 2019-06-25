package com.mobilemovement.kotlintvmaze.base.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import com.google.android.material.textfield.TextInputEditText
import com.mobilemovement.kotlintvmaze.base.util.ext.hideKeyBoard

class SearchTextInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var onActionTriggered: (query: String) -> Unit = {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setup()
    }

    private fun setup() {
        isFocusable = true
        isFocusableInTouchMode = true
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_DONE || actionId == IME_ACTION_NEXT || actionId == IME_ACTION_SEARCH) {
                clearFocus()
                if (text.toString().isNotEmpty()) {
                    onActionTriggered.invoke(text.toString().toLowerCase())
                }
                return@setOnEditorActionListener true
            }
            false
        }
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus.not()) {
                clearFocus()
                hideKeyBoard()
            }
        }
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK && event?.action == ACTION_UP) {
            clearFocus()
            return false
        }
        return super.dispatchKeyEvent(event)
    }

    fun doOnAction(onActionTriggered: (query: String) -> Unit = {}) {
        this.onActionTriggered = onActionTriggered
    }
}
