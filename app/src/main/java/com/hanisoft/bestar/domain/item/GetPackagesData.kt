package com.hanisoft.bestar.domain.item

data class GetPackagesData(
val packages: List<Package>
)


data class Package(
    val name: String,
    val coins: String,
    val price: String,
    val date: String,
)
