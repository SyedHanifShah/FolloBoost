package com.hanisoft.bestar.domain.repository

import androidx.lifecycle.LiveData
import com.hanisoft.bestar.data.database.*
import kotlinx.coroutines.flow.Flow

class DataBaseRepositoryImpl(
  private val compagionDao: CompagionDao,
  private val userDao: UserDao,
  private val historyDao: HistoryDao

):DataBaseRepository {
    override suspend fun insertCompagion(compagionModel: CompagionModel) {
        compagionDao.insertCompagion(compagionModel)
    }

    override suspend fun deleteCompagion(compagionModel: CompagionModel) {
        compagionDao.deleteCompagion(compagionModel)
    }

    override suspend fun getCompagionById(id: Int): CompagionModel? {
       return compagionDao.getCompagionById(id)
    }

    override fun getAllCompagion(): Flow<List<CompagionModel>> {
        return compagionDao.getAllCompagion()
    }

    override suspend fun insertUser(userModel: UserModel) {
        userDao.insertUser(userModel)
    }

    override suspend fun deleteUser(userModel: UserModel) {
        userDao.deleteUser(userModel)
    }

    override suspend fun getUserById(uniqueId: String): UserModel? {
        return userDao.getUserById(uniqueId)
    }

    override fun getAllUser(): LiveData<List<UserModel>> {
        return userDao.getAllUser()
    }

    override suspend fun insertHistory(historyModel: HistoryModel) {
        historyDao.insertHistory(historyModel)
    }

    override suspend fun deleteHistory(historyModel: HistoryModel) {
        historyDao.deleteHistory(historyModel)
    }

    override suspend fun getHistoryById(uniqueId: String): HistoryModel? {
        return historyDao.getHistoryById(uniqueId)
    }

    override fun getAllHistory(): LiveData<List<HistoryModel>> {
        return historyDao.getAllHistory()
    }
}