package id.rich.challengech5.service

import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)
