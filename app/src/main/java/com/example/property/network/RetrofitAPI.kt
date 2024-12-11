package com.example.property.network

import com.example.property.network.models.AuthModels.BuilderListResponse
import com.example.property.network.models.AuthModels.LoginRequest
import com.example.property.network.models.AuthModels.LoginResponse
import com.example.property.network.models.AuthModels.UserRegisterRequest
import com.example.property.network.models.AuthModels.UserRegisterResponse
import com.example.property.network.models.AuthModels.builder.BuilderProfileResponse
import com.example.property.network.models.AuthModels.builder.BuilderRegisterResponse
import com.example.property.network.models.AuthModels.builder.ProeprtyAddResponse
import com.example.property.network.models.AuthModels.builder.PropertyResponse
import com.example.property.network.models.AuthModels.builder.TokenRequest
import com.example.property.network.models.AuthModels.builder.TypeResponse
import com.example.property.network.models.AuthModels.builder.request.BuilderProfileUpdate
import com.example.property.network.models.AuthModels.builder.request.TokenReq2
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface RetrofitAPI {

    @POST("login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST("register_user")
    suspend fun userRegister(@Body registerRequest: UserRegisterRequest): Response<UserRegisterResponse>


    @Multipart
    @POST("register_builder")
    suspend fun builderProfile(
        //@PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part("username") username: RequestBody,
        @Part("company_name") company_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("mobile_no") mobile_no: RequestBody,
        @Part("password") password: RequestBody,
        @Part("owner_name") owner_name: RequestBody,
        @Part("company_objective") company_objective: RequestBody,
        @Part("city") city: RequestBody,
        @Part("achievement") achievement: RequestBody,
        @Part("year_since") year_since: RequestBody,
        @Part("experiance") experiance: RequestBody,
        @Part logo: MultipartBody.Part?,
        @Part profile: MultipartBody.Part?
    ): Response<BuilderRegisterResponse>


    @POST("builder/get_profile")
    suspend fun getBuilderProfile(@Body tokenRequest: TokenReq2): Response<BuilderProfileResponse>


    @POST("get/property_type")
    suspend fun getPropertyType(@Body tokenRequest: TokenRequest): Response<TypeResponse>


    @Multipart
    @POST("add/property")
    suspend fun addProperty(
        @Part("token") token: RequestBody,
        @Part("property_name") propertyName: RequestBody,
        @Part("price") price: RequestBody,
        @Part("built_in") buildYear: RequestBody,
        @Part("current_state") propertyStatus: RequestBody,
        @Part("description") description: RequestBody,
        @Part("facility") facalities: RequestBody,
        @Part("type") type: RequestBody,

        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
        @Part image5: MultipartBody.Part?
    ): Response<ProeprtyAddResponse>


    @POST("builder/update_profile")
    suspend fun updateBuilderProfile(
        @Body tokenRequest: BuilderProfileUpdate
    ): Response<ProeprtyAddResponse>


    @POST("get/property")
    suspend fun getProperty(@Body params: HashMap<String, Any>): Response<PropertyResponse>


    @POST("get/s_property")
    suspend fun getSProperty(@Body params: HashMap<String, Any>): Response<PropertyResponse>


    @POST("remove/property")
    suspend fun deleteProperty(@Body params: HashMap<String, Any>): Response<ProeprtyAddResponse>

    @POST("get_builder_list")
    suspend fun getBuilderList(@Body params: HashMap<String, Any>): Response<BuilderListResponse>
}

