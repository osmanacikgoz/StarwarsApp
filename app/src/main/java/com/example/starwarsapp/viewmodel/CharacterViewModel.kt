package com.example.starwarsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.starwarsapp.data.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.starwarsapp.model.*

class CharacterViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {
        fun getCharacters(search:String):Flow<PagingData<Character>>{
            return characterRepository.getCharacter(search).cachedIn(viewModelScope)
        }
}