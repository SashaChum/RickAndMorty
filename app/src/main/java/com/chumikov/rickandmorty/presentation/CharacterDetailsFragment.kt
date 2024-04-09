package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        val nameTemplate = getString(R.string.name_template)
        val locationTemplate = getString(R.string.location_template)
        val speciesTemplate = getString(R.string.species_template)
        val statusTemplate = getString(R.string.status_template)

        viewModel.characterDetails.observe(viewLifecycleOwner) {
            with(binding) {
                characterPhoto.load(it.imageUrl)
                characterName.text = String.format(nameTemplate, it.name)
                characterLocation.text = String.format(locationTemplate, it.location)
                characterSpecies.text = String.format(speciesTemplate, it.species)
                characterStatus.text = String.format(statusTemplate, it.status)

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}