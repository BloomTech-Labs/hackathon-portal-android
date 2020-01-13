package com.lambdaschool.hackathon_portal.ui.fragments.dashboard


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.CurrentUser
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
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
    }
}
