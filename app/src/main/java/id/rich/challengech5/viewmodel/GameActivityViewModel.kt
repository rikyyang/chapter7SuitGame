package id.rich.challengech5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rich.challengech5.model.GameHistory
import id.rich.challengech5.repository.GameHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameActivityViewModel(private val gameHistoryRepository: GameHistoryRepository): ViewModel() {

    private val _gameHistoryList = MutableLiveData<List<GameHistory>>()
    val gameHistoryList: LiveData<List<GameHistory>> = _gameHistoryList

    fun insertGameHistory(gameHistory: GameHistory){
        viewModelScope.launch(Dispatchers.IO) {
            gameHistoryRepository.insertGameHistory((gameHistory))
        }
    }

    fun getAllGameHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val gameHistoryList = gameHistoryRepository.getAllGameHistory()
            _gameHistoryList.postValue(gameHistoryList)
        }
    }

}