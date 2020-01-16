package com.lambdaschool.hackathon_portal.ui.fragments.edit


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_edit_hackathon.*
import java.util.*
import javax.inject.Inject

class EditHackathonFragment : Fragment() {

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
    lateinit var editHackathonViewModel: EditHackathonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectEditHackathonFragment(this)
        super.onCreate(savedInstanceState)
        editHackathonViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(EditHackathonViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_hackathon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hackathonId = arguments?.getInt("hackathon_id")
        var switchState = false

        fragment_edit_hackathon_switch_is_hackathon_open.setOnCheckedChangeListener { _, b ->
            switchState = b
            when (b) {
                true -> fragment_edit_hackathon_text_view_is_hackathon_open_yes_or_no.text = getString(R.string.yes)
                false -> fragment_edit_hackathon_text_view_is_hackathon_open_yes_or_no.text = getString(R.string.no)
            }
        }

        if (hackathonId != null) {
            editHackathonViewModel.getHackathon(hackathonId).observe(this, Observer {
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

        this.context?.let { context ->
            fragment_edit_hackathon_edit_text_hackathon_end_date.setOnClickListener {
                MaterialDialog(context).show {
                    datePicker { _, datetime ->
                        this@EditHackathonFragment
                            .fragment_edit_hackathon_edit_text_hackathon_end_date
                            .setText(formatCalendarToString(datetime))
                    }
                }
            }

            fragment_edit_hackathon_edit_text_hackathon_start_date.setOnClickListener {
                MaterialDialog(context).show {
                    datePicker { _, datetime ->
                        this@EditHackathonFragment
                            .fragment_edit_hackathon_edit_text_hackathon_start_date
                            .setText(formatCalendarToString(datetime))
                    }
                }
            }
        }

        fragment_edit_hackathon_fab_save_hackathon.setOnClickListener {
            val newHackathon =
                Hackathon(fragment_edit_hackathon_edit_text_hackathon_name.text.toString(),
                    fragment_edit_hackathon_edit_text_hackathon_description.text.toString(),
                    fragment_edit_hackathon_edit_text_hackathon_url.text.toString(),
                    fragment_edit_hackathon_edit_text_hackathon_start_date.text.toString(),
                    fragment_edit_hackathon_edit_text_hackathon_end_date.text.toString(),
                    fragment_edit_hackathon_edit_text_hackathon_location.text.toString(),
                    switchState)

            if (hackathonId != null) {
                editHackathonViewModel.updateHackathon(hackathonId, newHackathon)
                    .observe(this, Observer {
                    if (it != null) {
                        updateHackathonViews(it)
                        navigateToUserHackathonsFragment()
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

        fragment_edit_hackathon_fab_delete_hackathon.setOnClickListener {
            val title = "Delete Hackathon?"
            val msg = "Are you sure you want to delete this Hackathon?"

            AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes") { _, _ ->
                    if (hackathonId != null) {
                        editHackathonViewModel.deleteHackathon(hackathonId)
                            .observe(this, Observer {
                                if (it != null) {
                                    if (it) {
                                        navigateToUserHackathonsFragment()
                                        activity?.apply {
                                            Toast.makeText(this,
                                                "Successfully deleted Hackathon",
                                                Toast.LENGTH_LONG).show()
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

    private fun navigateToUserHackathonsFragment() {
        val bundle = Bundle()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_user_hackathons, true)
            .build()
        navController.navigate(
            R.id.nav_user_hackathons,
            bundle,
            navOptions)
    }

    private fun updateHackathonViews(hackathon: Hackathon) {
        fragment_edit_hackathon_edit_text_hackathon_name.setText(hackathon.name)
        fragment_edit_hackathon_edit_text_hackathon_description.setText(hackathon.description)
        fragment_edit_hackathon_edit_text_hackathon_url.setText(hackathon.url)
        fragment_edit_hackathon_edit_text_hackathon_location.setText(hackathon.location)
        fragment_edit_hackathon_edit_text_hackathon_start_date.setText(hackathon.start_date)
        fragment_edit_hackathon_edit_text_hackathon_end_date.setText(hackathon.end_date)
        fragment_edit_hackathon_switch_is_hackathon_open.isChecked = hackathon.is_open
    }

    private fun formatCalendarToString(calendar: Calendar): String {
        return "${calendar.month + 1}/${calendar.dayOfMonth}/${calendar.year}"
    }
}
