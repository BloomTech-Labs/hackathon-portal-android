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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Dev
import com.lambdaschool.hackathon_portal.model.Team
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.android.synthetic.main.team_list_item_view.view.*
import javax.inject.Inject

class TeamFragment : Fragment() {

    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectTeamFragment(this)
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_team_recycler_view.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = TeamListAdapter(mutableListOf<Team>())
        }

        detailViewModel.currentHackathon.observe(this, Observer {
            if (it != null) {
                it.teams?.let { teams ->
                    fragment_team_recycler_view.adapter = TeamListAdapter(teams)
                }
            }
        })
    }

    inner class TeamListAdapter(private val teams: MutableList<Team>):
        RecyclerView.Adapter<TeamListAdapter.ViewHolder>() {

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
            holder.nameView.text = data.team_name
            holder.participantsView.text = " ${data.devs.size.toString()}"
            holder.linearLayout.visibility = View.GONE
            holder.parentView.setOnClickListener {

                when {
                    !holder.linearLayout.isVisible && !hasBeenExpanded -> {
                        data.devs.forEach {
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

    private fun createTeamMemberViews(dev: Dev): LinearLayout {
        val linearLayout = LinearLayout(this.context)
        val usernameTextView = TextView(this.context)
        val roleTextView = TextView(this.context)
        val paddingAll = 8
        usernameTextView.text = dev.username
        usernameTextView.setTextColor(getResources().getColor(R.color.colorAccentLight))
        roleTextView.text = dev.developer_role
        roleTextView.setTextColor(getResources().getColor(R.color.colorAccentLight))
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(usernameTextView)
        linearLayout.addView(roleTextView)
        linearLayout.setPadding(paddingAll, paddingAll, paddingAll, paddingAll)
        return linearLayout
    }
}
