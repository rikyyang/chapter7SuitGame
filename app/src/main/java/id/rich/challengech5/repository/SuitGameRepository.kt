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
                    listener.onRegisterNewUserFailure("Data gagal dimuat")
                }
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful){
                        if (response.body()?.success == null){
                            listener.onRegisterNewUserFailure("Data gagal dimuat")
                        }
                        else{
                            listener.onRegisterNewUserSuccess("Data Sukses")
                        }
                    }
                    else{
                        listener.onRegisterNewUserFailure("Data gagal dimuat")
                    }
                }
            })
    }

    interface Listener{
        fun onRegisterNewUserSuccess(sccMessage: String)
        fun onRegisterNewUserFailure(errMessage: String)
    }
}