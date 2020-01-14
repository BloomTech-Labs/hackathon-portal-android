package com.lambdaschool.hackathon_portal.ui.fragments.userhackathons


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
import kotlinx.android.synthetic.main.fragment_user_hackathons.*
import kotlinx.android.synthetic.main.hackathon_list_item_view.view.*
import javax.inject.Inject

class UserHackathonsFragment : Fragment() {

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
    lateinit var userHackathonsViewModel: UserHackathonsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectUserHackathonsFragment(this)
        super.onCreate(savedInstanceState)
        userHackathonsViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(UserHackathonsViewModel::class.java)
        CurrentUser.currentUser.id?.toInt()?.let {
            userHackathonsViewModel.getUser(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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
                navController.navigate(R.id.editHackathonFragment, bundle)
            }
        }
    }
}
