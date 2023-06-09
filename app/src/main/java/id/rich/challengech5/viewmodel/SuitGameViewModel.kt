package id.rich.challengech5.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rich.challengech5.repository.SuitGameRepository
import id.rich.challengech5.service.PostRegisterNewUserRequest

class SuitGameViewModel(private val repository: SuitGameRepository) : ViewModel() {
    val onSuccess = MutableLiveData<Boolean>()
    fun registerNewUser(email: String, username: String, password: String) {
        val dataRegister = PostRegisterNewUserRequest(email, username, password)
        repository.registerNewUser(dataRegister, object : SuitGameRepository.Listener{
            override fun onRegisterNewUserSuccess(sccMessage: String) {
                onSuccess.value = true
            }

            override fun onRegisterNewUserFailure(errMessage: String) {
                onSuccess.value = false
            }

        })
    }
}