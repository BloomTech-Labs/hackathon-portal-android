package com.lambdaschool.hackathon_portal.ui.fragments.detail


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Participant
import com.lambdaschool.hackathon_portal.model.Project
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.team_list_item_view.view.*

class ProjectFragment : BaseFragment() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_project_recycler_view.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = ProjectListAdapter(mutableListOf<Project>())
        }

        detailViewModel.currentHackathon.observe(this, Observer {
            if (it != null) {
                it.projects?.let { projects ->
                    fragment_project_recycler_view.adapter = ProjectListAdapter(projects)
                }
            }
        })
    }

    inner class ProjectListAdapter(private val teams: MutableList<Project>):
        RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameView: TextView = view.text_view_team_name
            val participantsView: TextView = view.text_view_team_participants
            val parentView: CardView = view.parent_card_view
            val linearLayout: LinearLayout = view.linear_layout_list_members
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.team_list_item_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = teams.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = teams[position]
            var hasBeenExpanded = false
            holder.nameView.text = data.project_title
            holder.participantsView.text = " ${data.participants.size.toString()}"
            holder.linearLayout.visibility = View.GONE
            holder.parentView.setOnClickListener {

                when {
                    !holder.linearLayout.isVisible && !hasBeenExpanded -> {
                        data.participants.forEach {
                            holder.linearLayout.addView(createTeamMemberViews(it))
                        }
                        holder.linearLayout.visibility = View.VISIBLE
                        hasBeenExpanded = true
                    }

                    !holder.linearLayout.isVisible && hasBeenExpanded -> {
                        holder.linearLayout.visibility = View.VISIBLE
                    }

                    holder.linearLayout.isVisible -> {
                        holder.linearLayout.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun createTeamMemberViews(participant: Participant): LinearLayout {
        val linearLayout = LinearLayout(this.context)
        val usernameTextView = TextView(this.context)
        val roleTextView = TextView(this.context)
        val paddingAll = 8
        usernameTextView.text = participant.username
        usernameTextView.setTextColor(getResources().getColor(R.color.colorAccentLight))
        roleTextView.text = participant.developer_role
        roleTextView.setTextColor(getResources().getColor(R.color.colorAccentLight))
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(usernameTextView)
        linearLayout.addView(roleTextView)
        linearLayout.setPadding(paddingAll, paddingAll, paddingAll, paddingAll)
        return linearLayout
    }
}
