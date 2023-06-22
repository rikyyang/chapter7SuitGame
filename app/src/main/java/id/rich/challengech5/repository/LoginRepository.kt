package id.rich.challengech5.repository

import id.rich.challengech5.service.ApiService
import id.rich.challengech5.service.BaseResponse
import id.rich.challengech5.service.PostLoginRequest
import id.rich.challengech5.service.UserJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {

    fun login(postLoginResponse: PostLoginRequest, listener: Listener) {
        apiService.login(postLoginResponse)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful){
                        if (response.body()?.success == null){
                            listener.onLoginFailure("Failed to load data")
                        }
                        else{
                            if(response.body()?.success.toString() == "false"){
                                listener.onLoginFailure("Failed to load data")
                            }
                            else{
                                listener.onLoginSuccess("Success!",
                                    response.body()?.data
                                )
                            }
                        }
                    }
                    else{
                        listener.onLoginFailure(response.code().toString())
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    listener.onLoginFailure("Failed to load data")
                }

            })
    }

    interface Listener {
        fun onLoginSuccess(sccMessage: String, sccData: Any?)

        fun onLoginFailure(errMessage: String)
    }
}