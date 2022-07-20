package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.podium.technicalchallenge.DemoViewModel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentDashboardBinding
import com.podium.technicalchallenge.entity.MovieEntity

class DashboardFragment : Fragment() {

    private val viewModel: DemoViewModel by activityViewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    var _top5Adapter: MoviesAdapter? = null
    private val top5Adapter get() = _top5Adapter!!

    private lateinit var tvTitle: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val horizontalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        _top5Adapter = MoviesAdapter(this)
        binding.top5MoviesList.adapter = top5Adapter
        binding.top5MoviesList.layoutManager = horizontalLayoutManager

        viewModel.liveTop5Movies.observe(viewLifecycleOwner, {it ->
            it?.let {
                top5Adapter.updateData(it)
            }
        })

        viewModel.getTop5PopularMovies()

        viewModel.getMovies()
    }

    fun displayMovieInfo(movie: MovieEntity) {
        viewModel.liveSelectedMovie.value = movie
        findNavController().navigate(R.id.movieInfoFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _top5Adapter = null // prevent memory leak
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}