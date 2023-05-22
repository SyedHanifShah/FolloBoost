package com.hanisoft.bestar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [CompagionModel::class, UserModel::class, HistoryModel::class], version = 1
)
abstract class BeStarDataBase :RoomDatabase(){
abstract val compagionDao:CompagionDao
abstract val userDao: UserDao
abstract val historyDao:HistoryDao
}