package com.mobilemovement.kotlintvmaze.data

import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class SeriesDomainMapperTest {

    @Mock
    private lateinit var seriesDomainMapper: SeriesDomainMapper

    lateinit var fakeSeriesRaw: SeriesRaw

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fakeSeriesRaw = SeriesRaw(1.2, null)
    }

    @Test
    fun `series_domain_mapper_should_map_raw_data_to_series`() {
        val fakeSeries = Series(1.2, null)
        Mockito.`when`(seriesDomainMapper.map(fakeSeriesRaw)).thenReturn(fakeSeries)

        val expected = Series(1.2, null)

        Truth.assertThat(expected).isEqualTo(seriesDomainMapper.map(fakeSeriesRaw))
    }

    @After
    fun tearDown() {
        // no-op
    }
}
