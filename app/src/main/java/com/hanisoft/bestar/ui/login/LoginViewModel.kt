package com.hanisoft.bestar.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.data.database.UserModel
import com.hanisoft.bestar.domain.item.LoginData
import com.hanisoft.bestar.domain.repository.BeStarRepository
import com.hanisoft.bestar.util.LoginBody
import com.hanisoft.bestar.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.sokomishalov.skraper.client.okhttp.OkHttpSkraperClient
import ru.sokomishalov.skraper.model.PageInfo
import ru.sokomishalov.skraper.provider.tiktok.TikTokSkraper
import ru.sokomishalov.skraper.provider.tiktok.getUserInfo
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val beStarRepository: BeStarRepository,
    @ApplicationContext application: Context,
) :ViewModel(){
    var userName by mutableStateOf("")
    var authToken by mutableStateOf<LoginData?>(null)
    var loading by mutableStateOf(false)
    val dataStore = BeStarDataStore(application)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var userInfo by mutableStateOf<PageInfo?>(null)


    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.OnUserNameChange->{
                userName = event.userName
            }
            is LoginEvent.OnLoginClick->{
                if (userName.isBlank()){
                    sentUiEvent(UiEvent.ShowSnackBar(message = "User name can't be empty"))
                }else{
                    loading = true
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val client = OkHttpSkraperClient()
                            val sktr = TikTokSkraper(client)
                            sktr.getUserInfo(userName)?.let {
                                val res  = beStarRepository.login(LoginBody(
                                    nickname = it.nick!!,
                                    uniqueId = it.name!!,
                                    posts = it.statistics.posts ?: 0,
                                    follower = it.statistics.followers ?: 0,
                                    following = it.statistics.following ?: 0,
                                    avtar = it.avatar?.url ?: "",
                                    date = System.currentTimeMillis().toString()
                                ))
                                if (res.isSuccessful){
                                    authToken = res.body()
                                    userInfo = it
                                    dataStore.saveUniqueId(it.name!!)
                                    loading =  false
                                    dataStore.saveOpenLoginValue(true)

                                }else{
                                    sentUiEvent(UiEvent.ShowSnackBar(
                                        message = res.message()
                                    ))
                                    loading= false
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            loading =  false
                        }

                    }
                }

            }
            is LoginEvent.OnSelectAccount->{
                viewModelScope.launch {
                    dataStore.savedAuthToken(authToken!!.authtoken)
                }
            }
        }
    }

    private fun sentUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}
