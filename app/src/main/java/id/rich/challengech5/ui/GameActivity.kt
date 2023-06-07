package id.rich.challengech5.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.rich.challengech5.R
import id.rich.challengech5.databinding.GameBinding
import kotlin.random.Random


class GameActivity : AppCompatActivity() {

    private lateinit var binding: GameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playername = intent.getStringExtra("player_name")
        val enemyname = intent.getStringExtra("enemy")

        val player = Player()
        val enemy = Enemy()
        val game = GameBuilder(player, enemy)
        var result = ""
        var isEnemyChoose= false

        with(binding) {

            tvP1.setText(playername)
            tvEnemy.setText(enemyname)

            fun setEnableButtonP1(active: Boolean){
                ivBatup1.isEnabled = active
                ivKertasp1.isEnabled = active
                ivGuntingp1.isEnabled = active
            }

            fun setEnableButtonEnemy(active: Boolean){
                ivBatuenemy.isEnabled = active
                ivKertasenemy.isEnabled = active
                ivGuntingenemy.isEnabled = active
            }

            fun restart(){
                ivBatup1.setBackgroundResource(0)
                ivKertasp1.setBackgroundResource(0)
                ivGuntingp1.setBackgroundResource(0)
                ivBatuenemy.setBackgroundResource(0)
                ivKertasenemy.setBackgroundResource(0)
                ivGuntingenemy.setBackgroundResource(0)
                setEnableButtonP1(true)
                setEnableButtonEnemy(true)
                isEnemyChoose = false
            }

            fun openDialog(result: String){
                val builder = AlertDialog.Builder(this@GameActivity, R.style.CustomAlertDialog)
                    .create()
                val view = layoutInflater.inflate(R.layout.dialog_result,null)
                val buttonPlayAgain = view.findViewById<Button>(R.id.bt_dialogplayagain)
                val buttonBack = view.findViewById<Button>(R.id.bt_dialogback)
                val tvResult = view.findViewById<TextView>(R.id.tv_dialogresult)
                builder.setView(view)

                tvResult.setText(result)

                buttonPlayAgain.setOnClickListener {
                    builder.dismiss()
                    restart()
                }
                buttonBack.setOnClickListener {
                    builder.dismiss()
                    finish()
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }

            fun showResult(){
                if(isEnemyChoose){
                    if (player.getStatus() == "MENANG"){
                        result = "$playername\nMENANG!"
                    }
                    else if(enemy.getStatus() == "MENANG"){
                        result = "$enemyname\nMENANG!"
                    }
                    else{
                        result = "SERI!"
                    }
                    openDialog(result)
                }
            }

            fun start(){
                if (enemyname == "CPU"){
                    game.computerChoose()

                    Handler(Looper.getMainLooper()).postDelayed({
                        if(enemy.getPoint() == 2){
                            ivBatuenemy.setBackgroundResource(R.drawable.background_btnclick)
                            Toast.makeText(this@GameActivity,"$enemyname Memilih Batu",Toast.LENGTH_SHORT).show()
                            isEnemyChoose = true
                            game.startGame()
                            showResult()
                        }
                        if(enemy.getPoint() == 1) {
                            ivGuntingenemy.setBackgroundResource(R.drawable.background_btnclick)
                            Toast.makeText(this@GameActivity,"$enemyname Memilih Gunting",Toast.LENGTH_SHORT).show()
                            isEnemyChoose = true
                            game.startGame()
                            showResult()
                        }
                        if (enemy.getPoint() == 0){
                            ivKertasenemy.setBackgroundResource(R.drawable.background_btnclick)
                            Toast.makeText(this@GameActivity,"$enemyname Memilih Kertas",Toast.LENGTH_SHORT).show()
                            isEnemyChoose = true
                            game.startGame()
                            showResult()
                        }
                    },1000)
                }
                else{
                    ivBatuenemy.setOnClickListener {
                        ivBatuenemy.setBackgroundResource(R.drawable.background_btnclick)
                        setEnableButtonEnemy(false)
                        enemy.setPoint(2)
                        Toast.makeText(this@GameActivity,"$enemyname Memilih Batu",Toast.LENGTH_SHORT).show()
                        game.startGame()
                        isEnemyChoose = true
                        showResult()
                    }

                    ivKertasenemy.setOnClickListener {
                        ivKertasenemy.setBackgroundResource(R.drawable.background_btnclick)
                        setEnableButtonEnemy(false)
                        enemy.setPoint(0)
                        Toast.makeText(this@GameActivity,"$enemyname Memilih Kertas",Toast.LENGTH_SHORT).show()
                        game.startGame()
                        isEnemyChoose = true
                        showResult()
                    }

                    ivGuntingenemy.setOnClickListener {
                        ivGuntingenemy.setBackgroundResource(R.drawable.background_btnclick)
                        setEnableButtonEnemy(false)
                        enemy.setPoint(1)
                        Toast.makeText(this@GameActivity,"$enemyname Memilih Gunting",Toast.LENGTH_SHORT).show()
                        game.startGame()
                        isEnemyChoose = true
                        showResult()
                    }
                }
                //Log.d("getstatus", "1: ${player.getStatus()}, enemy : ${enemy.getStatus()} ")
                Log.d("isenemy", "isenemy : $isEnemyChoose")


            }

            ivBatup1.setOnClickListener {
                ivBatup1.setBackgroundResource(R.drawable.background_btnclick)
                setEnableButtonP1(false)
                player.setPoint(2)
                Toast.makeText(this@GameActivity,"$playername Memilih Batu",Toast.LENGTH_SHORT).show()
                start()
            }

            ivKertasp1.setOnClickListener {
                ivKertasp1.setBackgroundResource(R.drawable.background_btnclick)
                setEnableButtonP1(false)
                player.setPoint(0)
                Toast.makeText(this@GameActivity,"$playername Memilih Kertas",Toast.LENGTH_SHORT).show()
                start()
            }

            ivGuntingp1.setOnClickListener {
                ivGuntingp1.setBackgroundResource(R.drawable.background_btnclick)
                setEnableButtonP1(false)
                player.setPoint(1)
                Toast.makeText(this@GameActivity,"$playername Memilih Gunting",Toast.LENGTH_SHORT).show()
                start()
            }

            ivClose.setOnClickListener {
                finish()
            }

            ivRefresh.setOnClickListener {
                restart()
            }


        }



    }
}

abstract class PlayerImplementation {
    abstract fun setPoint(choose: Int)
    abstract fun getPoint(): Int
    abstract fun setStatus(status: String)
    abstract fun getStatus(): String
}

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

class Enemy: PlayerImplementation() {
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

abstract class DeclareGame{
    abstract fun computerChoose()
    abstract fun startGame()
}

class GameBuilder(val player: Player, val enemy: Enemy) : DeclareGame(){

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