package id.rich.challengech5.`class`

import id.rich.challengech5.model.GameResult

abstract class PlayerImplementation {
    abstract fun setPoint(choose: Int)
    abstract fun getPoint(): Int
    abstract fun setStatus(status: GameResult)
    abstract fun getStatus(): GameResult
}