package com.lambdaschool.hackathon_portal.ui.fragments.create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util._navigateAndPopUpTo
import com.lambdaschool.hackathon_portal.util._toastLong
import com.lambdaschool.hackathon_portal.util._toastShort
import kotlinx.android.synthetic.main.fragment_create_hackathon.*
import java.util.*

class CreateHackathonFragment : BaseFragment() {

    private lateinit var createHackathonViewModel: CreateHackathonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createHackathonViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
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
            edit_text_hackathon_end_date.setOnClickListener {
                MaterialDialog(context).show {
                    datePicker { _, datetime ->
                        this@CreateHackathonFragment
                            .edit_text_hackathon_end_date
                            .setText(formatCalendarToString(datetime))
                    }
                }
            }

            edit_text_hackathon_start_date.setOnClickListener {
                MaterialDialog(context).show {
                    datePicker { _, datetime ->
                        this@CreateHackathonFragment
                            .edit_text_hackathon_start_date
                            .setText(formatCalendarToString(datetime))
                    }
                }
            }
        }

        fab_save_hackathon.setOnClickListener {

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
                            navController._navigateAndPopUpTo(
                                Bundle(), R.id.nav_dashboard, false, R.id.nav_user_hackathons
                            )
                            activity?._toastLong("Successfully created Hackathon")
                        }
                        else {
                            activity?._toastShort("Failed to create Hackathon")
                        }
                    }
                })
            }
        }
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
        return requiredFieldsEmpty
    }

    //Todo move this to an extension file or util class
    private fun formatCalendarToString(calendar: Calendar): String {
        return "${calendar.month + 1}/${calendar.dayOfMonth}/${calendar.year}"
    }
}
