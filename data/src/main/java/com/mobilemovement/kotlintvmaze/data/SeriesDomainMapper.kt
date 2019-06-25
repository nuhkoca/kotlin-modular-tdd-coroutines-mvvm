package com.mobilemovement.kotlintvmaze.data

import com.mobilemovement.kotlintvmaze.base.util.mapper.Mapper
import com.mobilemovement.kotlintvmaze.domain.Image
import com.mobilemovement.kotlintvmaze.domain.Series
import com.mobilemovement.kotlintvmaze.domain.SeriesRaw
import com.mobilemovement.kotlintvmaze.domain.Show
import javax.inject.Inject

class SeriesDomainMapper @Inject constructor() : Mapper<SeriesRaw, Series> {
    override fun map(input: SeriesRaw) = with(input) {
        val image = Image(show?.image?.original)
        val show = Show(show?.id, show?.url, show?.name, image, show?.summary)

        Series(score, show)
    }
}
