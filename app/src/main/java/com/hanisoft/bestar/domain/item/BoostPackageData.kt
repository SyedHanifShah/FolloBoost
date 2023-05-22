package com.hanisoft.bestar.domain.item

data class BoostPackageData(
    val boostPackages: List<BoostPackage>
)

data class BoostPackage(
    val __v: Int,
    val _id: String,
    val date: String,
    val coins: String,
    val follower: String,
    val name: String
)