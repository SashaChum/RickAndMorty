package com.chumikov.rickandmorty.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import coil.load
import com.chumikov.rickandmorty.databinding.ItemCharacterBinding
import com.chumikov.rickandmorty.domain.Character


class CharacterPageAdapter :
    PagingDataAdapter<Character, CharacterListViewHolder>(CharacterDiffCallback()) {

    var onCharacterClickListener: ( (id: Int) -> Unit )? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        val characterItem = getItem(position)  // тут возвращает  Character?
        val binding = holder.binding
        with(binding) {
            characterName.text = characterItem?.name
            characterPhoto.load(characterItem?.imageUrl)
        }
        binding.root.setOnClickListener {
            if (characterItem != null) {
                onCharacterClickListener?.invoke(characterItem.id)
            }
        }
    }


}