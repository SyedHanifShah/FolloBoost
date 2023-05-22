package com.hanisoft.bestar.ui.login

import com.hanisoft.bestar.ui.profile.ProfileEvent

sealed class LoginEvent{
    data class OnUserNameChange(val userName:String):LoginEvent()
    object OnLoginClick:LoginEvent()
    object OnSelectAccount: LoginEvent()
}
