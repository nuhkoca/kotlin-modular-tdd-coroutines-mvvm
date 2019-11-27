/*
 * Copyright 2019 nuhkoca.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobilemovement.kotlintvmaze.base.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import com.google.android.material.textfield.TextInputEditText
import com.mobilemovement.kotlintvmaze.base.util.ext.hideKeyBoard
import java.util.Locale

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
            if (actionId == IME_ACTION_DONE ||
                actionId == IME_ACTION_NEXT ||
                actionId == IME_ACTION_SEARCH
            ) {
                handleAction()
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
        setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                handleAction()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK && event?.action == ACTION_UP) {
            clearFocus()
            return false
        }
        return super.dispatchKeyEvent(event)
    }

    private fun handleAction() {
        clearFocus()
        if (text.toString().isNotEmpty()) {
            onActionTriggered.invoke(text.toString().toLowerCase(Locale.getDefault()))
        }
    }

    fun doOnAction(onActionTriggered: (query: String) -> Unit = {}) {
        this.onActionTriggered = onActionTriggered
    }
}
