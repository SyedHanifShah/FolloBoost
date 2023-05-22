package com.hanisoft.bestar.data.remote



import com.hanisoft.bestar.domain.item.*
import com.hanisoft.bestar.util.AddFollowerBody
import com.hanisoft.bestar.util.BoostActivationBody
import com.hanisoft.bestar.util.BoostActivationHeader
import com.hanisoft.bestar.util.Constants.Companion.ADD_FOLLOWER
import com.hanisoft.bestar.util.Constants.Companion.ADD_TO_COMPAGION_ENDPOINT
import com.hanisoft.bestar.util.Constants.Companion.GET_BOOST_PACKAGE_ENDPOINT
import com.hanisoft.bestar.util.Constants.Companion.GET_COMPAGION_ENDPOINT
import com.hanisoft.bestar.util.Constants.Companion.GET_HISTORY_ENDPOINT
import com.hanisoft.bestar.util.Constants.Companion.GET_PACKAGES_ENDPOINT
import com.hanisoft.bestar.util.Constants.Companion.GET_USER_INFO_ENDPOINT
import com.hanisoft.bestar.util.Constants.Companion.LOGIN_ENDPOINT
import com.hanisoft.bestar.util.LoginBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BeStarApi {

    @POST(LOGIN_ENDPOINT)
    suspend fun Login(
        @Body loginBody: LoginBody
    ): Response<LoginData>

    @GET(GET_COMPAGION_ENDPOINT)
    suspend fun getCompagion():Response<CompaionData>

    @GET(GET_BOOST_PACKAGE_ENDPOINT)
    suspend fun getBoostPackages():Response<BoostPackageData>

    @POST(ADD_TO_COMPAGION_ENDPOINT)
    suspend fun addToCompagioon(
        @Header("auth-token") authToken:String,
        @Body boostActivationBody: BoostActivationBody
    ): Response<AddToCompagionData>

    @GET(GET_USER_INFO_ENDPOINT)
    suspend fun getUserInfo(
        @Header("auth-token") authToken:String
    ): Response<UserData>

    @POST(ADD_FOLLOWER)
    suspend fun addFollower(
        @Header("auth-token") authToken:String,
        @Body addFollowerBody: AddFollowerBody
    ): Response<AddFollower>


    @GET(GET_PACKAGES_ENDPOINT)
    suspend fun getPackages():Response<GetPackagesData>


    @GET(GET_HISTORY_ENDPOINT)
    suspend fun getHistory(
        @Header("auth-token") authToken:String
    ):Response<HistoryData>




}