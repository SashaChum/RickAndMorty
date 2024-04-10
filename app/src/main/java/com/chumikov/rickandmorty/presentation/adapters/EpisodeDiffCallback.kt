package com.chumikov.rickandmorty.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.chumikov.rickandmorty.domain.Episode

class EpisodeDiffCallback: DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}