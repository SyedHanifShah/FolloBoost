package com.hanisoft.bestar.domain.item

import com.hanisoft.bestar.data.remote.LoginDto


data class LoginData(
    val authtoken:String
)
fun LoginDto.toLoginData() = LoginData(authtoken)
