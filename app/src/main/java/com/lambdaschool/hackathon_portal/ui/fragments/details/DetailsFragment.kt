package com.lambdaschool.hackathon_portal.ui.fragments.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectDetailsFragment(this)
        super.onCreate(savedInstanceState)
        detailsViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hackathonId = arguments?.getInt("hackathon_id")

        var switchState = false

        fragment_details_switch_is_hackathon_open.setOnCheckedChangeListener { _, b ->
            switchState = b
            when (b) {
                true -> fragment_details_text_view_is_hackathon_open_yes_or_no.text = getString(R.string.yes)
                false -> fragment_details_text_view_is_hackathon_open_yes_or_no.text = getString(R.string.no)
            }
        }

        if (hackathonId != null) {
            detailsViewModel.getHackathon(hackathonId).observe(this, Observer {
                if (it != null) {
                    fragment_details_edit_text_hackathon_name.setText(it.name)
                    fragment_details_edit_text_hackathon_description.setText(it.description)
                    fragment_details_edit_text_hackathon_url.setText(it.url)
                    fragment_details_edit_text_hackathon_location.setText(it.location)
                    fragment_details_edit_text_hackathon_start_date.setText(it.start_date)
                    fragment_details_edit_text_hackathon_end_date.setText(it.end_date)
                    fragment_details_switch_is_hackathon_open.isChecked = it.is_open
                }
            })
        }

    }
}
