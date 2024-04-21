package com.chumikov.rickandmorty.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import coil.load
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.databinding.ItemCharacterBinding
import com.chumikov.rickandmorty.domain.Character


class CharacterPageAdapter(
    private val context: Context
) : PagingDataAdapter<Character, CharacterListViewHolder>(CharacterDiffCallback()) {

    var onCharacterClickListener: ( (id: Int) -> Unit )? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        val characterItem = getItem(position)
        val binding = holder.binding
        val filler = ContextCompat.getDrawable(context, R.drawable.placeholder_image)

        with(binding) {
            characterName.text = characterItem?.name
            characterPhoto.load(characterItem?.imageUrl) {
                crossfade(true)
                placeholder(filler)
                error(filler)
                fallback(filler)
            }
        }
        binding.root.setOnClickListener {
            if (characterItem != null) {
                onCharacterClickListener?.invoke(characterItem.id)
            }
        }
    }


}