package com.chumikov.rickandmorty.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chumikov.rickandmorty.databinding.FragmentCharacterListBinding
import com.chumikov.rickandmorty.presentation.adapters.CharacterListAdapter
import javax.inject.Inject

class CharacterListFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: CharacterListViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory)[CharacterListViewModel::class.java]

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