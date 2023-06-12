package id.rich.challengech5.`class`

import id.rich.challengech5.model.GameResult
import kotlin.random.Random

class GameBuilder(val player: Player, val enemy: Enemy): DeclareGame() {

    override fun computerChoose(){
        val comChoose = Random.nextInt(0,3)
        enemy.setPoint(comChoose)
    }

    override fun startGame() {
        val player1Point = player.getPoint()
        val enemyPoint = enemy.getPoint()
        val calculate = player1Point - enemyPoint

        if (calculate == 1 || calculate == -2){
            player.setStatus(GameResult.WIN)
            enemy.setStatus(GameResult.LOSE)
        }
        else if(calculate == 2 || calculate == -1){
            player.setStatus(GameResult.LOSE)
            enemy.setStatus(GameResult.WIN)
        }
        else{
            player.setStatus(GameResult.DRAW)
            enemy.setStatus(GameResult.DRAW)
        }
    }
}