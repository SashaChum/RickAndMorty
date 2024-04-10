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


class EpisodeFragment : Fragment() {

    private val args by navArgs<EpisodeFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<EpisodeViewModel> { viewModelFactory }

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
        val adapter = EpisodeListAdapter(requireContext())
        binding.rvEpisodes.adapter = adapter
        viewModel.episodeList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}