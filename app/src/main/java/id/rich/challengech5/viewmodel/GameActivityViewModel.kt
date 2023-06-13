package id.rich.challengech5.viewmodel

import android.app.Application
import androidx.lifecycle.*
import id.rich.challengech5.`class`.Enemy
import id.rich.challengech5.`class`.GameBuilder
import id.rich.challengech5.`class`.Player
import id.rich.challengech5.database.GameDatabase
import id.rich.challengech5.model.GameHistory
import id.rich.challengech5.model.GameResult
import id.rich.challengech5.repository.GameHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameActivityViewModel(application: Application): AndroidViewModel(application)
    {

    private val gameHistoryRepository: GameHistoryRepository
    private val _gameHistoryList = MutableLiveData<List<GameHistory>>()
    val gameHistoryList: LiveData<List<GameHistory>> = _gameHistoryList

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> = _gameResult

        val player = Player()
        val enemy = Enemy()
        val game = GameBuilder(player, enemy)

    init {

        val gameHistoryDao = GameDatabase.getInstance(application).gameHistoryDao()
        gameHistoryRepository = GameHistoryRepository(gameHistoryDao)
    }

    fun startGame(playerName: String, enemyName: String,player1Choice: Int,player2choice: Int) {

        player.setPoint(player1Choice)
        enemy.setPoint(player2choice)

        game.startGame()

        val result = player.getStatus()
        enemy.setStatus(result)

        val gameHistory = GameHistory(0,playerName,enemyName,result)
        insertGameHistory(gameHistory)

        _gameResult.postValue(result)


    }


    fun insertGameHistory(gameHistory: GameHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            gameHistoryRepository.insertGameHistory(gameHistory)
            _gameResult.postValue(null)
        }
    }

    fun getAllGameHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val gameHistoryList = gameHistoryRepository.getAllGameHistory()
            _gameHistoryList.postValue(gameHistoryList)
            _gameResult.postValue(null)
        }
    }

}