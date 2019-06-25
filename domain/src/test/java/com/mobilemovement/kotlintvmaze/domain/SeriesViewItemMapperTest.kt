package com.mobilemovement.kotlintvmaze.domain

import com.google.common.truth.Truth
import com.mobilemovement.kotlintvmaze.data.Series
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class SeriesViewItemMapperTest {

    @Mock
    private lateinit var seriesViewItemMapper: SeriesViewItemMapper

    lateinit var fakeSeries: Series

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fakeSeries = Series(1.2, null)
    }

    @Test
    fun `series_domain_mapper_should_map_raw_data_to_series`() {
        val fakeSeriesViewItem = SeriesViewItem(null)
        Mockito.`when`(seriesViewItemMapper.map(fakeSeries)).thenReturn(fakeSeriesViewItem)

        val expected = SeriesViewItem(null)

        Truth.assertThat(expected).isEqualTo(seriesViewItemMapper.map(fakeSeries))
    }

    @After
    fun tearDown() {
        // no-op
    }
}
