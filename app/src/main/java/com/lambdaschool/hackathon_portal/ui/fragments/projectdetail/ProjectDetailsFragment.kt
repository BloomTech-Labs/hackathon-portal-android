package com.lambdaschool.hackathon_portal.ui.fragments.projectdetail


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.gson.JsonObject

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Participant
import com.lambdaschool.hackathon_portal.model.Project
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.toastLong
import com.lambdaschool.hackathon_portal.util.toastShort
import kotlinx.android.synthetic.main.fragment_project_details.*
import kotlinx.android.synthetic.main.participant_list_view.view.*

class ProjectDetailsFragment : BaseFragment() {

    private lateinit var projectDetailViewModel: ProjectDetailViewModel
    private lateinit var retrievedProject: Project
    private val displayRoles = mutableListOf<String>()
    private val actualRoles = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectDetailViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(ProjectDetailViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = arguments?.getInt("project_id")

        recycler_view_participants.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = ParticipantListAdapter(mutableListOf<Participant>())
        }

        projectId?.let { id ->
            projectDetailViewModel.getProject(id).observe(this, Observer { project ->
                if (project != null) {
                    updateProjectViews(project)
                    retrievedProject = project
                    buildRoleLists()
                } else activity?.toastShort("Failed to get project")
            })
        }


        var message = "Please select the developer role you will be fulfilling for this project"
        if (actualRoles.size == 0) message = "There are no open roles for this project"

        button_fragment_project_details_join_hackathon.setOnClickListener {
            context?.let { myContext ->
                MaterialDialog(myContext).show {
                    title(text = "Select Role")
                    message(text = message)
                    listItemsSingleChoice(items = displayRoles) { _, index, _ ->
                        joinHackathon(actualRoles[index])
                    }
                }
            }
        }
    }

    inner class ParticipantListAdapter(private val participants: MutableList<Participant>):
        RecyclerView.Adapter<ParticipantListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val usernameView: TextView = view.text_view_participant_username_value
            val roleView: TextView = view.text_view_participant_role_value
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.participant_list_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = participants.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = participants[position]
            holder.usernameView.text = data.username
            holder.roleView.text = data.developer_role
        }

    }

    private fun updateProjectViews(project: Project) {
        project_details_title.text = project.title
        project_details_description.text = project.description
        text_view_front_end_count.text = project.front_end_spots.toString()
        text_view_back_end_count.text = project.back_end_spots.toString()
        text_view_ios_count.text = project.ios_spots.toString()
        text_view_android_count.text = project.android_spots.toString()
        text_view_data_science_count.text = project.data_science_spots.toString()
        text_view_ux_designer_count.text = project.ux_spots.toString()
        project.participants?.let {
            recycler_view_participants.adapter = ParticipantListAdapter(it)
        }
    }

    private fun joinHackathon(role: String) {
        val json = JsonObject()
        json.addProperty("project_id", retrievedProject.id)
        json.addProperty("user_hackathon_role", "participant")
        json.addProperty("developer_role", role)
        projectDetailViewModel.joinProject(json, retrievedProject.hackathon_id).observe(this, Observer {
            it?.let {
                activity?.toastLong(it)
                navController.popBackStack()
            }
        })
    }

    private fun buildRoleLists() {
        if (retrievedProject.front_end_spots > 0) {
            displayRoles.add("Front End Developer")
            actualRoles.add("front-end")
        }

        if (retrievedProject.back_end_spots > 0) {
            displayRoles.add("Back End Developer")
            actualRoles.add("back-end")
        }

        if (retrievedProject.ios_spots > 0) {
            displayRoles.add("iOS Developer")
            actualRoles.add("ios")
        }

        if (retrievedProject.android_spots > 0) {
            displayRoles.add("Android Developer")
            actualRoles.add("android")
        }

        if (retrievedProject.data_science_spots > 0) {
            displayRoles.add("Data Scientist")
            actualRoles.add("data_science")
        }

        if (retrievedProject.front_end_spots > 0) {
            displayRoles.add("UX Designer")
            actualRoles.add("ux")
        }
    }
}
