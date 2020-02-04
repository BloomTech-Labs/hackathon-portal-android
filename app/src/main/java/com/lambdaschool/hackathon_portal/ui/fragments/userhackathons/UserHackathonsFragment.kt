package com.lambdaschool.hackathon_portal.ui.fragments.userhackathons


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.visVisible
import kotlinx.android.synthetic.main.fragment_user_hackathons.*
import kotlinx.android.synthetic.main.hackathon_list_item_view.view.*

class UserHackathonsFragment : BaseFragment() {

    private lateinit var userHackathonsViewModel: UserHackathonsViewModel
    private val ORGANIZER = "organizer"
    private val PARTICIPANT = "participant"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userHackathonsViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(UserHackathonsViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_hackathons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_user_hackathons_recycler_view_my_hackathons.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = UserHackathonListAdapter(mutableListOf<UserHackathon>())
        }

        userHackathonsViewModel.getUserHackathonList().observe(this, Observer {
            if (it != null) {
                fragment_user_hackathons_recycler_view_my_hackathons.adapter = UserHackathonListAdapter(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                fragment_user_hackathons_progressbar.visibility = View.VISIBLE
                userHackathonsViewModel.getUser().observe(this, Observer {
                    it?.let { fragment_user_hackathons_progressbar.visibility = View.GONE }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class UserHackathonListAdapter(private val userHackathons: MutableList<UserHackathon>):
        RecyclerView.Adapter<UserHackathonListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameView: TextView = view.text_view_hackathon_name
            val userRole: TextView = view.text_view_user_role
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

        override fun getItemCount(): Int = userHackathons.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = userHackathons[position]
            holder.nameView.text = data.hackathon_name
            holder.userRole.visVisible()
            holder.userRole.text = data.user_hackathon_role
            //TODO: bind locationView
            holder.startView.text = "Start Date: ${data.start_date}"
            //TODO: bind statusView
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("hackathon_id", data.hackathon_id)
                navController.navigate(R.id.nav_hackathon_details, bundle)
            }
        }
    }
}
