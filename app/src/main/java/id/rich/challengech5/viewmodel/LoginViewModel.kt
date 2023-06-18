package id.rich.challengech5.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rich.challengech5.repository.LoginRepository
import id.rich.challengech5.service.PostLoginRequest

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    val onSuccess = MutableLiveData<Boolean>()
    val errCause = MutableLiveData<String>()
    val onSuccessData = MutableLiveData<Any?>()

    fun login(email:String, password: String) {
        if (email.isEmpty()) {
            errCause.value = "Email cannot be empty"
            onSuccess.value = false
        } else if (password.isEmpty()) {
            errCause.value = "Password cannot be empty"
            onSuccess.value = false
        } else {
            val dataLogin = PostLoginRequest(email, password)
            repository.login(dataLogin, object : LoginRepository.Listener {
                override fun onLoginSuccess(sccMessage: String, data: Any?) {
                    onSuccess.value = true
                    onSuccessData.value = data
                }

                override fun onLoginFailure(errMessage: String) {
                    errCause.value = errMessage
                    onSuccess.value = false
                }
            })
        }
    }
}