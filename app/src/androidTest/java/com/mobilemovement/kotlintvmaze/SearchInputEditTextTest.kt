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
    fun `SearchInputEditText has focus at first start`() {
        searchTextInputEditText.requestFocus()
        onView(allOf(isAssignableFrom(TextInputEditText::class.java))).check(matches(hasFocus()))
    }

    @Test
    fun `SearchInputEditText does not have any focus`() {
        searchTextInputEditText.clearFocus()
        onView(allOf(isAssignableFrom(TextInputEditText::class.java))).check(matches(hasFocus()))
    }
}
