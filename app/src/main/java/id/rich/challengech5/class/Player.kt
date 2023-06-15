package id.rich.challengech5.`class`


class Player: PlayerImplementation() {

    private var point = 0
    private var status = "KALAH"

    override fun getPoint(): Int = point

    override fun setPoint(choose: Int){
        this.point = choose
    }

    override fun setStatus(status: String) {
        this.status = status
    }

    override fun getStatus(): String = status

}