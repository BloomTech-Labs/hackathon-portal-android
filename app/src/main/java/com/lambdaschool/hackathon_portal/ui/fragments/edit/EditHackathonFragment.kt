package com.lambdaschool.hackathon_portal.ui.fragments.edit


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.util.SelectiveJsonObject
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.toastLong
import com.lambdaschool.hackathon_portal.util.toastShort
import kotlinx.android.synthetic.main.fragment_edit_hackathon.*
import java.util.*

class EditHackathonFragment : BaseFragment() {

    private lateinit var editHackathonViewModel: EditHackathonViewModel
    private lateinit var retrievedHackathon: Hackathon
    private val TAG = "EDIT HACKATHON"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editHackathonViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(EditHackathonViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                    updateRetrievedHackathon(it)
                } else {
                    activity?.toastShort("Failed to get Hackathon")
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
            // TODO: Disable Buttons & show a progress bar
            val selectiveJsonObject = SelectiveJsonObject.Builder()
                .add("name", fragment_edit_hackathon_edit_text_hackathon_name, retrievedHackathon.name, true)
                .add("description", fragment_edit_hackathon_edit_text_hackathon_description, retrievedHackathon.description, true)
                .add("url", fragment_edit_hackathon_edit_text_hackathon_url, retrievedHackathon.url, false)
                .add("start_date", fragment_edit_hackathon_edit_text_hackathon_start_date, retrievedHackathon.start_date, true)
                .add("end_date", fragment_edit_hackathon_edit_text_hackathon_end_date, retrievedHackathon.end_date, true)
                .add("location", fragment_edit_hackathon_edit_text_hackathon_location, retrievedHackathon.location, true)
                .add("is_open", switchState, retrievedHackathon.is_open)
                .build()

            if (hackathonId != null) {
                if (selectiveJsonObject != null) {
                    editHackathonViewModel.updateHackathon(hackathonId, selectiveJsonObject).observe(this, Observer {
                        if (it != null) {
                            activity?.toastLong("Successfully updated Hackathon")
                            navigateAndPopUpTo(
                                Bundle(), R.id.nav_user_hackathons, true, R.id.nav_user_hackathons
                            )
                        } else {
                            // TODO: Enable Buttons & disable progress bar
                            activity?.toastShort("Failed to update Hackathon")
                        }
                    })
                } else {
                    // TODO: Enable Buttons & disable progress bar
                    activity?.toastShort("Nothing to update")
                }
            } else {
                // TODO: Enable Buttons & disable progress bar
                Log.d(TAG, "Hackathon Id was null")
            }
        }

        fragment_edit_hackathon_fab_delete_hackathon.setOnClickListener {
            // TODO: Disable Buttons & show a progress bar
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
                                        navigateAndPopUpTo(
                                            Bundle(), R.id.nav_user_hackathons, true, R.id.nav_user_hackathons
                                        )
                                        activity?.toastLong("Successfully deleted Hackathon")
                                    } else {
                                        // TODO: Enable Buttons & disable progress bar
                                        activity?.toastShort("Failed to delete Hackathon")
                                    }
                                } else {
                                    Log.d(TAG, "Hackathon came back null")
                                    // TODO: Enable Buttons & disable progress bar
                                }
                            })
                    }
                }
                .setNegativeButton("No") { _, _ -> }
                .create()
                .show()
        }
    }

    override fun onDestroyView() {
        // TODO: Enable Buttons & disable progress bar
        super.onDestroyView()
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

    private fun updateRetrievedHackathon(hackathon: Hackathon) {
        retrievedHackathon = Hackathon(
            hackathon.name,
            hackathon.description,
            hackathon.url,
            hackathon.start_date,
            hackathon.end_date,
            hackathon.location,
            hackathon.is_open)
    }

    private fun formatCalendarToString(calendar: Calendar): String {
        return "${calendar.month + 1}/${calendar.dayOfMonth}/${calendar.year}"
    }
}
