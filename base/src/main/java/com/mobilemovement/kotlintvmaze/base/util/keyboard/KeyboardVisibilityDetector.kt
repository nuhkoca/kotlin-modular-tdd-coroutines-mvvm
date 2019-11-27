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
package com.mobilemovement.kotlintvmaze.base.util.keyboard

import android.view.ViewTreeObserver

object KeyboardVisibilityDetector {

    fun listen(viewHolder: ActivityViewHolder, listener: (KeyboardVisibilityChanged) -> Unit) {
        val detector = Detector(viewHolder, listener)
        viewHolder.nonResizableLayout.viewTreeObserver.addOnPreDrawListener(detector)
        viewHolder.onDetach {
            viewHolder.nonResizableLayout.viewTreeObserver.removeOnPreDrawListener(detector)
        }
    }

    private class Detector(
        val viewHolder: ActivityViewHolder,
        val listener: (KeyboardVisibilityChanged) -> Unit
    ) : ViewTreeObserver.OnPreDrawListener {

        private var previousHeight: Int = -1

        override fun onPreDraw(): Boolean {
            val detected = detect()

            return detected.not()
        }

        private fun detect(): Boolean {
            val contentHeight = viewHolder.resizableLayout.height
            if (contentHeight == previousHeight) {
                return false
            }

            if (previousHeight != -1) {
                val statusBarHeight = viewHolder.resizableLayout.top
                val isKeyboardVisible =
                    contentHeight < viewHolder.nonResizableLayout.height - statusBarHeight

                listener(
                    KeyboardVisibilityChanged(
                        visible = isKeyboardVisible,
                        contentHeight = contentHeight,
                        contentHeightBeforeResize = previousHeight
                    )
                )
            }

            previousHeight = contentHeight
            return true
        }
    }
}
