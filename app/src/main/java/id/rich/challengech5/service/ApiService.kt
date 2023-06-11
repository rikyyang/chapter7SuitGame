package id.rich.challengech5.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/v1/auth/register")
    fun registerNewUser(@Body response: PostRegisterNewUserRequest): Call<BaseResponse>

    @GET("api/v1/users")
    suspend fun getUser(@Path("username") username: String): Call<BaseResponse>

}