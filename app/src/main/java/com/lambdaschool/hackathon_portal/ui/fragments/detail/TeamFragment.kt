package com.lambdaschool.hackathon_portal.ui.fragments.detail


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Team
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.android.synthetic.main.team_list_item_view.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
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
            holder.nameView.text = data.team_name
            holder.participantsView.text = data.devs.size.toString()
        }
    }
}
