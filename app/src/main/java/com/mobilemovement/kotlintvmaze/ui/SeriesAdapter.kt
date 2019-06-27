package com.mobilemovement.kotlintvmaze.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobilemovement.kotlintvmaze.base.BaseViewHolder
import com.mobilemovement.kotlintvmaze.base.util.ext.fromHtml
import com.mobilemovement.kotlintvmaze.base.util.ext.inflater
import com.mobilemovement.kotlintvmaze.data.SeriesViewItem
import com.mobilemovement.kotlintvmaze.databinding.LayoutSeriesRowItemBinding
import com.mobilemovement.kotlintvmaze.ui.SeriesAdapter.SeriesViewHolder
import javax.inject.Inject

class SeriesAdapter @Inject constructor() : RecyclerView.Adapter<SeriesViewHolder>() {

    var series = mutableListOf<SeriesViewItem>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view = LayoutSeriesRowItemBinding.inflate(parent.context.inflater)
        return SeriesViewHolder(view.root)
    }

    override fun getItemCount() = series.size

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val item = series[position]
        holder.bindTo(item)
    }

    inner class SeriesViewHolder(itemView: View) :
        BaseViewHolder<LayoutSeriesRowItemBinding, SeriesViewItem>(itemView) {
        override fun bindTo(item: SeriesViewItem) {
            with(item) {
                dataBinding?.apply {
                    tvName.text = show?.name
                    tvSummary.text = show?.summary?.fromHtml()

                    imageUrl = show?.image?.original
                    executePendingBindings()
                }
            }
        }
    }
}
