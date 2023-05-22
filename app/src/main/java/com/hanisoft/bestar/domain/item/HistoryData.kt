package com.hanisoft.bestar.domain.item

data class HistoryData(
    val history:History
)

data class History(
    val __v: Int,
    val _id: String,
    val date: String,
    val gainFollwer: String,
    val packageFollwer: String,
    val packageName: String,
    val remainFollower: String,
    val status: String,
    val uniqueId: String
)