package id.rich.challengech5.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import id.rich.challengech5.database.UserDao
import id.rich.challengech5.service.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var userDao: UserDao
    private val apiService: ApiService by lazy { createApiService() }

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

//    private val _userGender = MutableLiveData<Gender>()
//    val userGender: LiveData<Gender> get() = _userGender

    private val _navigateToSettingActivity = MutableLiveData<Unit>()
    val navigateToSettingActivity: LiveData<Unit> get() = _navigateToSettingActivity

    private val _showDialog = MutableLiveData<Unit>()
    val showDialog: LiveData<Unit> get() = _showDialog

    private val _dismissDialog = MutableLiveData<Unit>()
    val dismissDialog: LiveData<Unit> get() = _dismissDialog

    private val _setResultAndFinish = MutableLiveData<Int>()
    val setResultAndFinish: LiveData<Int> get() = _setResultAndFinish

    fun init(userDao: UserDao) {
        this.userDao = userDao
    }

    private fun createApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://binar-gdd-cc8.herokuapp.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun fetchUserProfile(username: String) {
        viewModelScope.launch {
            try {
                val user = apiService.getUser(username)
                _userName.value = user.toString()
//                _userGender.value = user.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onThemeSettingClicked() {
        _navigateToSettingActivity.value = Unit
    }

    fun onGameHistoryClicked() {

    }

    fun onLogOutClicked() {
        _showDialog.value = Unit
    }

    fun onLogOutConfirmation() {
        clearSession()
        _dismissDialog.value = Unit
        _setResultAndFinish.value = Activity.RESULT_OK
    }

    fun onLogOutCanceled() {
        _dismissDialog.value = Unit
    }

    fun clearSession() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


}
