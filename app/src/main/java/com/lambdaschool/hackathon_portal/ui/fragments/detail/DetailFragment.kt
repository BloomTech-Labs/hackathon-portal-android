package com.lambdaschool.hackathon_portal.ui.fragments.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_edit_hackathon.*
import javax.inject.Inject

class DetailFragment : Fragment() {


    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var navController: NavController
    lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectDetailFragment(this)
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hackathonId = arguments?.getInt("hackathon_id")


        if (hackathonId != null) {
            detailViewModel.getHackathon(hackathonId).observe(this, Observer {
                if (it != null) {
                    updateHackathonViews(it)
                } else {
                    activity?.apply {
                        Toast.makeText(this,
                            "Failed to get Hackathon",
                            Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun updateHackathonViews(hackathon: Hackathon) {
        fragment_detail_text_view_hackathon_name.text = hackathon.name
        fragment_detail_text_view_hackathon_description.text = hackathon.description
        fragment_detail_text_view_hackathon_url.text = hackathon.url
        fragment_detail_text_view_hackathon_location.text = hackathon.location
        fragment_detail_text_view_hackathon_start_date.text = hackathon.start_date
        fragment_detail_text_view_hackathon_end_date.text = hackathon.end_date
        if (hackathon.is_open) {
            fragment_detail_text_view_hackathon_is_open.text = getString(R.string.hackathon_is_open)
        } else {
            fragment_detail_text_view_hackathon_is_open.text = getString(R.string.hackathon_is_closed)
        }
    }
}
