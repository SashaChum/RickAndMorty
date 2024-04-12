package com.chumikov.rickandmorty.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.databinding.LoadStateFooterItemBinding

class LoadStateViewHolder(
    private val binding: LoadStateFooterItemBinding,
    retry: () -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
           binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.load_state_footer_item, parent, false)
            val binding = LoadStateFooterItemBinding.bind(view)
            return LoadStateViewHolder(binding, retry)
        }
    }
}