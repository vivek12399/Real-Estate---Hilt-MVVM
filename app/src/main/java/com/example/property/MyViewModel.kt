package com.example.property

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.property.repository.UserRepository
import com.example.property.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val loginResponseLiveData:LiveData<NetworkResult<LoginResponse>>
        get() = repository.loginResponseLiveData

    val registerResponseLiveData:LiveData<NetworkResult<UserRegisterResponse>>
        get() = repository.registerResponseLiveData

    val profileResponseLiveData:LiveData<NetworkResult<BuilderRegisterResponse>>
        get() = repository.builderProfileResponseLiveData

    val getProfileResponseLiveData:LiveData<NetworkResult<BuilderProfileResponse>>
        get() = repository.getBuilderProfileResponse

    val getTypeResponseLiveData:LiveData<NetworkResult<TypeResponse>>
        get() = repository.propertyTypeResponse

    val propertyAddResponseLiveData:LiveData<NetworkResult<ProeprtyAddResponse>>
        get() = repository.propertyAddResponse

    val propertyDelResponseLiveData:LiveData<NetworkResult<ProeprtyAddResponse>>
        get() = repository.propertyDelResponse

    val getProfileUpdateResponse:LiveData<NetworkResult<ProeprtyAddResponse>>
        get() = repository.builderProfileUpdateResponse

    val propertyResponseLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = repository.propertyResponseLiveData

    val propertyCompleteResponseLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = repository.propertyCompleteResponseLiveData
    val propertyRunningResponseLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = repository.runningPropertiesLiveData
    val propertyUpcomingResponseLiveData: LiveData<NetworkResult<PropertyResponse>>
        get() = repository.upcomingPropertiesLiveData
    val builderListResponseLiveData: LiveData<NetworkResult<BuilderListResponse>>
        get() = repository.builderListLiveData

    fun getBuilderList(params: HashMap<String, Any>){
        viewModelScope.launch {
            repository.getBuilderList(params)
        }
    }
    fun getBuilderProfile(tokenRequest: TokenReq2){
        viewModelScope.launch {
            repository.getBuilderProfile(tokenRequest)
        }
    }
    fun updateBuilderProfile(tokenRequest: BuilderProfileUpdate){
        viewModelScope.launch {
            repository.updateBuilderProfile(tokenRequest)
        }
    }
    fun getPropertyType(tokenRequest: TokenRequest){
        viewModelScope.launch {
            repository.getPropertyType(tokenRequest)
        }
    }

    fun builderProfile(builderProfileRequest: BuilderProfileRequest){
        viewModelScope.launch {
            repository.builderProfile(builderProfileRequest)
        }
    }
    fun addProperty(propertyRequest: PropertyRequest){
        viewModelScope.launch {
            repository.addProperty(propertyRequest)
        }
    }
    fun userLogin(loginRequest: LoginRequest){
        viewModelScope.launch {
            repository.userLogin(loginRequest)
        }
    }

    fun userRegister(userRegister: UserRegisterRequest){
        viewModelScope.launch {
            repository.userRegister(userRegister)
        }
    }
    fun getProperty(params: HashMap<String, Any>) {
        viewModelScope.launch {
            repository.getProperty(params)
        }
    }
    fun getCompleteProperty(params: HashMap<String, Any>) {
        viewModelScope.launch {
            repository.getCompleteProperty(params)
        }
    }
    fun getRunningProperty(params: HashMap<String, Any>) {
        viewModelScope.launch {
            repository.getRunningProperty(params)
        }
    }
    fun getUpcomingProperty(params: HashMap<String, Any>) {
        viewModelScope.launch {
            repository.getUpcomingProperty(params)
        }
    }
    fun propertyDelete(params: HashMap<String, Any>) {
        viewModelScope.launch {
            repository.delPro(params)
        }
    }
}