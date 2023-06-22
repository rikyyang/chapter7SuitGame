package id.rich.challengech5.repository

import id.rich.challengech5.database.GameHistoryDao
import id.rich.challengech5.model.GameHistory

class GameHistoryRepository(private val gameHistoryDao: GameHistoryDao) {

    suspend fun insertGameHistory(gameHistory: GameHistory) {
        gameHistoryDao.insertGameHistory(gameHistory)
    }

    suspend fun getAllGameHistory(): List<GameHistory> {
        return gameHistoryDao.getAllGameHistory()
    }

}