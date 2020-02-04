package com.lambdaschool.hackathon_portal.ui.fragments.detail


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.ProjectHackathon
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.toastLong
import com.lambdaschool.hackathon_portal.util.visGone
import com.lambdaschool.hackathon_portal.util.visVisible
import kotlinx.android.synthetic.main.fragment_approve_project.*
import kotlinx.android.synthetic.main.project_approval_list_view.view.*

class ApproveProjectFragment : BaseFragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var approvedProjects: MutableList<ProjectHackathon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_approve_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        project_approval_recyclerview.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = ApproveProjectListAdapter(mutableListOf<ProjectHackathon>())
        }

        detailViewModel.currentHackathon.observe(this, Observer {
            it?.let { hackathon ->
                hackathon.projects?.let { projectList ->
                    approvedProjects = projectList.filter { project -> !project.is_approved!! }
                        .toMutableList()
                    project_approval_recyclerview.adapter =
                        ApproveProjectListAdapter(approvedProjects)
                }
            }
        })
    }

    inner class ApproveProjectListAdapter(private val projects: MutableList<ProjectHackathon>):
        RecyclerView.Adapter<ApproveProjectListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val parentCardView: CardView = view.project_approval_parent_card
            val expandView: LinearLayout = view.project_approval_linear_layout_expand
            val titleView: TextView = view.project_approval_title
            val desciptionView: TextView = view.project_approval_description
            val frontEndCountView: TextView = view.project_approval_text_view_front_end_count
            val backEndCountView: TextView = view.project_approval_text_view_back_end_count
            val iosCountView: TextView = view.project_approval_text_view_ios_count
            val androidCountView: TextView = view.project_approval_text_view_android_count
            val uxDesignerCountView: TextView = view.project_approval_text_view_ux_designer_count
            val dataScienceCountView: TextView = view.project_approval_text_view_data_science_count
            val buttonApproveView: Button = view.project_approval_button_approve
            val buttonDeleteView: Button = view.project_approval_button_delete
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.project_approval_list_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = projects.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = projects[position]
            var isExpanded = false
            holder.titleView.text = data.project_title
            holder.parentCardView.setOnClickListener {
                if (!isExpanded) {
                    isExpanded = true
                    holder.expandView.visVisible()
                    holder.desciptionView.text = data.project_description
                    holder.frontEndCountView.text = data.front_end_spots.toString()
                    holder.backEndCountView.text = data.back_end_spots.toString()
                    holder.iosCountView.text = data.ios_spots.toString()
                    holder.androidCountView.text = data.android_spots.toString()
                    holder.uxDesignerCountView.text = data.ux_spots.toString()
                    holder.dataScienceCountView.text = data.data_science_spots.toString()
                    holder.buttonApproveView.setOnClickListener {
                        detailViewModel.approveProject(data.project_id)
                            .observe(this@ApproveProjectFragment, Observer {
                                it?.let { response ->
                                    when (response) {
                                        true -> {
                                            activity?.toastLong("Successfully approved Project!")
                                            projects.removeAt(position)
                                            notifyItemRemoved(position)
                                            notifyItemRangeChanged(position, projects.size)
                                        }
                                        false -> activity?.toastLong("Failed to approve Project!")
                                    }
                                }
                            })
                    }
                    holder.buttonDeleteView.setOnClickListener {
                        detailViewModel.deleteProject(data.project_id)
                            .observe(this@ApproveProjectFragment, Observer {
                                it?.let { response ->
                                    when (response) {
                                        true -> {
                                            activity?.toastLong("Successfully deleted Project!")
                                            projects.removeAt(position)
                                            notifyItemRemoved(position)
                                            notifyItemRangeChanged(position, projects.size)
                                        }
                                        false -> activity?.toastLong("Failed to delete Project!")
                                    }
                                }
                            })
                    }
                } else {
                    isExpanded = false
                    holder.expandView.visGone()
                }
            }
        }
    }
}
