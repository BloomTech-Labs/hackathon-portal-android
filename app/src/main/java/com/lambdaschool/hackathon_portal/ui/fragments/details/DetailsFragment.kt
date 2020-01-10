package com.lambdaschool.hackathon_portal.ui.fragments.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
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

        fragment_details_fab_save_hackathon.setOnClickListener {
            val newHackathon =
                Hackathon(fragment_details_edit_text_hackathon_name.text.toString(),
                    fragment_details_edit_text_hackathon_description.text.toString(),
                    fragment_details_edit_text_hackathon_url.text.toString(),
                    fragment_details_edit_text_hackathon_start_date.text.toString(),
                    fragment_details_edit_text_hackathon_end_date.text.toString(),
                    fragment_details_edit_text_hackathon_location.text.toString(),
                    switchState)

            if (hackathonId != null) {
                detailsViewModel.updateHackathon(hackathonId, newHackathon)
                    .observe(this, Observer {
                    if (it != null) {
                        updateHackathonViews(it)
                        activity?.apply {
                            Toast.makeText(this,
                                "Successfully updated Hackathon",
                                Toast.LENGTH_LONG).show()
                        }
                    } else {
                        activity?.apply {
                            Toast.makeText(this,
                                "Failed to update Hackathon",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }

        fragment_details_fab_delete_hackathon.setOnClickListener {
            val title = "Delete Hackathon?"
            val msg = "Are you sure you want to delete this Hackathon?"

            AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes") { _, _ ->
                    if (hackathonId != null) {
                        detailsViewModel.deleteHackathon(hackathonId)
                            .observe(this, Observer {
                                if (it != null) {
                                    if (it) {
                                        activity?.apply {
                                            Toast.makeText(this,
                                                "Successfully deleted Hackathon",
                                                Toast.LENGTH_LONG).show()
                                            findNavController().navigateUp()
                                        }
                                    } else {
                                        activity?.apply {
                                            Toast.makeText(this,
                                                "Failed to delete Hackathon",
                                                Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            })
                    }
                }

                .setNegativeButton("No") { _, _ -> }
                .create()
                .show()

        }
    }

    private fun updateHackathonViews(hackathon: Hackathon) {
        fragment_details_edit_text_hackathon_name.setText(hackathon.name)
        fragment_details_edit_text_hackathon_description.setText(hackathon.description)
        fragment_details_edit_text_hackathon_url.setText(hackathon.url)
        fragment_details_edit_text_hackathon_location.setText(hackathon.location)
        fragment_details_edit_text_hackathon_start_date.setText(hackathon.start_date)
        fragment_details_edit_text_hackathon_end_date.setText(hackathon.end_date)
        fragment_details_switch_is_hackathon_open.isChecked = hackathon.is_open
    }
}
