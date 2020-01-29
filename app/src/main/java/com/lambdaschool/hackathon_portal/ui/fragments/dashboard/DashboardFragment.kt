package com.lambdaschool.hackathon_portal.ui.fragments.dashboard


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.clearAndAddAll
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.hackathon_list_item_view.view.*

class DashboardFragment : BaseFragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var hackathonList = mutableListOf<Hackathon>()
    private var hackathonListCopy = mutableListOf<Hackathon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(DashboardViewModel::class.java)
        dashboardViewModel.getAllHackthons()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_dashboard_recycler_view_all_hackathons.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = HackathonListAdapter(hackathonList)
        }

        dashboardViewModel.getAllHackathonsList().observe(this, Observer {
            if (it != null) {
                hackathonList.clearAndAddAll(it)
                hackathonListCopy = it
                fragment_dashboard_recycler_view_all_hackathons.adapter?.notifyDataSetChanged()
            }
        })

        initTextQueryListener()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                fragment_dashboard_progressbar.visibility = View.VISIBLE
                dashboardViewModel.getAllHackthons().observe(this, Observer {
                    it?.let { fragment_dashboard_progressbar.visibility = View.GONE }
                })
            }
        }
        return super.onOptionsItemSelected(item)
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
                navController.navigate(R.id.nav_hackathon_details, bundle)
            }
        }
    }

    private fun initTextQueryListener() {
        fragment_dashboard_searchview.setIconifiedByDefault(false)
        fragment_dashboard_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var searchList = mutableListOf<Hackathon>()
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchList = dashboardViewModel
                    .searchHackathonList(query, hackathonList, searchList)
                hackathonList.clearAndAddAll(searchList)
                fragment_dashboard_recycler_view_all_hackathons.adapter?.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                hackathonList.clearAndAddAll(hackathonListCopy)
                searchList = dashboardViewModel
                    .searchHackathonList(newText, hackathonList, searchList)
                hackathonList.clearAndAddAll(searchList)
                fragment_dashboard_recycler_view_all_hackathons.adapter?.notifyDataSetChanged()
                return true
            }
        })
    }
}
