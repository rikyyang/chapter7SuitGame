package id.rich.challengech5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    private val _showVideoDialog = MutableLiveData<String>()
    val showVideoDialog: LiveData<String> = _showVideoDialog


    fun openVideoDialog(videoUrl: String) {
        _showVideoDialog.value = videoUrl
    }

}
