package com.hanisoft.bestar.ui.home


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.data.database.CompagionModel
import com.hanisoft.bestar.data.database.HistoryModel
import com.hanisoft.bestar.data.database.UserModel
import com.hanisoft.bestar.domain.item.BoostPackageData
import com.hanisoft.bestar.domain.item.CompaionData
import com.hanisoft.bestar.domain.repository.BeStarRepository
import com.hanisoft.bestar.domain.repository.DataBaseRepository
import com.hanisoft.bestar.util.AddFollowerBody
import com.hanisoft.bestar.util.BoostActivationBody
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
class HomeViewModel @Inject constructor(
    private val beStarRepository: BeStarRepository,
    private val dataBaseRepository: DataBaseRepository,
    @ApplicationContext application: Context,
) : ViewModel(), DefaultLifecycleObserver {
    private val dataStore = BeStarDataStore(application)
    private val _authToken = dataStore.getAuthToken
    private val _unquieId = dataStore.getUserUniqueId
    var authToken by mutableStateOf("")
    var uniqueId by mutableStateOf("")


    var activatingBoost by mutableStateOf(false)
    var currentNumber by mutableStateOf(0)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var compagion by mutableStateOf<CompaionData?>(null)

    var _compagonFromDataBase = dataBaseRepository.getAllCompagion()
    var compagonFromDataBase by mutableStateOf<List<CompagionModel>>(emptyList())
    var boostPackagesList by mutableStateOf(BoostPackageData(emptyList()))
    var currentCompagion by mutableStateOf<CompagionModel?>(null)
    var userInfo by mutableStateOf<PageInfo?>(null)
    var followedUserInfo by mutableStateOf<PageInfo?>(null)
    var name by mutableStateOf("")
    var avatar by mutableStateOf("")
    var follower by mutableStateOf(0)
    var following by mutableStateOf(0)
    var followedUserUniqueId by mutableStateOf("")
    var _goToTitTokForFollow = dataStore.getBackFomTikTokValue
    var goToTitTokForFollow by mutableStateOf(false)
    var openAlertDialoge by mutableStateOf(false)
    var getUserData by mutableStateOf<UserModel?>(null)
    var history by mutableStateOf<HistoryModel?>(null)


    init {
        viewModelScope.launch {
            try {
                val boost = beStarRepository.getBoostPackages()
                if (boost.isSuccessful) {
                    boost.body().let {
                        if (it != null) {
                            boostPackagesList = it
                        }
                    }
                } else {
                    sentUiEvent(
                        UiEvent.ShowSnackBar(
                            message = boost.message()
                        )
                    )
                }
            } catch (e: Exception) {
            e.printStackTrace()
            }
        }


        viewModelScope.launch {
            getCompagionsApi()
        }
        viewModelScope.launch {
            _compagonFromDataBase.collect {
                compagonFromDataBase = it
                currentCompagion = compagonFromDataBase[currentNumber]
                followedUserUniqueId = currentCompagion!!.uniqueId
            }

        }

        viewModelScope.launch {
            try {
                _authToken.collect {
                    if (it != null) {
                        authToken = it
                    }
                }
            } catch (e: Exception) {
            e.printStackTrace()
            }

        }


        viewModelScope.launch {
            try {
                _unquieId.collect {
                    val client = OkHttpSkraperClient()
                    val sktr = TikTokSkraper(client)
                    if (it != null) {
                        uniqueId = it
                        sktr.getUserInfo(it)?.let {
                            userInfo = it
                            name = it.name.toString()
                            avatar = it.avatar?.url.toString()
                            follower = it.statistics.followers ?: 0
                            following = it.statistics.following ?: 0
                        }
                    }
                }
            } catch (e: Exception) {
            e.printStackTrace()
            }
        }

        viewModelScope.launch {
            _goToTitTokForFollow.collect {
                if (it != null) {
                    goToTitTokForFollow = it
                }
            }
        }



        getUserApi()


        viewModelScope.launch {
            try {
                getUserData = dataBaseRepository.getUserById(uniqueId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch {
    try {
        val res =  beStarRepository.getHistory(authToken)
        if (res.isSuccessful){
            val history = res.body()?.history
            history?.uniqueId?.let {
                HistoryModel(
                    uniqueId = it,
                    packageName = history.packageName,
                    packageFollwer = history.packageFollwer,
                    gainFollwer = history.gainFollwer,
                    remainFollower = history.remainFollower
                )
            }?.let {
                dataBaseRepository.insertHistory(
                    it
                )
            }

        }
    }catch (e:Exception){
        e.printStackTrace()
    }

        }


    viewModelScope.launch {
       dataBaseRepository.getHistoryById(uniqueId).let {
      history = it
      }
    }




    }


    val context = application
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnFollowClick -> {
                viewModelScope.launch {
                    try {
                        followedUserUniqueId.let {
                            val client = OkHttpSkraperClient()
                            val sktr = TikTokSkraper(client)
                            sktr.getUserInfo(it)?.let {
                                followedUserInfo = it
                            }
                            Log.d("TAG", "follow click:$followedUserInfo ")
                        }
                        dataStore.saveBackFomTikTokValue(value = true)
                        openTikTokProfile(context = context, username = followedUserUniqueId)
                    } catch (e: Exception) {
                        e.message
                        e.message?.let {
                            if (it.contains("TLS packet header")){
                             sentUiEvent(UiEvent.ShowSnackBar(
                                 message = "Internet connection is to slow."
                             ))
                            }
                        }
                    }
                }
            }
            is HomeEvent.OnSkipClick -> {
                whenUserInCompagion()
                nextCompagion()
            }
            is HomeEvent.OnBoostPackageClick -> {
                CoroutineScope(Dispatchers.IO).launch {
                    activatingBoost = true
                    try {
                        if (userInfo != null) {
                            val result = beStarRepository.addToCompagion(
                                authToken, BoostActivationBody(
                                    name = event.packageName,
                                    avatar = avatar,
                                    following = following,
                                    followers = follower
                                )
                            )
                            if (result.isSuccessful) {
                                val useroins = getUserData?.coins?.toInt()
                                val coins = useroins?.minus(event.coins).toString()
                                getUserData?.id?.let {
                                    UserModel(
                                        id = it,
                                        __v = getUserData!!.__v,
                                        avtar = getUserData!!.avtar,
                                        coins = coins,
                                        follower = getUserData!!.follower,
                                        following = getUserData!!.following,
                                        name = getUserData!!.name,
                                        uniqueId = getUserData!!.uniqueId,
                                        posts = getUserData!!.posts,
                                        date = getUserData!!.date
                                    )
                                }?.let {
                                    dataBaseRepository.insertUser(
                                        it
                                    )
                                }
                                    getUserData = dataBaseRepository.getUserById(uniqueId)

                                sentUiEvent(
                                    UiEvent.ShowSnackBar(
                                        message = result.body()?.message!!
                                    )
                                )
                                activatingBoost = false
                            }
                        }
                    } catch (_: Exception) {
                    }
                }
            }
        }
    }

    private fun sentUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


    private fun openTikTokProfile(username: String, context: Context) {
        val uri: Uri = Uri.parse("https://www.tiktok.com/@${username}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setPackage("com.zhiliaoapp.musically")
        context.startActivity(intent)
    }

    fun backToScreenFromTikkTok() {
        if (checkIfUserFollowOrNot(followedUserUniqueId)) {
            viewModelScope.launch {
                try {
                    val res = beStarRepository.addFollower(
                        authToken, AddFollowerBody(
                            followedUserUniqueId
                        )
                    )
                    if (res.isSuccessful) {
                        val coins = (getUserData?.coins?.toInt()?.plus(1)).toString()
                        getUserData?.id?.let {
                            UserModel(
                                id = it,
                                __v = getUserData!!.__v,
                                avtar = getUserData!!.avtar,
                                coins = coins,
                                follower = getUserData!!.follower,
                                following = getUserData!!.following,
                                name = getUserData!!.name,
                                uniqueId = getUserData!!.uniqueId,
                                posts = getUserData!!.posts,
                                date = getUserData!!.date
                            )
                        }?.let {
                            dataBaseRepository.insertUser(
                                it
                            )
                        }
                        getUserData = dataBaseRepository.getUserById(uniqueId)
                        if (currentCompagion?.packageFollowers == "1") {
                            dataBaseRepository.deleteCompagion(currentCompagion!!)
                        } else {
                            nextCompagion()
                        }
                        dataStore.saveBackFomTikTokValue(value = false)
                        sentUiEvent(UiEvent.ShowSnackBar(
                            message = "Follow detected"
                        ))
                    }else{
                        sentUiEvent(UiEvent.ShowSnackBar(
                            message = res.message()
                        ))
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
                getCompagionsApi()
            }
        } else {
            viewModelScope.launch {
                dataStore.saveBackFomTikTokValue(value = false)
                dataStore.saveOpenDialogalue(true)
            }
    }

    }

    fun checkIfUserFollowOrNot(compagionUserUniqueId: String): Boolean {
        var res = false
        viewModelScope.launch {
            try {
                val client = OkHttpSkraperClient()
                val sktr = TikTokSkraper(client)
                sktr.getUserInfo(compagionUserUniqueId)?.let {
                    val oldFollowers = followedUserInfo?.statistics?.followers
                    val newfollowers = it.statistics.followers
                    if (oldFollowers != null) {
                        if (newfollowers == oldFollowers + 1) {
                            res = true
                        }

                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return res
    }


    fun nextCompagion() {
        if (compagonFromDataBase.size  > currentNumber + 1) {
            currentNumber++
            currentCompagion = compagonFromDataBase[currentNumber]
            followedUserUniqueId = currentCompagion!!.uniqueId
        } else {
            currentNumber = 0
            currentCompagion = compagonFromDataBase[currentNumber]
            followedUserUniqueId = currentCompagion!!.uniqueId
        }
    }


    fun whenUserInCompagion() {
        if (uniqueId == followedUserUniqueId) {
            if (compagonFromDataBase.size > currentNumber + 1) {
                currentNumber++
                currentCompagion = compagonFromDataBase[currentNumber]
                followedUserUniqueId = currentCompagion!!.uniqueId
            } else {
                currentNumber = 0
                currentCompagion = compagonFromDataBase[currentNumber]
                followedUserUniqueId = currentCompagion!!.uniqueId
            }
        }

    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (goToTitTokForFollow) {
            backToScreenFromTikkTok()
        }
    }


    private fun getUserApi() {
        viewModelScope.launch {
            try {
                val res = beStarRepository.getUserInfo(authToken)
                if (res.isSuccessful) {
                    res.body().let {
                        if (it != null) {
                            dataBaseRepository.insertUser(
                                UserModel(
                                    id = it.user._id,
                                    __v = it.user.__v,
                                    avtar = it.user.avtar,
                                    coins = it.user.coins,
                                    follower = it.user.follower,
                                    following = it.user.following,
                                    name = it.user.name,
                                    uniqueId = it.user.uniqueId,
                                    posts = it.user.posts,
                                    date = it.user.date
                                )
                            )
                        }
                    }
                } else {
                    sentUiEvent(
                        UiEvent.ShowSnackBar(
                            message = res.message()
                        )
                    )
                }
            } catch (_: Exception) {
            }
        }
    }


    private suspend fun getCompagionsApi() {
        try {
            val comp = beStarRepository.getCompagion()
            if (comp.isSuccessful) {
                compagion = comp.body()
                if (compagion?.compagions != null){
                    compagion?.compagions?.forEach {
                        dataBaseRepository.insertCompagion(
                            CompagionModel(
                                id = it._id,
                                avatar = it.avatar,
                                currentFollowers = it.currentFollowers,
                                currentFollowing = it.currentFollowing,
                                packageFollowers = it.packageFollowers,
                                uniqueId = it.uniqueId,
                                date = it.date
                            )
                        )
                    }
                }
            } else {
                sentUiEvent(
                    UiEvent.ShowSnackBar(
                        message = comp.message()
                    )
                )
            }
        } catch (e: Exception) {
        e.printStackTrace()
        }

    }


}

