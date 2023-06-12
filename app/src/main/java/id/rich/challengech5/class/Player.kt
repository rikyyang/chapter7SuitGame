package id.rich.challengech5.`class`

import id.rich.challengech5.model.GameResult


class Player: PlayerImplementation() {

    private var point = 0
    private var status = GameResult.LOSE

    override fun getPoint(): Int = point

    override fun setPoint(choose: Int){
        this.point = choose
    }

    override fun setStatus(status: GameResult) {
        this.status = status
    }

    override fun getStatus(): GameResult = status

}