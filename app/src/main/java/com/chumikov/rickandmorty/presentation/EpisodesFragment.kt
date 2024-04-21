package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chumikov.rickandmorty.databinding.FragmentEpisodeListBinding
import com.chumikov.rickandmorty.presentation.adapters.EpisodeListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


class EpisodesFragment : Fragment() {

    private val args by navArgs<EpisodesFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: EpisodesViewModel.Factory

    private val viewModel by getViewModel<EpisodesViewModel> {
        viewModelFactory.get(args.episodesId.toList())
    }

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding: FragmentEpisodeListBinding
        get() = _binding ?: throw RuntimeException("FragmentEpisodeListBinding == null")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retryButton = binding.retryButton
        retryButton.setOnClickListener { viewModel.retry() }

        val toolbar = binding.toolbar
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        val adapter = EpisodeListAdapter(requireContext())
        binding.rvEpisodes.adapter = adapter

        lifecycleScope.launch {
            viewModel.status.collect { state ->
                toolbar.isInvisible = state !is EpisodesLoadingState.Success
                retryButton.isInvisible = state !is EpisodesLoadingState.Error
                binding.rvEpisodes.isInvisible = state !is EpisodesLoadingState.Success
                binding.loader.isInvisible = state !is EpisodesLoadingState.Loading

                if (state is EpisodesLoadingState.Success) adapter.submitList(state.data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}