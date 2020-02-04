package com.lambdaschool.hackathon_portal.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

/**
 * The BaseFragment incorporates core functions that most, if not all Fragments
 * will need to utilize.
 * */
abstract class BaseFragment: Fragment() {

    val fragmentComponent by lazy {
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

    /**
     * Builds NavOptions to popUpTo and navigate to specified fragment.
     *
     * @param bundle: Bundle
     * @param popUpTo_ResId : @IdRes Int
     * @param popUpToInclusive : Boolean
     * @param navigateTo_ResId : @IdRes Int
     * */
    fun navigateAndPopUpTo(bundle: Bundle,
                           @IdRes popUpTo_ResId: Int,
                           popUpToInclusive: Boolean,
                           @IdRes navigateTo_ResId: Int) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(popUpTo_ResId, popUpToInclusive)
            .build()

        navController.navigate(navigateTo_ResId, bundle, navOptions)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent.injectBaseFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}