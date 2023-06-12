package id.rich.challengech5.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/v1/auth/register")
    fun registerNewUser(@Body response: PostRegisterNewUserRequest): Call<BaseResponse>

    @GET("api/v1/users")
    fun getData(): Call<BaseResponse>

}