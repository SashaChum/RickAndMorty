package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.databinding.FragmentCharacterDetailsBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {

    private val args by navArgs<CharacterDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<CharacterDetailsViewModel> { viewModelFactory }

    private val component by lazy {
        (requireActivity()
            .application as MyApplication)
            .component
            .characterListFragmentComponentFactory()
            .create(args.characterId)
    }

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding: FragmentCharacterDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentCharacterDetailsBinding == null")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filler = R.drawable.placeholder_image

        val toolbar = binding.toolbar
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        val retryButton = binding.retryButton
        retryButton.setOnClickListener { viewModel.retry() }

        lifecycleScope.launch {
            viewModel.status.collect { state ->

                binding.mainCardView.isInvisible = state !is CharacterDetailsLoadingState.Success
                binding.loader.isInvisible = state !is CharacterDetailsLoadingState.Loading
                toolbar.isInvisible = state !is CharacterDetailsLoadingState.Success
                retryButton.isInvisible = state !is CharacterDetailsLoadingState.Error

                if (state is CharacterDetailsLoadingState.Success) {
                    binding.characterPhoto.load(state.data.imageUrl) {
                        placeholder(filler)
                        error(filler)
                        fallback(filler)
                    }
                    binding.characterName.text = String.format(
                        getString(R.string.name_template), state.data.name
                    )
                    binding.characterLocation.text = String.format(
                        getString(R.string.location_template), state.data.location
                    )
                    binding.characterSpecies.text = String.format(
                        getString(R.string.species_template), state.data.species
                    )
                    binding.characterStatus.text = String.format(
                        getString(R.string.status_template), state.data.status
                    )
                    toEpisodesScreen(state)
                }
            }
        }

    }

    private fun toEpisodesScreen(state: CharacterDetailsLoadingState.Success) {
        binding.episodesButton.setOnClickListener {
            findNavController().navigate(
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToEpisodeFragment(
                    state.data.episodes.toIntArray()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}