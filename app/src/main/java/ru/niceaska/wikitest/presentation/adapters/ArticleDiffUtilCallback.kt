package ru.niceaska.wikitest.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

/**
 * [DiffUtil.ItemCallback] для списка статей Википедии
 */
class ArticleDiffUtilCallback : DiffUtil.ItemCallback<WikiGeoPagePresentation>() {

    override fun areItemsTheSame(
        oldItem: WikiGeoPagePresentation,
        newItem: WikiGeoPagePresentation
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: WikiGeoPagePresentation,
        newItem: WikiGeoPagePresentation
    ): Boolean = oldItem == newItem

}