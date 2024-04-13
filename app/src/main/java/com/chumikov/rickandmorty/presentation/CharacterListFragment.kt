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
import androidx.paging.LoadState
import com.chumikov.rickandmorty.databinding.FragmentCharacterListBinding
import com.chumikov.rickandmorty.presentation.adapters.CharacterPageAdapter
import com.chumikov.rickandmorty.presentation.adapters.LoadStateAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterListFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<CharacterListViewModel> { viewModelFactory }

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentCharacterListBinding? = null

    private val binding: FragmentCharacterListBinding
        get() = _binding ?: throw RuntimeException("FragmentCharacterListBinding is null")



    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.rvCharacterList
        val loader = binding.progrBarFirstScreen
        val retryButton = binding.retryButton

        val listAdapter = CharacterPageAdapter(requireContext())
        val loadStateAdapter = LoadStateAdapter { listAdapter.retry() }
        recyclerView.adapter = listAdapter.withLoadStateFooter(loadStateAdapter)

        retryButton.setOnClickListener { listAdapter.retry() }

       lifecycleScope.launch {
            listAdapter.loadStateFlow.collect { loadState ->
                recyclerView.isInvisible = loadState.refresh !is LoadState.NotLoading
                loader.isInvisible = loadState.refresh !is LoadState.Loading
                retryButton.isInvisible = loadState.refresh !is LoadState.Error
            }
        }

        listAdapter.onCharacterClickListener = {
            findNavController().navigate(
                CharacterListFragmentDirections
                    .actionCharacterListFragmentToCharacterDetailsFragment(it)
            )
        }

        viewModel.characters.observe(viewLifecycleOwner) {
            listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}