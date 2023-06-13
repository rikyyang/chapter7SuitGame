package id.rich.challengech5.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("api/v1/auth/register")
    fun registerNewUser(@Body response: PostRegisterNewUserRequest): Call<BaseResponse>

    @GET("api/v1/users")
    fun getDataUserProfile(): Call<BaseResponse>

    @PUT("api/vi/users")
    fun updateDataUserProfile(_id: String, newName: String): Call<BaseResponse>

}