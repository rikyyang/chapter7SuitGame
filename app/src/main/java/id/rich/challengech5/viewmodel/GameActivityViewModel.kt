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


    init {
        _gameResult.value = GameResult.DRAW
        val gameHistoryDao = GameDatabase.getInstance(application).gameHistoryDao()
        gameHistoryRepository = GameHistoryRepository(gameHistoryDao)
    }

    fun startGame(playerName: String, enemyName: String) {
        val player = Player()
        val enemy = Enemy()
        val game = GameBuilder(player, enemy)

        val playerChoice = player.getPoint()
        val enemyChoice = enemy.getPoint()

        val result = determineResult(playerChoice, enemyChoice)
        player.setStatus(result)
        enemy.setStatus(result)

        val gameHistory = GameHistory(0,playerName,enemyName,result)
        insertGameHistory(gameHistory)

        _gameResult.postValue(result)


    }

    fun determineResult(playerChoice: Int, enemyChoice: Int): GameResult {
        return when {
            playerChoice == enemyChoice -> GameResult.DRAW
            (playerChoice == 0 && enemyChoice == 2) ||
                    (playerChoice == 1 && enemyChoice == 0) ||
                    (playerChoice == 2 && enemyChoice == 1) -> GameResult.WIN
            else -> GameResult.LOSE
        }
    }

    fun insertGameHistory(gameHistory: GameHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            gameHistoryRepository.insertGameHistory(gameHistory)
        }
    }

    fun getAllGameHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val gameHistoryList = gameHistoryRepository.getAllGameHistory()
            _gameHistoryList.postValue(gameHistoryList)
        }
    }

}