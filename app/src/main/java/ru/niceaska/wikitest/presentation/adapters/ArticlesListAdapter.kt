package ru.niceaska.wikitest.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.niceaska.wikitest.presentation.activities.ShowImageTitlesListener
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

/**
 * [ListAdapter] для списка статей Википедии
 *
 * @constructor
 * @property listener слушатель нажатий для отображения списка картинок статьи
 */
class ArticlesListAdapter(private val listener: ShowImageTitlesListener) :
    ListAdapter<WikiGeoPagePresentation, ArticlesListAdapter.ArticlesViewHolder>(
        ArticleDiffUtilCallback(),
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ArticlesViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ArticlesViewHolder(
        private val listener: ShowImageTitlesListener,
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(android.R.id.text1)
        fun bind(data: WikiGeoPagePresentation) {
            textView.text = data.title
            textView.setOnClickListener { _ ->
                listener.showTitles(data.id)
            }
        }
    }
}