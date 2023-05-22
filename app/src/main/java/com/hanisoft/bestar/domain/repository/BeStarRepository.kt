package com.hanisoft.bestar.domain.repository


import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hanisoft.bestar.data.database.CompagionModel
import com.hanisoft.bestar.data.database.UserModel
import com.hanisoft.bestar.domain.item.*
import com.hanisoft.bestar.util.AddFollowerBody
import com.hanisoft.bestar.util.BoostActivationBody
import com.hanisoft.bestar.util.LoginBody
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface BeStarRepository {
    suspend fun login(body: LoginBody): Response<LoginData>

    suspend fun getCompagion(): Response<CompaionData>
    suspend fun getBoostPackages(): Response<BoostPackageData>
    suspend fun getPackages(): Response<GetPackagesData>
    suspend fun getHistory(authToken: String): Response<HistoryData>
    suspend fun addToCompagion(authToken: String,body: BoostActivationBody ): Response<AddToCompagionData>
    suspend fun getUserInfo(authToken: String): Response<UserData>
    suspend fun addFollower(authToken: String,addFollowerBody: AddFollowerBody): Response<AddFollower>


}