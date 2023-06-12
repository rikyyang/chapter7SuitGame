package id.rich.challengech5.`class`

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
            player.setStatus("MENANG")
            enemy.setStatus("KALAH")
        }
        else if(calculate == 2 || calculate == -1){
            player.setStatus("KALAH")
            enemy.setStatus("MENANG")
        }
        else{
            player.setStatus("DRAW")
            enemy.setStatus("DRAW")
        }
    }
}