package com.lambdaschool.hackathon_portal.ui.fragments.detail


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Admin
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.visGone
import com.lambdaschool.hackathon_portal.util.visVisible
import kotlinx.android.synthetic.main.admin_list_item_view.view.*
import kotlinx.android.synthetic.main.fragment_admin.*

class AdminFragment : BaseFragment() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_fragment_admin_add_admin.visGone()

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
                initAddAdminButton()
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

    private fun initAddAdminButton() {
        if (detailViewModel.currentHackathon.value?.organizer_id
            == detailViewModel.getCurrentUserId()) {
            button_fragment_admin_add_admin.visVisible()
            button_fragment_admin_add_admin.setOnClickListener {
                val bundle = Bundle()
                detailViewModel.currentHackathon.value?.id?.let { bundle.putInt("hackathon_id", it) }
                navController.navigate(R.id.nav_add_admin, bundle)
            }
        }
    }
}
