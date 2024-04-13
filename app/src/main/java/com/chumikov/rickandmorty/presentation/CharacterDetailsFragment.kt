package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.databinding.FragmentCharacterDetailsBinding
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
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView = binding.mainCardView
        val loader = binding.progrBarDetailsScreen
        val retryButton = binding.retryButton
        val imageView =  binding.characterPhoto

        retryButton.setOnClickListener {
            viewModel.retry()
        }

        val nameTemplate = getString(R.string.name_template)
        val locationTemplate = getString(R.string.location_template)
        val speciesTemplate = getString(R.string.species_template)
        val statusTemplate = getString(R.string.status_template)
        val filler = R.drawable.placeholder_image

        viewModel.status.observe(viewLifecycleOwner) { state ->
            when(state) {
                is CharacterDetailsLoadingState.Error -> {
                    retryButton.visibility = View.VISIBLE
                    cardView.visibility = View.INVISIBLE
                    loader.visibility = View.INVISIBLE
                }
                is CharacterDetailsLoadingState.Loading -> {
                    loader.visibility = View.VISIBLE
                    cardView.visibility = View.INVISIBLE
                    retryButton.visibility = View.INVISIBLE
                }
                is CharacterDetailsLoadingState.Success -> {
                    cardView.visibility = View.VISIBLE
                    loader.visibility = View.INVISIBLE
                    retryButton.visibility = View.INVISIBLE

                    imageView.load(state.data.imageUrl) {
                        placeholder(filler)
                        error(filler)
                        fallback(filler)
                    }
                    binding.characterName.text = String.format(
                        nameTemplate, state.data.name
                    )
                    binding.characterLocation.text = String.format(
                        locationTemplate, state.data.location
                    )
                    binding.characterSpecies.text = String.format(
                        speciesTemplate, state.data.species
                    )
                    binding.characterStatus.text = String.format(
                        statusTemplate, state.data.status
                    )

                    binding.episodesButton.setOnClickListener {
                        findNavController().navigate(
                            CharacterDetailsFragmentDirections
                                .actionCharacterDetailsFragmentToEpisodeFragment(
                                    state.data.episodes.toIntArray()
                                )
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}