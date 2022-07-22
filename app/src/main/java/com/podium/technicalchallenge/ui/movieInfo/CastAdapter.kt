package com.podium.technicalchallenge.ui.movieInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.ItemCastMemberBinding
import com.podium.technicalchallenge.entity.CastMember
import com.squareup.picasso.Picasso

class CastAdapter(): RecyclerView.Adapter<CastAdapter.CastMemberViewHolder>() {

    var items: List<CastMember> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMemberViewHolder {
        return CastMemberViewHolder(
            ItemCastMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CastMemberViewHolder(val binding: ItemCastMemberBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(data: List<CastMember>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CastMemberViewHolder, position: Int) {
        val cast = items[position]
        holder.binding.castMember = cast
        try {
            Picasso.get().load(cast.profilePath).into(holder.binding.portraitImg)
        }
        catch (e: IllegalArgumentException) {
            // Found this error when looking at 100% Wold. Rupert Degas' Name was blank
            Log.e("Error", "Couldn't load cast member image")
        }
    }

}