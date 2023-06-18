package id.rich.challengech5.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rich.challengech5.repository.SuitGameRepository
import id.rich.challengech5.service.PostRegisterNewUserRequest

class SuitGameViewModel(private val repository: SuitGameRepository) : ViewModel() {
    val onSuccess = MutableLiveData<Boolean>()
    val errCause = MutableLiveData<String>()
    fun registerNewUser(email: String, username: String, password: String) {
        if (email.isEmpty()){
            errCause.value = "Email cannot be empty"
            onSuccess.value = false
        }
        else if(username.isEmpty()){
            errCause.value = "Username cannot be empty"
            onSuccess.value = false
        }
        else if(password.isEmpty()){
            errCause.value = "Password cannot be empty"
            onSuccess.value = false
        }
        else{
            val dataRegister = PostRegisterNewUserRequest(email, username, password)
            repository.registerNewUser(dataRegister, object : SuitGameRepository.Listener{
                override fun onRegisterNewUserSuccess(sccMessage: String) {
                    onSuccess.value = true
                }

                override fun onRegisterNewUserFailure(errMessage: String) {

                    if(errMessage == "422"){
                        errCause.value = "Email or Username has been already used!"
                    }
                    else{
                        errCause.value = errMessage
                    }

                    onSuccess.value = false
                }

            })
        }
    }
}