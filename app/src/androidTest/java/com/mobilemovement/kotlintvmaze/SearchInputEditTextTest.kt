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
package com.mobilemovement.kotlintvmaze

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasFocus
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.android.material.textfield.TextInputEditText
import com.mobilemovement.kotlintvmaze.base.util.widget.SearchTextInputEditText
import com.mobilemovement.kotlintvmaze.ui.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("IllegalIdentifier")
@RunWith(AndroidJUnit4::class)
class SearchInputEditTextTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var searchTextInputEditText: SearchTextInputEditText

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        searchTextInputEditText = SearchTextInputEditText(context)
    }

    @Test
    fun searchInputEditText_hasFocus_atFirstStart() {
        searchTextInputEditText.requestFocus()
        onView(allOf(isAssignableFrom(TextInputEditText::class.java))).check(matches(hasFocus()))
    }

    @Test
    fun searchInputEditText_doesNot_have_anyFocus() {
        searchTextInputEditText.clearFocus()
        onView(allOf(isAssignableFrom(TextInputEditText::class.java))).check(matches(hasFocus()))
    }
}
