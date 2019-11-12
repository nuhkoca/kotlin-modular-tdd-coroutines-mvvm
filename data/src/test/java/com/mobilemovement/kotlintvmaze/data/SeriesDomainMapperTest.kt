package com.mobilemovement.kotlintvmaze.data

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Before
import org.junit.Test

class SeriesDomainMapperTest {

    @Before
    fun setUp() {
        // no-op
    }

    @Test
    fun `SeriesViewItemMapper maps raw data to Series`() {
        val mapper = mockk<SeriesDomainMapper>()

        val slot = slot<SeriesRaw>()

        every { mapper.invoke(capture(slot)) } returns
            Series(
                10.0,
                Show(
                    1,
                    "testname",
                    "testurl",
                    Image("testoriginal"), "testsummary"
                )
            )

        val invocation = mapper.invoke(
            SeriesRaw(
                10.0,
                ShowRaw(
                    1,
                    "testurl",
                    "testname",
                    "testype",
                    "testlanguage",
                    ImageRaw(
                        "testmedium",
                        "testoriginal"
                    ), "testsummary"
                )
            )
        )

        Truth.assertThat(invocation).isNotNull()
        Truth.assertThat(invocation).isEqualTo(
            Series(
                10.0,
                Show(
                    1,
                    "testname",
                    "testurl",
                    Image("testoriginal"), "testsummary"
                )
            )
        )
        Truth.assertThat(slot.captured).isEqualTo(
            SeriesRaw(
                10.0,
                ShowRaw(
                    1,
                    "testurl",
                    "testname",
                    "testype",
                    "testlanguage",
                    ImageRaw(
                        "testmedium",
                        "testoriginal"
                    ), "testsummary"
                )
            )
        )
    }

    @After
    fun tearDown() {
        // no-op
    }
}
