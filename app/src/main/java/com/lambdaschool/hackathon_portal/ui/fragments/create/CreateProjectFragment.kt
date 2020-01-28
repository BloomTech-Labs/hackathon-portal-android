package com.lambdaschool.hackathon_portal.ui.fragments.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Project
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.openSoftKeyboardAndFocus
import com.lambdaschool.hackathon_portal.util.toastShort
import kotlinx.android.synthetic.main.fragment_create_project.*

class CreateProjectFragment : BaseFragment() {

    private lateinit var createProjectViewModel: CreateProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createProjectViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(CreateProjectViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.openSoftKeyboardAndFocus(edit_text_project_title)

        val hackathonId = arguments?.getInt("hackathon_id")

        button_fragment_create_project_create_project.setOnClickListener {
            if (hackathonId != null) {
                val projectTitle = edit_text_project_title.text.toString()
                val projectDescription = edit_text_project_description.text.toString()
                val frontEndSpots = getIntegerFromEditText(edit_text_front_end_spots)
                val backEndSpots = getIntegerFromEditText(edit_text_back_end_spots)
                val iosSpots = getIntegerFromEditText(edit_text_ios_spots)
                val androidSpots = getIntegerFromEditText(edit_text_android_spots)
                val dataScienceSpots = getIntegerFromEditText(edit_text_data_science_spots)
                val uxDesignerSpots = getIntegerFromEditText(edit_text_ux_designer_spots)

                val project =
                    Project(
                        projectTitle,
                        projectDescription,
                        false,
                        createProjectViewModel.getUserObject().id,
                        hackathonId,
                        frontEndSpots,
                        backEndSpots,
                        iosSpots,
                        androidSpots,
                        dataScienceSpots,
                        uxDesignerSpots)

                createProjectViewModel.postProject(project).observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it) {
                            activity?.toastShort("Project created!")
                            navController.popBackStack()
                        } else {
                            activity?.toastShort("Failed to create Project")
                        }
                    }
                })
            }
        }
    }

    private fun getIntegerFromEditText(editText: EditText): Int {
        return if (!editText.text.isNullOrEmpty()) {
            editText.text.toString().toInt()
        } else {
            0
        }
    }
}
