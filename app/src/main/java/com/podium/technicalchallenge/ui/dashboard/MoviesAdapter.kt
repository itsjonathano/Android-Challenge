package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.ItemMovieBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.home.HomepageFragment
import com.squareup.picasso.Picasso

class MoviesAdapter(val fragment: HomepageFragment): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var items: List<MovieEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(data: List<MovieEntity>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.binding.movie = movie
        Picasso.get().load(movie.posterPath).into(holder.binding.movieImg)
        holder.binding.movieImg.setOnClickListener {
            fragment.displayMovieInfo(movie)
        }
    }

}