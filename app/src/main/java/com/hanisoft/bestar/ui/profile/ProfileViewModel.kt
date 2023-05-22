package com.hanisoft.bestar.ui.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.data.database.UserModel
import com.hanisoft.bestar.domain.repository.BeStarRepository
import com.hanisoft.bestar.domain.repository.DataBaseRepository
import com.hanisoft.bestar.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.sokomishalov.skraper.client.okhttp.OkHttpSkraperClient
import ru.sokomishalov.skraper.model.PageInfo
import ru.sokomishalov.skraper.provider.tiktok.TikTokSkraper
import ru.sokomishalov.skraper.provider.tiktok.getUserInfo
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: BeStarRepository,
    @ApplicationContext application: Context,
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val dataStore = BeStarDataStore(application)
    private val _uniqueId = dataStore.getUserUniqueId
    var getUserData by mutableStateOf<UserModel?>(null)

    var uniqueId by mutableStateOf("")
    var userInfo by mutableStateOf<PageInfo?>(null)


    init {
        viewModelScope.launch {
            _uniqueId.collect {
                try {
                    val client = OkHttpSkraperClient()
                    val sktr = TikTokSkraper(client)
                    if (it != null) {
                        uniqueId = it
                        sktr.getUserInfo(it)?.let {user->
                          userInfo = user
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        }



        viewModelScope.launch {
            try {
                getUserData = dataBaseRepository.getUserById(uniqueId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.OnSignOuClick ->{
                viewModelScope.launch {
                    dataStore.saveUniqueId("")
                    dataStore.savedAuthToken("")
                }

            }
            is ProfileEvent.OnbackClick-> {
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