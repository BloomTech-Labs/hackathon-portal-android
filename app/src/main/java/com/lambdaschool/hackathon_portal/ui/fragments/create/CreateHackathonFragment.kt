package com.lambdaschool.hackathon_portal.ui.fragments.create


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.setClickListenerToOpenDatePickerAndSetTextToDate
import com.lambdaschool.hackathon_portal.util.toastLong
import com.lambdaschool.hackathon_portal.util.toastShort
import kotlinx.android.synthetic.main.fragment_create_hackathon.*
import timber.log.Timber

class CreateHackathonFragment : BaseFragment() {

    private lateinit var createHackathonViewModel: CreateHackathonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createHackathonViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(CreateHackathonViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_hackathon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var switchState = false

        switch_is_hackathon_open.setOnCheckedChangeListener { _, b ->
            switchState = b
            when (b) {
                true -> text_view_is_hackathon_open_yes_or_no.text = getString(R.string.yes)
                false -> text_view_is_hackathon_open_yes_or_no.text = getString(R.string.no)
            }
        }

        this.context?.let { context ->
            edit_text_hackathon_end_date.setClickListenerToOpenDatePickerAndSetTextToDate(context)
            edit_text_hackathon_start_date.setClickListenerToOpenDatePickerAndSetTextToDate(context)
        }

        button_fragment_create_hackathon_create_hackathon.setOnClickListener {
            // TODO: Disable Button & show a progress bar

            if (!checkIfRequiredFieldsEmpty()) {
                val newHackathon =
                    Hackathon(edit_text_hackathon_name.text.toString(),
                        edit_text_hackathon_description.text.toString(),
                        edit_text_hackathon_url.text.toString(),
                        edit_text_hackathon_start_date.text.toString(),
                        edit_text_hackathon_end_date.text.toString(),
                        edit_text_hackathon_location.text.toString(),
                        switchState)

                createHackathonViewModel.postHackathon(newHackathon).observe(this, Observer {
                    if (it != null) {
                        if (it) {
                            navigateAndPopUpTo(
                                Bundle(), R.id.nav_user_hackathons, true, R.id.nav_user_hackathons
                            )
                            activity?.toastLong("Successfully created Hackathon")
                        }
                        else {
                            // TODO: Enable Buttons & disable progress bar
                            activity?.toastShort("Failed to create Hackathon")
                        }
                    } else {
                        // TODO: Enable Buttons & disable progress bar
                        Timber.d("Hackathon object came back null")
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        // TODO: Enable Buttons & disable progress bar
        super.onDestroyView()
    }

    private fun checkIfRequiredFieldsEmpty(): Boolean {
        var requiredFieldsEmpty = false
        if (edit_text_hackathon_name.text.toString().isEmpty()) {
            edit_text_hackathon_name.error = "Name is required"
            requiredFieldsEmpty = true
        }
        if (edit_text_hackathon_start_date.text.toString().isEmpty()) {
            edit_text_hackathon_start_date.error = "Start Date is required"
            requiredFieldsEmpty = true
        }
        if (edit_text_hackathon_end_date.text.toString().isEmpty()) {
            edit_text_hackathon_end_date.error = "End Date is required"
            requiredFieldsEmpty = true
        }
        if (requiredFieldsEmpty) {
            Timber.d("Required fields needed")
            // TODO: Enable Button & disable progress bar
        }
        return requiredFieldsEmpty
    }
}
