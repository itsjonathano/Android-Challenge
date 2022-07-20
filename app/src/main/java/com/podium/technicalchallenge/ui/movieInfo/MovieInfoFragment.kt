package com.podium.technicalchallenge.ui.movieInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.podium.technicalchallenge.DemoViewModel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentMovieInfoBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.squareup.picasso.Picasso


class MovieInfoFragment : Fragment() {

    private val viewModel: DemoViewModel by activityViewModels()
    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!
    private var castAdapter: CastAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieInfoBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.movie = viewModel.liveSelectedMovie.value

        val verticalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        castAdapter = CastAdapter()

        binding.movieCast.adapter = castAdapter
        binding.movieCast.layoutManager = verticalLayoutManager

        viewModel.liveSelectedMovie.value?.let {
            castAdapter?.updateData(it.cast)
        }

        Picasso.get().load(viewModel.liveSelectedMovie.value?.posterPath).into(binding.moviePosterImage)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        castAdapter = null
    }

    companion object {
        fun newInstance() = MovieInfoFragment()
    }
}
