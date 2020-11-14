package ru.niceaska.wikitest.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import ru.niceaska.wikitest.R

/**
 * [Fragment] для отображения [ProgressBar] загрузки
 */
class ProgressFragment : Fragment() {

    private var progress: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progres_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = view.findViewById(R.id.progress)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progress = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progress?.postDelayed({
            progress?.visibility = View.VISIBLE
        }, 20)
    }
}