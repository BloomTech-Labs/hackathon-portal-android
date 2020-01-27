package com.lambdaschool.hackathon_portal.ui.fragments.userhackathons


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_hackathons.*
import kotlinx.android.synthetic.main.hackathon_list_item_view.view.*

class UserHackathonsFragment : BaseFragment() {

    private lateinit var userHackathonsViewModel: UserHackathonsViewModel
    private val ORGANIZER = "organizer"
    private val PARTICIPANT = "participant"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userHackathonsViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(UserHackathonsViewModel::class.java)
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

    inner class UserHackathonListAdapter(private val userHackathons: MutableList<UserHackathon>):
        RecyclerView.Adapter<UserHackathonListAdapter.ViewHolder>() {

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

        override fun getItemCount(): Int = userHackathons.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = userHackathons[position]
            holder.nameView.text = data.hackathon_name
            //TODO: bind locationView
            holder.startView.text = "Start Date: ${data.start_date}"
            //TODO: bind statusView
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("hackathon_id", data.hackathon_id)
                if (data.user_hackathon_role == ORGANIZER) {
                    navController.navigate(R.id.nav_edit_hackathon, bundle)
                } else if (data.user_hackathon_role == PARTICIPANT) {
                    navController.navigate(R.id.nav_hackathon_details, bundle)
                }
            }
        }
    }
}
