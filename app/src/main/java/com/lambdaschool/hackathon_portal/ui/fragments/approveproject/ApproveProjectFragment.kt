package com.lambdaschool.hackathon_portal.ui.fragments.approveproject


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lambdaschool.hackathon_portal.R

/**
 * A simple [Fragment] subclass.
 */
class ApproveProjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approve_project, container, false)
    }


}
