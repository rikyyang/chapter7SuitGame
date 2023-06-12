package id.rich.challengech5.service

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("data")
    val data: Any?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("id")
    val _id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String
)


