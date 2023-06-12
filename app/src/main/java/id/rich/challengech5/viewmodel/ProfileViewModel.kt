package id.rich.challengech5.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import id.rich.challengech5.database.UserDao

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var userDao: UserDao

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
