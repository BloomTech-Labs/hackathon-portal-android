package com.lambdaschool.hackathon_portal.ui.fragments.addadmin


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.toastLong
import kotlinx.android.synthetic.main.add_admin_list_view.view.*
import kotlinx.android.synthetic.main.fragment_add_admin.*

class AddAdminFragment : BaseFragment() {

    private lateinit var addAdminViewModel: AddAdminViewModel
    private var hackathonId: Int? = null
    private var userList = mutableListOf<User>()
    private var userListCopy = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addAdminViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(AddAdminViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO implement search view
        hackathonId = arguments?.getInt("hackathon_id")

        add_admin_recyclerview.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = UserListAdapter(userList)
        }

        addAdminViewModel.getAllUsers().observe(this, Observer {
            it?.let {
                userList = it
                userListCopy.addAll(it)
                add_admin_recyclerview.adapter = UserListAdapter(userList)
            }
        })

        add_admin_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var searchList = mutableListOf<User>()
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchList = addAdminViewModel.searchUserList(query, userList, searchList)
                userList.clear()
                userList.addAll(searchList)
                add_admin_recyclerview.adapter?.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length == 0) {
                    userList.clear()
                    userList.addAll(userListCopy)
                    add_admin_recyclerview.adapter?.notifyDataSetChanged()
                } else {
                    searchList = addAdminViewModel.searchUserList(newText, userList, searchList)
                    userList.clear()
                    userList.addAll(searchList)
                    add_admin_recyclerview.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })
    }

    inner class UserListAdapter(var users: MutableList<User>):
        RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

        lateinit var context: Context

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val usernameView = view.add_admin_text_view_admin_name
            val parentCard = view.add_admin_parent_cardview
            val buttonLayout = view.linear_layout_add_admin_buttons
            val buttonAddOrganizer = view.add_admin_button_add_admin
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_admin_list_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = users[position]
            var isExpanded = false
            holder.usernameView.text = data.username
            holder.parentCard.setOnClickListener {
                if (!isExpanded) {
                    isExpanded = true
                    holder.buttonLayout.visibility = View.VISIBLE
                    holder.buttonAddOrganizer.setOnClickListener {
                        // TODO display alert dialog first?
                        hackathonId?.let { id ->
                            addOrganizer(id, data.id)
                        }
                    }
                } else {
                    isExpanded = false
                    holder.buttonLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun addOrganizer(myHackathonId: Int, userId: Int) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_hackathon_role", "organizer")
        addAdminViewModel.addOrganizerToHackathon(myHackathonId, userId, jsonObject)
            .observe(this, Observer { activity?.toastLong(it) })
    }
}
