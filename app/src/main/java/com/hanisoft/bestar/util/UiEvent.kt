package com.hanisoft.bestar.util

import com.hanisoft.bestar.domain.item.BoostPackage
import com.hanisoft.bestar.domain.item.Compagion


sealed class UiEvent{
    object PopBackStack:UiEvent()
    data class ShowSnackBar(val message:String,  val action: String? = null):UiEvent()
    data class Navigate(val route:String):UiEvent()

}
