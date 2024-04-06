package com.chumikov.rickandmorty.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chumikov.rickandmorty.databinding.FragmentCharacterListBinding
import com.chumikov.rickandmorty.presentation.adapters.CharacterListAdapter

class CharacterListFragment: Fragment() {

    private var _binding: FragmentCharacterListBinding? = null

    private val binding: FragmentCharacterListBinding
        get() = _binding ?: throw RuntimeException("FragmentCharacterListBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(
                requireActivity().application)
        )[CharacterListViewModel::class.java]
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

        val adapter = CharacterListAdapter()
        binding.rvCharacterList.adapter = adapter

        viewModel.characters.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}