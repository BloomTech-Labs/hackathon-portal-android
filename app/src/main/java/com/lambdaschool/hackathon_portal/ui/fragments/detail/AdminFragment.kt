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
import com.lambdaschool.hackathon_portal.model.Admin
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.admin_list_item_view.view.*
import kotlinx.android.synthetic.main.fragment_admin.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

class AdminFragment : Fragment() {

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
        fragmentComponent.injectAdminFragment(this)
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_admin_recycler_view.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = AdminListAdapter(mutableListOf<Admin>())
        }

        detailViewModel.currentHackathon.observe(this, Observer {
            if (it != null) {
                it.admins?.let { admins ->
                    fragment_admin_recycler_view.adapter = AdminListAdapter(admins)
                }
            }
        })
    }

    inner class AdminListAdapter(private val admins: MutableList<Admin>):
        RecyclerView.Adapter<AdminListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameView: TextView = view.text_view_admin_name
            val roleView: TextView = view.text_view_admin_role
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.admin_list_item_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = admins.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = admins[position]
            holder.nameView.text = data.username
            holder.roleView.text = data.user_hackathon_role
        }
    }
}
