package com.hanisoft.bestar.ui.shop

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.data.database.HistoryModel
import com.hanisoft.bestar.domain.repository.BeStarRepository
import com.hanisoft.bestar.domain.repository.DataBaseRepository
import com.hanisoft.bestar.ui.profile.ProfileEvent
import com.hanisoft.bestar.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: BeStarRepository,
    @ApplicationContext application: Context,
    private val dataBaseRepository: DataBaseRepository
) :ViewModel(){
    private val dataStore = BeStarDataStore(application)
    private val _authToken = dataStore.getAuthToken
    var authToken by mutableStateOf("")
    var packagesList by mutableStateOf <List<com.hanisoft.bestar.domain.item.Package?>>(emptyList())
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
           val res = repository.getPackages()
            if (res.isSuccessful){
                packagesList = res.body()?.packages!!
            }

        }


    }


    fun onEvent(event: ShopEvent){
        when(event){
            is ShopEvent.OnbackClick -> {
            sentUiEvent(UiEvent.PopBackStack)

            }
        }
    }



    private fun sentUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }





}