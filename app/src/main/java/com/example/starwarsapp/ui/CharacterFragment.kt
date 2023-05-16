package com.example.starwarsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.starwarsapp.adapter.CharactersAdapter
import com.example.starwarsapp.databinding.FragmentCharacterBinding
import com.example.starwarsapp.utils.hideKeyboard
import com.example.starwarsapp.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private val viewModel: CharacterViewModel by viewModels()

    private val characterAdapter: CharactersAdapter by lazy {
        CharactersAdapter(CharactersAdapter.OnClickListener { character ->
            val action =
                CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
            binding.searchView.editText?.setText("")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setEndIconOnClickListener {
            setupObserver(binding.searchView.editText?.text.toString())
            binding.progress.isVisible = true
            hideKeyboard()
        }
        setupAdapter()
    }


    private fun setupObserver(search: String) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getCharacters(search).collect {
                characterAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setupAdapter() {
        binding.characterList.adapter = characterAdapter

        characterAdapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh is LoadState.Loading) {
                if (characterAdapter.snapshot().isEmpty()) {
                    binding.progress.isVisible = true
                }
                binding.textViewError.isVisible = false
            } else {
                binding.progress.isVisible = false
            }
        }
    }


}

