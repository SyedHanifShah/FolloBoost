package com.hanisoft.bestar.data.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyModel: HistoryModel)

    @Delete
    suspend fun deleteHistory(historyModel: HistoryModel)

    @Query("SELECT * FROM HistoryModel WHERE uniqueId =:uniqueId")
    suspend fun getHistoryById(uniqueId: String):HistoryModel?

    @Query("SELECT * FROM HistoryModel")
    fun getAllHistory(): LiveData<List<HistoryModel>>


}