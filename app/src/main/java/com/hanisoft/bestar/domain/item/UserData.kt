package com.hanisoft.bestar.domain.item

data class UserData(
    val user: User
)


data class User(
    val __v: Int,
    val _id: String,
    val avtar: String,
    val coins: String,
    val date: String,
    val follower: String,
    val following: String,
    val name: String,
    val posts: String,
    val uniqueId: String
)