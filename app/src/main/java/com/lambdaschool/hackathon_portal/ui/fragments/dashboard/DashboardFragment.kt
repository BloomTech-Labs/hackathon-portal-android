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
import com.lambdaschool.hackathon_portal.model.Hackathon
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
        dashboardViewModel.getAllHackthons()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_dashboard_recycler_view_all_hackathons.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = HackathonListAdapter(mutableListOf<Hackathon>())
        }

        dashboardViewModel.getAllHackathonsList().observe(this, Observer {
            if (it != null) {
                fragment_dashboard_recycler_view_all_hackathons.adapter = HackathonListAdapter(it)
            }
        })
    }

    inner class HackathonListAdapter(private val hackathons: MutableList<Hackathon>):
        RecyclerView.Adapter<HackathonListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameView: TextView = view.text_view_hackathon_name
            val locationView: TextView = view.text_view_hackathon_location
            val startView: TextView = view.text_view_start_date
            val statusView: TextView = view.text_view_status
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hackathon_list_item_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = hackathons.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = hackathons[position]
            holder.nameView.text = data.name
            holder.locationView.text = "Location: ${data.location}"
            holder.startView.text = "Start Date: ${data.start_date}"
            holder.statusView.text = if (data.is_open) {
                getString(R.string.status_open)
            } else {
                getString(R.string.status_closed)
                // TODO: set text color
            }
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                data.id?.let {
                    bundle.putInt("hackathon_id", it)
                }
                navController.navigate(R.id.detailFragment, bundle)
            }
        }
    }
}
