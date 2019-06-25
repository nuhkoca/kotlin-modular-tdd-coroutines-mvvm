package com.mobilemovement.kotlintvmaze.domain

import com.mobilemovement.kotlintvmaze.base.util.mapper.Mapper
import com.mobilemovement.kotlintvmaze.data.ImageViewItem
import com.mobilemovement.kotlintvmaze.data.Series
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.data.ShowViewItem
import javax.inject.Inject

class SeriesViewItemMapper @Inject constructor() : Mapper<Series, SeriesViewItem> {
    override fun map(input: Series) = with(input) {
        val image = ImageViewItem(show?.image?.original)
        val show = ShowViewItem(show?.name, image, show?.summary)

        SeriesViewItem(show)
    }
}
