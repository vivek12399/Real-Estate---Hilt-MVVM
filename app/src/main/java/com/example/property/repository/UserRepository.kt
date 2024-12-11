package com.example.property.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.property.network.RetrofitAPI
import com.example.property.network.models.AuthModels.BuilderListResponse
import com.example.property.network.models.AuthModels.LoginRequest
import com.example.property.network.models.AuthModels.LoginResponse
import com.example.property.network.models.AuthModels.UserRegisterRequest
import com.example.property.network.models.AuthModels.UserRegisterResponse
import com.example.property.network.models.AuthModels.builder.BuilderProfileRequest
import com.example.property.network.models.AuthModels.builder.BuilderProfileResponse
import com.example.property.network.models.AuthModels.builder.BuilderRegisterResponse
import com.example.property.network.models.AuthModels.builder.ProeprtyAddResponse
import com.example.property.network.models.AuthModels.builder.PropertyResponse
import com.example.property.network.models.AuthModels.builder.TokenRequest
import com.example.property.network.models.AuthModels.builder.TypeResponse
import com.example.property.network.models.AuthModels.builder.request.BuilderProfileUpdate
import com.example.property.network.models.AuthModels.builder.request.PropertyRequest
import com.example.property.network.models.AuthModels.builder.request.TokenReq2
import com.example.property.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class UserRepository @Inject constructor(private val retrofitApi:RetrofitAPI) {

    private val _loginResponseLiveData=MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResponseLiveData: LiveData<NetworkResult<LoginResponse>>
    get() = _loginResponseLiveData
    private val _registerResponseLiveData=MutableLiveData<NetworkResult<UserRegisterResponse>>()
    val registerResponseLiveData: LiveData<NetworkResult<UserRegisterResponse>>
        get() = _registerResponseLiveData

    private val _builderProfileLiveData=MutableLiveData<NetworkResult<BuilderRegisterResponse>>()
    val builderProfileResponseLiveData: LiveData<NetworkResult<BuilderRegisterResponse>>
        get() = _builderProfileLiveData

    private val _propertyAddResponse=MutableLiveData<NetworkResult<ProeprtyAddResponse>>()
    val propertyAddResponse: LiveData<NetworkResult<ProeprtyAddResponse>>
        get() = _propertyAddResponse
    private val _propertyDelResponse=MutableLiveData<NetworkResult<ProeprtyAddResponse>>()
    val propertyDelResponse: LiveData<NetworkResult<ProeprtyAddResponse>>
        get() = _propertyDelResponse

    private val _builderProfileResponse = MutableLiveData<NetworkResult<BuilderProfileResponse>>()
    val getBuilderProfileResponse:LiveData<NetworkResult<BuilderProfileResponse>>
        get() = _builderProfileResponse

    private val propertyTypeLiveData = MutableLiveData<NetworkResult<TypeResponse>>()
    val propertyTypeResponse:LiveData<NetworkResult<TypeResponse>>
        get() = propertyTypeLiveData
    private val _builderProfileUpdateResponse = MutableLiveData<NetworkResult<ProeprtyAddResponse>>()
    val builderProfileUpdateResponse:LiveData<NetworkResult<ProeprtyAddResponse>>
        get() = _builderProfileUpdateResponse

    private val _propertyResponseLiveData = MutableLiveData<NetworkResult<PropertyResponse>>()
    val propertyResponseLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = _propertyResponseLiveData

    private val _propertyCompleteResponseLiveData = MutableLiveData<NetworkResult<PropertyResponse>>()
    val propertyCompleteResponseLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = _propertyCompleteResponseLiveData
    private val _runningPropertiesLiveData = MutableLiveData<NetworkResult<PropertyResponse>>()
    val runningPropertiesLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = _runningPropertiesLiveData

    private val _upcomingPropertiesLiveData = MutableLiveData<NetworkResult<PropertyResponse>>()
    val upcomingPropertiesLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = _upcomingPropertiesLiveData

    private val builderListResponse = MutableLiveData<NetworkResult<BuilderListResponse>>()
    val builderListLiveData: LiveData<NetworkResult<BuilderListResponse>>
        get() = builderListResponse


    suspend fun getProperty(params: HashMap<String, Any>) {

        _propertyResponseLiveData.postValue(NetworkResult.Loading())
        val response = retrofitApi.getProperty(params)
        if (response.isSuccessful && response.body() != null) {
            _propertyResponseLiveData.value = NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            _propertyResponseLiveData.value = NetworkResult.Error(response.errorBody()!!.toString())
        } else {
            _propertyResponseLiveData.value = NetworkResult.Error("Error")
        }
    }
    suspend fun delPro(params: HashMap<String, Any>) {
        _propertyDelResponse.postValue(NetworkResult.Loading())
        val response = retrofitApi.deleteProperty(params)
        if (response.isSuccessful && response.body() != null) {
            _propertyDelResponse.value = NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            _propertyDelResponse.value = NetworkResult.Error(response.errorBody()!!.toString())
        } else {
            _propertyDelResponse.value = NetworkResult.Error("Error")
        }
    }
    suspend fun getCompleteProperty(params: HashMap<String, Any>) {
        _propertyCompleteResponseLiveData.postValue(NetworkResult.Loading())
        val response = retrofitApi.getSProperty(params)
        if (response.isSuccessful && response.body() != null) {
            _propertyCompleteResponseLiveData.value = NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            _propertyCompleteResponseLiveData.value = NetworkResult.Error(response.errorBody()!!.toString())
        } else {
            _propertyCompleteResponseLiveData.value = NetworkResult.Error("Error")
        }
    }

    suspend fun getRunningProperty(params: HashMap<String, Any>) {
        _runningPropertiesLiveData.postValue(NetworkResult.Loading())
        val response = retrofitApi.getSProperty(params)
        if (response.isSuccessful && response.body() != null) {
            _runningPropertiesLiveData.value = NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            _runningPropertiesLiveData.value = NetworkResult.Error(response.errorBody()!!.toString())
        } else {
            _propertyCompleteResponseLiveData.value = NetworkResult.Error("Error")
        }
    }
    suspend fun getUpcomingProperty(params: HashMap<String, Any>) {
        _upcomingPropertiesLiveData.postValue(NetworkResult.Loading())
        val response = retrofitApi.getSProperty(params)
        if (response.isSuccessful && response.body() != null) {
            _upcomingPropertiesLiveData.value = NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            _upcomingPropertiesLiveData.value = NetworkResult.Error(response.errorBody()!!.toString())
        } else {
            _upcomingPropertiesLiveData.value = NetworkResult.Error("Error")
        }
    }
    suspend fun updateBuilderProfile(tokenReq: BuilderProfileUpdate){
        _builderProfileUpdateResponse.postValue(NetworkResult.Loading())
        val response = retrofitApi.updateBuilderProfile(tokenReq)
        if (response.isSuccessful && response.body()!=null){
            _builderProfileUpdateResponse.value=NetworkResult.Success(response.body()!!)
        }else if (response.errorBody()!=null){
            _builderProfileUpdateResponse.value=NetworkResult.Error(response.errorBody()!!.toString())
        }else{
            _builderProfileUpdateResponse.value=NetworkResult.Error("Error")
        }
    }
    suspend fun getBuilderProfile(tokenReq:TokenReq2){
        _builderProfileResponse.postValue(NetworkResult.Loading())
        val response = retrofitApi.getBuilderProfile(tokenReq)
        if (response.isSuccessful && response.body()!=null){
            _builderProfileResponse.value=NetworkResult.Success(response.body()!!)
        }else if (response.errorBody()!=null){
            _builderProfileResponse.value=NetworkResult.Error(response.errorBody()!!.toString())
        }else{
            _builderProfileResponse.value=NetworkResult.Error("Error")
        }
    }
    suspend fun getPropertyType(tokenReq:TokenRequest){
        propertyTypeLiveData.postValue(NetworkResult.Loading())
        val response = retrofitApi.getPropertyType(tokenReq)
        if (response.isSuccessful && response.body()!=null){
            propertyTypeLiveData.value=NetworkResult.Success(response.body()!!)
        }else if (response.errorBody()!=null){
            propertyTypeLiveData.value=NetworkResult.Error(response.errorBody()!!.toString())
        }else{
            propertyTypeLiveData.value=NetworkResult.Error("Error")
        }
    }
    suspend fun addProperty(request: PropertyRequest) {
        _propertyAddResponse.postValue(NetworkResult.Loading())

        val tokenPart = createRequestBody(request.token)
        val propertyNamePart = createRequestBody(request.propertyName)
        val propertyTypePart = createRequestBody(request.propertyType)
        val pricePart = createRequestBody(request.price)
        val buildYearPart = createRequestBody(request.buildYear)
        val propertyStatusPart = createRequestBody(request.propertyStatus)
        val descriptionPart = createRequestBody(request.description)
        val facilitiesPart = createRequestBody(request.facalities)

        // Convert images to MultipartBody.Part
        val image1Part = createImagePart(request.image1, "image1")
        val image2Part = createImagePart(request.image2, "image2")
        val image3Part = createImagePart(request.image3, "image3")
        val image4Part = createImagePart(request.image4, "image4")
        val image5Part = createImagePart(request.image5, "image5")

        // Make the API request
        val response = retrofitApi.addProperty(
            tokenPart,
            propertyNamePart,
            pricePart,
            buildYearPart,
            propertyStatusPart,
            descriptionPart,
            facilitiesPart,
            propertyTypePart,
            image1Part,
            image2Part,
            image3Part,
            image4Part,
            image5Part
        )

        if (response.isSuccessful && response.body() != null) {
            _propertyAddResponse.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _propertyAddResponse.postValue(NetworkResult.Error(response.errorBody()!!.string()))
        } else {
            _propertyAddResponse.postValue(NetworkResult.Error("Error"))
        }
    }

    private fun createRequestBody(content: String): RequestBody =
        RequestBody.create("text/plain".toMediaTypeOrNull(), content)

    private fun createImagePart(bitmap: Bitmap?, name: String): MultipartBody.Part? {
        return bitmap?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            val byteArray = stream.toByteArray()
            val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(name, "$name.jpg", requestBody)
        }
    }


    suspend fun builderProfile(builderProfileRequest: BuilderProfileRequest) {
        _builderProfileLiveData.postValue(NetworkResult.Loading())
        val userName = createRequestBody(builderProfileRequest.userName)
        val companyName = createRequestBody(builderProfileRequest.builderCompanyName)
        val email = createRequestBody(builderProfileRequest.email)
        val mobileNo = createRequestBody(builderProfileRequest.mobileNumber)
        val password = createRequestBody(builderProfileRequest.password)
        val ownerName = createRequestBody(builderProfileRequest.ownerName)
        val companyObjective = createRequestBody(builderProfileRequest.companyObjective)
        val city = createRequestBody(builderProfileRequest.companyCity)
        val achievement = createRequestBody(builderProfileRequest.companyAchievement)
        val yearSince = createRequestBody(builderProfileRequest.companySince.toString())
        val experience = createRequestBody(builderProfileRequest.companyExperience.toString())

        // Create image parts for logo and profile picture
        val logoPart = createImagePart(builderProfileRequest.logoBitmap, "logo")
        val profilePart = createImagePart(builderProfileRequest.profileBitmap, "profile_pic")

        // Make API request
        val response = retrofitApi.builderProfile(
            userName,
            companyName,
            email,
            mobileNo,
            password,
            ownerName,
            companyObjective,
            city,
            achievement,
            yearSince,
            experience,
            logoPart,
            profilePart
        )


        if (response.isSuccessful && response.body() != null) {
            _builderProfileLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _builderProfileLiveData.postValue(NetworkResult.Error(response.errorBody()!!.string()))
        } else {
            _builderProfileLiveData.postValue(NetworkResult.Error("Error"))
        }
    }

    suspend fun userLogin(loginRequest: LoginRequest){
        _loginResponseLiveData.postValue(NetworkResult.Loading())
        val response = retrofitApi.userLogin(loginRequest)

        if (response.isSuccessful && response.body()!=null){
            _loginResponseLiveData.value=NetworkResult.Success(response.body()!!)
        }else if (response.errorBody()!=null){
            _loginResponseLiveData.value=NetworkResult.Error(response.errorBody()!!.toString())
        }else{
            _loginResponseLiveData.value=NetworkResult.Error("Error")
        }
    }
    suspend fun userRegister(userRegister: UserRegisterRequest){
        _registerResponseLiveData.postValue(NetworkResult.Loading())
        val response = retrofitApi.userRegister(userRegister)
        if (response.isSuccessful && response.body()!=null){
            _registerResponseLiveData.value=NetworkResult.Success(response.body()!!)
        }else if (response.errorBody()!=null){
            _registerResponseLiveData.value=NetworkResult.Error(response.errorBody()!!.toString())
        }else{
            _registerResponseLiveData.value=NetworkResult.Error("Error")
        }
    }

    suspend fun getBuilderList(params: HashMap<String, Any>) {
        builderListResponse.postValue(NetworkResult.Loading())
        val response = retrofitApi.getBuilderList(params)
        if (response.isSuccessful && response.body() != null) {
            builderListResponse.value = NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            builderListResponse.value = NetworkResult.Error(response.errorBody()!!.toString())
        } else {
            builderListResponse.value = NetworkResult.Error("Error")
        }
    }

    private fun createImagePart(name: String, bitmap: Bitmap?): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, "$name.png", requestBody)
    }
}