package com.mikdadstream.app.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MikdadApi {

    @GET("api/modelles/models")
    suspend fun models(): ModelsEnvelope

    @GET("api/modelles/categories")
    suspend fun categories(@Query("model_id") modelId: String): Envelope<CategoriesData>

    @GET("api/modelles/videos")
    suspend fun videos(
        @Query("model_id") modelId: String,
        @Query("category_id") categoryId: String,
        @Query("page") page: Int = 1,
    ): Envelope<VideosData>

    @GET("api/modelles/detail")
    suspend fun detail(
        @Query("model_id") modelId: String,
        @Query("id") id: String,
    ): Envelope<DetailData>

    @GET("api/modelles/source")
    suspend fun source(
        @Query("model_id") modelId: String,
        @Query("episode_id") episodeId: String,
        @Query("id") id: String,
    ): Envelope<SourceData>

    @GET("api/modelles/download")
    suspend fun download(
        @Query("model_id") modelId: String,
        @Query("episode_id") episodeId: String,
        @Query("id") id: String,
    ): Envelope<SourceData>

    @POST("api/modelles/search")
    suspend fun search(@Body body: SearchBody): Envelope<SearchData>

    @GET("api/app/account/status")
    suspend fun accountStatus(): Envelope<AccountData>

    @POST("api/app/auth/nonce")
    suspend fun nonce(@Body body: NonceBody): Envelope<NonceData>

    @POST("api/app/auth/login/google-account")
    suspend fun login(@Body body: LoginBody): Envelope<TokenData>

    @POST("api/app/auth/refresh-token")
    suspend fun refresh(@Body body: RefreshBody): Envelope<TokenData>

    @POST("api/app/auth/device/pairing")
    suspend fun devicePairing(@Body body: PairingBody): Envelope<PairingData>

    @GET("api/app/auth/device/status")
    suspend fun deviceStatus(
        @Query("device_code") deviceCode: String,
        @Query("app_instance_id") appInstanceId: String,
    ): Envelope<DeviceStatusData>

    @POST("api/app/auth/device/approve")
    suspend fun deviceApprove(@Body body: ApproveBody): Envelope<Any>

    @POST("api/app/auth/device/exchange")
    suspend fun deviceExchange(@Body body: ExchangeBody): Envelope<TokenData>

    @GET("api/app/moviebox/plan")
    suspend fun movieboxPlan(@Query("cursor") cursor: Int): Envelope<MovieboxPlanData>

    @POST("api/app/moviebox/submit")
    suspend fun movieboxSubmit(@Body body: MovieboxSubmitBody): Envelope<MovieboxSubmitResult>
}
