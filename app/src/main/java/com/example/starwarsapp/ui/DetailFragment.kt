package com.example.starwarsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.starwarsapp.adapter.FilmAdapter
import com.example.starwarsapp.databinding.FragmentDetailBinding
import com.example.starwarsapp.utils.Resource
import com.example.starwarsapp.viewmodel.CharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: CharacterDetailViewModel by viewModels()
    private val filmsAdapter: FilmAdapter by lazy {
        FilmAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.details.observe(viewLifecycleOwner) { result ->

            binding.apply {
                fullNameValue.text = result.name
                skinColorValue.text = result.skinColor
                hairColorValue.text = result.hairColor
                heightValue.text = result.height
                massValue.text = result.mass
                eyeColorValue.text = result.eyeColor
                genderValue.text = result.gender
                birthYearValue.text = result.birthYear
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.filmDetailResponse.collect { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.filmProgressBar.isVisible = false
                        filmsAdapter.submitList(event.data)
                        binding.filmList.adapter = filmsAdapter
                    }
                    is Resource.Failure -> {
                        binding.filmProgressBar.isVisible = false
                        binding.textViewFilmsError.isVisible = true
                        binding.textViewFilmsError.text = event.message
                    }
                    is Resource.Loading -> {
                        binding.filmProgressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homeWorldResponse.collect { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.filmProgressBar.isVisible = false
                        binding.homeWorldValue.text = event.data!!.name
                    }
                    is Resource.Failure -> {
                        binding.filmProgressBar.isVisible = false
                        binding.homeWorldValue.text = event.message
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.filmProgressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
}