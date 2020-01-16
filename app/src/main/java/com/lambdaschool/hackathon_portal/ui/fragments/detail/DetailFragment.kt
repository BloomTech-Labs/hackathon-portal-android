package com.lambdaschool.hackathon_portal.ui.fragments.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util._toast
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailPageAdapter: DetailPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hackathonId = arguments?.getInt("hackathon_id")
        detailPageAdapter = DetailPageAdapter(childFragmentManager, fragment_detail_tab_layout.tabCount)
        view_pager.adapter = detailPageAdapter

        if (hackathonId != null) {
            detailViewModel.getHackathon(hackathonId).observe(this, Observer {
                if (it != null) {
                    updateHackathonViews(it)
                    detailViewModel.currentHackathon.value = it
                } else {
                    activity?._toast("Failed to get Hackathon")
                }
            })
        }

        fragment_detail_tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { position ->
                    view_pager.currentItem = position
                }
            }
        })

        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                fragment_detail_tab_layout.getTabAt(position)?.select()
            }

        })
    }
    private fun updateHackathonViews(hackathon: Hackathon) {
        fragment_detail_text_view_hackathon_name.text = hackathon.name
        //fragment_detail_text_view_hackathon_description.text = hackathon.description
        fragment_detail_text_view_hackathon_url.text = hackathon.url
        fragment_detail_text_view_hackathon_location.text = hackathon.location
        fragment_detail_text_view_hackathon_start_date.text = hackathon.start_date
        fragment_detail_text_view_hackathon_end_date.text = hackathon.end_date
        if (hackathon.is_open) {
            fragment_detail_text_view_hackathon_is_open.text = getString(R.string.hackathon_is_open)
        } else {
            fragment_detail_text_view_hackathon_is_open.text = getString(R.string.hackathon_is_closed)
        }
    }
}
