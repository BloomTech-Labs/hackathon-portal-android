package com.lambdaschool.hackathon_portal.ui.fragments.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DetailPageAdapter(fm: FragmentManager, private val numOfTabs: Int):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->  DescriptionFragment()
            1 ->  AdminFragment()
            2 ->  ProjectFragment()
            else -> DescriptionFragment()
        }
    }
    override fun getCount() = numOfTabs
}