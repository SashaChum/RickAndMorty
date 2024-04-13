package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.chumikov.rickandmorty.databinding.FragmentEpisodeListBinding
import com.chumikov.rickandmorty.presentation.adapters.EpisodeListAdapter
import javax.inject.Inject


class EpisodesFragment : Fragment() {

    private val args by navArgs<EpisodesFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<EpisodesViewModel> { viewModelFactory }

    private val component by lazy {
        (requireActivity()
            .application as MyApplication)
            .component
            .episodeFragmentComponentFactory()
            .create(args.episodesId.toList())
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

        val loader = binding.progrBarEpisodeScreen
        val retryButton = binding.retryButton
        val recyclerView = binding.rvEpisodes

        retryButton.setOnClickListener {
            viewModel.retry()
        }

        val adapter = EpisodeListAdapter(requireContext())
        binding.rvEpisodes.adapter = adapter

        viewModel.status.observe(viewLifecycleOwner) {status ->
            when(status) {
                is EpisodesLoadingState.Error -> {
                    retryButton.visibility = View.VISIBLE
                    recyclerView.visibility = View.INVISIBLE
                    loader.visibility = View.INVISIBLE
                }
                is EpisodesLoadingState.Loading -> {
                    loader.visibility = View.VISIBLE
                    recyclerView.visibility = View.INVISIBLE
                    retryButton.visibility = View.INVISIBLE
                }
                is EpisodesLoadingState.Success -> {
                    recyclerView.visibility = View.VISIBLE
                    loader.visibility = View.INVISIBLE
                    retryButton.visibility = View.INVISIBLE
                    adapter.submitList(status.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}