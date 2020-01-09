package com.lambdaschool.hackathon_portal.ui.fragments.add


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_add_hackathon.*
import javax.inject.Inject

class AddHackathonFragment : Fragment() {

    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var addHackathonViewModel: AddHackathonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectAddHackathonFragment(this)
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
            switchState = b
        }

        fab_save_hackathon.setOnClickListener {
            val newHackathon = Hackathon(edit_text_hackathon_name.text.toString(),
                edit_text_hackathon_description.text.toString(),
                edit_text_hackathon_url.text.toString(),
                edit_text_hackathon_start_date.text.toString(),
                edit_text_hackathon_end_date.text.toString(),
                edit_text_hackathon_location.text.toString(),
                switchState)

            addHackathonViewModel.postHackathon(newHackathon).observe(this, Observer {
                if (it != null) {
                    if (it) {
                        activity?.apply {
                            Toast.makeText(this, "Successfully created Hackathon", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        activity?.apply {
                            Toast.makeText(this, "Failed to create Hackathon", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }
    }
}
