package com.hanisoft.bestar.ui.home

import com.hanisoft.bestar.domain.item.BoostPackage

sealed class HomeEvent{
    object OnFollowClick :HomeEvent()
    object OnSkipClick:HomeEvent()
    data class OnBoostPackageClick(val packageName:String, val coins: Int):HomeEvent()

}
