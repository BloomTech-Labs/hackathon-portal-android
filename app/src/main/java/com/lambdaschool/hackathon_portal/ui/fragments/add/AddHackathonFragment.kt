package com.lambdaschool.hackathon_portal.ui.fragments.add


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.lambdaschool.hackathon_portal.App

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_add_hackathon.*
import javax.inject.Inject

class AddHackathonFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var addHackathonViewModel: AddHackathonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App)
            .appComponent
            .injectAddHackathonFragment(this)
        super.onCreate(savedInstanceState)

        addHackathonViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AddHackathonViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_hackathon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var switchState = false

        switch_is_open.setOnCheckedChangeListener { button, b ->
            switchState = when (b) {
                true -> true
                false -> false
            }
        }

        fab_save_hackathon.setOnClickListener {
            val newHackathon = Hackathon(edit_text_hackathon_name.text.toString(),
                edit_text_hackathon_description.text.toString(),
                edit_text_hackathon_url.text.toString(),
                edit_text_hackathon_start_date.text.toString(),
                edit_text_hackathon_end_date.text.toString(),
                edit_text_hackathon_location.text.toString(),
                switchState)

            addHackathonViewModel.postHackathon(newHackathon)
        }
    }
}
