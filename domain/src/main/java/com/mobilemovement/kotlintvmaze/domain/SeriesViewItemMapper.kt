package com.mobilemovement.kotlintvmaze.domain

import com.mobilemovement.kotlintvmaze.base.util.mapper.Mapper
import javax.inject.Inject

class SeriesViewItemMapper @Inject constructor() : Mapper<Series, SeriesViewItem> {
    override fun map(input: Series) = with(input) {
        val image = ImageViewItem(show?.image?.original)
        val show = ShowViewItem(show?.name, image, show?.summary)

        SeriesViewItem(show)
    }
}
