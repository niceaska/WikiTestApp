package ru.niceaska.wikitest.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.niceaska.wikitest.R

class ImagesTitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val strings = arguments?.getStringArrayList(STRINGS)
        view.findViewById<TextView>(R.id.result)
            .text = strings?.reduce { acc, s -> acc + "\n" + s }
    }

    companion object {
        private const val STRINGS = "strings"
        fun newInstance(list: List<String>): Fragment {
            val fragment = ImagesTitleFragment()
            val bundle: Bundle = Bundle().apply {
                putStringArrayList(STRINGS, ArrayList<String>(list))
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}