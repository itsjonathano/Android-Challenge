package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.podium.technicalchallenge.MainActivity
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentDashboardBinding
import com.podium.technicalchallenge.ui.discover.DiscoverFragment
import com.podium.technicalchallenge.ui.home.HomepageFragment
import com.podium.technicalchallenge.ui.search.SearchFragment

class DashboardFragment : Fragment() {

    private var mediator : TabLayoutMediator? = null
    private lateinit var tabView : View

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    var overviewPagerStateAdapter: DashboardPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val fragments = ArrayList<Fragment> ()
        fragments.add(HomepageFragment.newInstance())
        fragments.add(DiscoverFragment.newInstance())
        fragments.add(SearchFragment.newInstance())

        overviewPagerStateAdapter = DashboardPagerAdapter(this, fragments)

        binding.pager.adapter = overviewPagerStateAdapter
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                (activity as MainActivity).apply {
                }
            }
        })
        binding.pager.isUserInputEnabled = false;

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        (activity as? MainActivity)?.apply {
            tabView = layoutInflater.inflate(R.layout.tablayout, this.binding.coordinatorLayout,false)
            this.binding.coordinatorLayout.addView(tabView)
        }
        mediator = TabLayoutMediator(tabView.findViewById(R.id.tabLayout), binding.pager) { tab, position ->
            tab.apply {
                when(position){
                    0 -> {
                        text = getString(R.string.title_home)
                        setIcon(R.drawable.ic_home_black_24dp)
                    }
                    1 -> {
                        text = getString(R.string.title_discover)
                        setIcon(R.drawable.ic_baseline_adjust_24)
                    }
                    2 -> {
                        text = getString(R.string.title_search)
                        setIcon(R.drawable.ic_baseline_search_24)
                    }
                }
            }
        }

        mediator!!.attach()

        tabView.findViewById<TabLayout>(R.id.tabLayout).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}