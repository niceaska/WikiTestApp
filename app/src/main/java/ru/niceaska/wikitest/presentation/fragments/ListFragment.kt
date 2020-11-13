package ru.niceaska.wikitest.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.niceaska.wikitest.R
import ru.niceaska.wikitest.presentation.adapters.ArticlesListAdapter
import ru.niceaska.wikitest.presentation.models.WikiGeoPagePresentation

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.adapter = ArticlesListAdapter().apply {
            submitList(arguments?.getParcelableArrayList("list"))
        }

    }

    companion object {
        fun newInstance(list: List<WikiGeoPagePresentation>): Fragment {
            val listFragment = ListFragment()
            val bundle: Bundle = Bundle().apply {
                putParcelableArrayList("list", ArrayList<WikiGeoPagePresentation>(list))
            }
            listFragment.arguments = bundle
            return listFragment
        }
    }
}