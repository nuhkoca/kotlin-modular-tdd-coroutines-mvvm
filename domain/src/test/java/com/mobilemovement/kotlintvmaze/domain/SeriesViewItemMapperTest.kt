package com.mobilemovement.kotlintvmaze.domain

import com.google.common.truth.Truth.assertThat
import com.mobilemovement.kotlintvmaze.data.Image
import com.mobilemovement.kotlintvmaze.data.ImageViewItem
import com.mobilemovement.kotlintvmaze.data.Series
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.data.Show
import com.mobilemovement.kotlintvmaze.data.ShowViewItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Before
import org.junit.Test

class SeriesViewItemMapperTest {

    @Before
    fun setUp() {
        // no-op
    }

    @Test
    fun `SeriesViewItemMapper maps raw data to Series`() {
        val mapper = mockk<SeriesViewItemMapper>()

        val slot = slot<Series>()

        every { mapper.invoke(capture(slot)) } returns
            SeriesViewItem(
                ShowViewItem(
                    "testname",
                    ImageViewItem("testoriginal"),
                    "testsummary"
                )
            )

        val invocation = mapper.invoke(
            Series(
                10.0,
                Show(
                    1,
                    "testurl",
                    "testname",
                    Image("testoriginal"),
                    "testsummary"
                )
            )
        )

        assertThat(invocation).isNotNull()
        assertThat(invocation).isEqualTo(
            SeriesViewItem(
                ShowViewItem(
                    "testname",
                    ImageViewItem("testoriginal"),
                    "testsummary"
                )
            )
        )
        assertThat(slot.captured).isEqualTo(
            Series(
                10.0,
                Show(
                    1,
                    "testurl",
                    "testname",
                    Image("testoriginal"),
                    "testsummary"
                )
            )
        )
    }

    @After
    fun tearDown() {
        // no-op
    }
}
