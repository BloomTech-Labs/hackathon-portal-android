package com.lambdaschool.hackathon_portal.ui.fragments.dashboard


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.CurrentUser
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.hackathon_list_item_view.view.*
import javax.inject.Inject

class DashboardFragment : Fragment() {

    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var navController: NavController
    lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectDashboardFragment(this)
        super.onCreate(savedInstanceState)
        dashboardViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DashboardViewModel::class.java)
        CurrentUser.currentUser.id?.toInt()?.let {
            dashboardViewModel.getUser(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_dashboard_my_hackathons.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = UserHackathonListAdapter(mutableListOf<UserHackathon>())
        }

        dashboardViewModel.getUserHackathonList().observe(this, Observer {
            if (it != null) {
                recycler_view_dashboard_my_hackathons.adapter = UserHackathonListAdapter(it)
            }
        })
    }

    inner class UserHackathonListAdapter(private val userHackathons: MutableList<UserHackathon>):
        RecyclerView.Adapter<UserHackathonListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameView: TextView = view.text_view_hackathon_name
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hackathon_list_item_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = userHackathons.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = userHackathons[position]
            holder.nameView.text = data.hackathon_name
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("hackathon_id", data.hackathon_id)
                navController.navigate(R.id.detailsFragment, bundle)
            }
        }
    }
}
