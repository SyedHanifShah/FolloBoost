package com.hanisoft.bestar.data.repository

import android.util.Log
import com.hanisoft.bestar.data.remote.BeStarApi
import com.hanisoft.bestar.domain.item.*
import com.hanisoft.bestar.domain.repository.BeStarRepository
import com.hanisoft.bestar.util.AddFollowerBody
import com.hanisoft.bestar.util.BoostActivationBody
import com.hanisoft.bestar.util.BoostActivationHeader
import com.hanisoft.bestar.util.LoginBody
import retrofit2.Response
import javax.inject.Inject

class BeStarRepositoryImpl @Inject constructor(
    private val api: BeStarApi
):BeStarRepository{
    override suspend fun login(body: LoginBody):Response<LoginData> {
        return api.Login(body)
}

    override suspend fun getCompagion(): Response<CompaionData> {
        return api.getCompagion()
    }

    override suspend fun getBoostPackages(): Response<BoostPackageData> {
        return api.getBoostPackages()
    }

    override suspend fun getPackages(): Response<GetPackagesData> {
        return api.getPackages()
    }

    override suspend fun getHistory(authToken: String): Response<HistoryData> {
        return api.getHistory(authToken)
    }

    override suspend fun addToCompagion(authToken: String ,body: BoostActivationBody, ):Response<AddToCompagionData> {
        return  api.addToCompagioon(authToken = authToken, boostActivationBody = body)
    }

    override suspend fun getUserInfo(authToken: String): Response<UserData> {
     return api.getUserInfo(authToken)
    }

    override suspend fun addFollower(authToken: String, addFollowerBody: AddFollowerBody): Response<AddFollower> {
       return api.addFollower(authToken, addFollowerBody)
    }


}