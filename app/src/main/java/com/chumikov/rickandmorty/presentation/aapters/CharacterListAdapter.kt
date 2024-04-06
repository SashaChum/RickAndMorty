package com.chumikov.rickandmorty.presentation.aapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.chumikov.rickandmorty.databinding.ItemCharacterBinding
import com.chumikov.rickandmorty.domain.Character


class CharacterListAdapter :
    ListAdapter<Character, CharacterListViewHolder>(CharacterDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent,false
        )
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        val characterItem = getItem(position)
        with(holder.binding) {
            characterName.text = characterItem.name
        }
    }


}