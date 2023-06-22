package id.rich.challengech5.service

import com.google.gson.annotations.SerializedName

data class UserJson(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String
)


