package com.hanisoft.bestar.domain.item






data class CompaionData(
    val compagions: List<Compagion>
)

data class Compagion(
    val _id: String,
    val avatar: String,
    val currentFollowers: String,
    val currentFollowing: String,
    val date: String,
    val packageFollowers: String,
    val uniqueId: String
)


