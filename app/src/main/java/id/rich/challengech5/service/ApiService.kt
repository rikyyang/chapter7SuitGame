package id.rich.challengech5.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/v1/auth/register")
    fun registerNewUser(@Body response: PostRegisterNewUserRequest): Call<BaseResponse>

}