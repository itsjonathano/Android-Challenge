package com.podium.technicalchallenge.ui.movieInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
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

        //val verticalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val layoutManager = GridLayoutManager(requireContext(), 2)

        castAdapter = CastAdapter()

        binding.movieCast.adapter = castAdapter

        binding.movieCast.layoutManager = layoutManager

        viewModel.liveSelectedMovie.value?.let {
            castAdapter?.updateData(it.cast)
        }

        binding.genreButtonsList.removeAllViews()


        viewModel.liveSelectedMovie.value?.genres?.forEach {
            val chip = layoutInflater.inflate(R.layout.standalone_chip, binding.genreButtonsList, false) as Chip
            chip.text = it
            chip.setOnClickListener {
                viewModel.liveGenresSelected.value = listOf(chip.text.toString())
                findNavController().navigate(R.id.discoverFragment)
            }
            binding.genreButtonsList.addView(chip as View)
        }

        Picasso.get().load(viewModel.liveSelectedMovie.value?.posterPath).into(binding.moviePosterImage)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun genreLinkFormat(genres: List<String>) {

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
