package com.hanisoft.bestar.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hanisoft.bestar.data.database.CompagionModel
import com.hanisoft.bestar.data.database.HistoryModel
import com.hanisoft.bestar.data.database.UserModel
import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {


    suspend fun insertCompagion(compagionModel: CompagionModel)


    suspend fun deleteCompagion(compagionModel: CompagionModel)


    suspend fun getCompagionById(id: Int): CompagionModel?


    fun getAllCompagion(): Flow<List<CompagionModel>>


    suspend fun insertUser(userModel: UserModel)

    suspend fun deleteUser(userModel: UserModel)

    suspend fun getUserById(uniqueId: String): UserModel?

    fun getAllUser(): LiveData<List<UserModel>>



    suspend fun insertHistory(historyModel: HistoryModel)

    suspend fun deleteHistory(historyModel: HistoryModel)

    suspend fun getHistoryById(uniqueId: String):HistoryModel?

    fun getAllHistory(): LiveData<List<HistoryModel>>

}