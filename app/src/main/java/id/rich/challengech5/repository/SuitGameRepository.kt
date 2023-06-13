package id.rich.challengech5.repository

import id.rich.challengech5.service.ApiService
import id.rich.challengech5.service.BaseResponse
import id.rich.challengech5.service.PostRegisterNewUserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuitGameRepository(private val apiService: ApiService) {
    fun registerNewUser(postRegisterNewUserResponse: PostRegisterNewUserRequest, listener: Listener) {
        apiService.registerNewUser(postRegisterNewUserResponse)
            .enqueue(object : Callback<BaseResponse>{
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    listener.onRegisterNewUserFailure("Failed load data")
                }
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful){
                        if (response.body()?.success == null){
                            listener.onRegisterNewUserFailure("Failed load data")
                        }
                        else{
                            if(response.body()?.success.toString() == "false"){
                                listener.onRegisterNewUserFailure("Failed load data")
                            }
                            else{
                                listener.onRegisterNewUserSuccess("Success!")
                            }
                        }
                    }
                    else{
                        listener.onRegisterNewUserFailure(response.code().toString())
                    }
                }
            })
    }

    interface Listener{
        fun onRegisterNewUserSuccess(sccMessage: String)
        fun onRegisterNewUserFailure(errMessage: String)
    }
}