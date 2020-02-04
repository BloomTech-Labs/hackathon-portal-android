package com.lambdaschool.hackathon_portal.ui.fragments.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_description.*

class DescriptionFragment : BaseFragment() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.currentHackathon.observe(this, Observer { hackathon ->
            hackathon?.let { fragment_description_text_view_description.text = it.description }
        })
    }
}
