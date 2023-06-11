package id.rich.challengech5.service

import com.google.gson.annotations.SerializedName

data class PostRegisterNewUserRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?
)
