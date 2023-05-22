package com.hanisoft.bestar.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CompagionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompagion(compagionModel: CompagionModel)

    @Delete
    suspend fun deleteCompagion(compagionModel: CompagionModel)

    @Query("SELECT * FROM CompagionModel WHERE id =:id")
    suspend fun getCompagionById(id: Int):CompagionModel?

    @Query("SELECT * FROM CompagionModel")
    fun getAllCompagion(): Flow<List<CompagionModel>>


}