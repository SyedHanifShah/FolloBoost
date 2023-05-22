package com.hanisoft.bestar.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userModel: UserModel)

    @Delete
    suspend fun deleteUser(userModel: UserModel)

    @Query("SELECT * FROM UserModel WHERE uniqueId =:uniqueId")
    suspend fun getUserById(uniqueId: String):UserModel?

    @Query("SELECT * FROM UserModel")
    fun getAllUser(): LiveData<List<UserModel>>

}