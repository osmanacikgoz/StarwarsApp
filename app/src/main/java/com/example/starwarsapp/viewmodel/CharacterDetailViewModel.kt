package com.example.starwarsapp.viewmodel

import androidx.lifecycle.*
import com.example.starwarsapp.data.repositories.CharacterRepository
import com.example.starwarsapp.model.Character
import com.example.starwarsapp.model.FilmResponse
import com.example.starwarsapp.model.HomeWorldResponse
import com.example.starwarsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
 @HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val myArguments = savedStateHandle.get<Character>("characterDetails")

    private val _details = MutableLiveData<Character>()
    val details: LiveData<Character>
        get() = _details

    private val _homeWorld = MutableStateFlow<Resource<HomeWorldResponse>>(Resource.Empty())
    val homeWorldResponse: StateFlow<Resource<HomeWorldResponse>>
        get() = _homeWorld

    private val _filmDetails = MutableStateFlow<Resource<List<FilmResponse>>>(Resource.Empty())
    val filmDetailResponse: StateFlow<Resource<List<FilmResponse>>>
        get() = _filmDetails

    private val filmList: ArrayList<FilmResponse> = ArrayList()

    init {
        _details.value = myArguments!!
        getHomeWorldData(myArguments.homeworld)
        getFilmData()

    }


    private fun getFilmData() {
        myArguments?.films?.forEach { film ->
            viewModelScope.launch(Dispatchers.IO) {
                _filmDetails.value = Resource.Loading()

                when (val characterDetail = characterRepository.getFilm(film)) {
                    is Resource.Failure -> {
                        _filmDetails.value =
                            Resource.Failure(characterDetail.message!!)
                    }
                    is Resource.Success -> {
                        if (characterDetail.data == null) {
                            _filmDetails.value = Resource.Failure("Empty Film List")
                        } else {
                            filmList.add(characterDetail.data)
                            _filmDetails.value = Resource.Success(filmList)
                        }
                    }
                    else -> {
                        _filmDetails.value = Resource.Failure("ERROR")
                    }
                }
            }
        }
    }

    private fun getHomeWorldData(homeWorldUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _filmDetails.value = Resource.Loading()

            when (val homeWorldResponse = characterRepository.getHomeWorld(homeWorldUrl)) {
                is Resource.Failure -> {
                    _homeWorld.value = Resource.Failure(homeWorldResponse.message!!)
                }
                is Resource.Success -> {
                    if (homeWorldResponse.data == null) {
                        _homeWorld.value = Resource.Failure("N/A")
                    } else {
                        _homeWorld.value = Resource.Success(homeWorldResponse.data)
                    }
                }
                else -> {
                    _homeWorld.value = Resource.Failure("ERROR")
                }
            }
        }
    }
}
