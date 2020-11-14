package ru.niceaska.wikitest.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.niceaska.wikitest.R
import ru.niceaska.wikitest.presentation.activities.ImageTitlesViewer
import ru.niceaska.wikitest.presentation.activities.ShowImageTitlesListener
import ru.niceaska.wikitest.presentation.adapters.ArticlesListAdapter
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

/**
 * [Fragment] для отображения списка заголовков статей
 */
class ListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler)
        swipeRefreshLayout = view.findViewById(R.id.swipetorefresh)
        recyclerView?.adapter = ArticlesListAdapter(object : ShowImageTitlesListener {
            override fun showTitles(id: Long) {
                val mainActivity = activity
                if (mainActivity is ImageTitlesViewer) {
                    mainActivity.showImageTitles(id)
                }
            }
        }).apply {
            submitList(arguments?.getParcelableArrayList(LIST))
        }
        swipeRefreshLayout?.setOnRefreshListener {
            val mainActivity = activity
            if (mainActivity is SwipeRefreshLayout.OnRefreshListener) {
                mainActivity.onRefresh()
            }
            swipeRefreshLayout?.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
        swipeRefreshLayout = null
    }

    companion object {
        private const val LIST = "list"

        /**
         * Создает новый инстанс [ListFragment]
         *
         * @param list лист статей Википедии
         */
        fun newInstance(list: List<WikiGeoPagePresentation>): Fragment {
            val listFragment = ListFragment()
            val bundle: Bundle = Bundle().apply {
                putParcelableArrayList(LIST, ArrayList<WikiGeoPagePresentation>(list))
            }
            listFragment.arguments = bundle
            return listFragment
        }
    }
}