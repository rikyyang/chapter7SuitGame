package id.rich.challengech5.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.rich.challengech5.R
import id.rich.challengech5.databinding.GameBinding
import id.rich.challengech5.viewmodel.GameActivityViewModel


class GameActivity : AppCompatActivity() {

    private lateinit var binding: GameBinding
    private lateinit var gameActivityViewModel: GameActivityViewModel
    private var player1Choice: Int = -1
    private var player2Choice: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerName = intent.getStringExtra("player_name")
        val enemyName = intent.getStringExtra("enemy")

        gameActivityViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(GameActivityViewModel::class.java)


        with(binding) {
            tvP1.text = playerName
            tvEnemy.text = enemyName

            ivBatup1.setOnClickListener {
                ivBatup1.setBackgroundResource(R.drawable.background_btnclick)
                setEnableButtonP1(false)
                Toast.makeText(this@GameActivity, "$playerName Memilih Batu", Toast.LENGTH_SHORT)
                    .show()
                player1Choice = 0

                if (enemyName == "CPU") {
                    computerChoose()
                } else {
                    ivBatuenemy.setOnClickListener {
                        player2Choice = 0
                        setBackgroundEnemy(player2Choice)
                    }
                    ivKertasenemy.setOnClickListener {
                        player2Choice = 1
                        setBackgroundEnemy(player2Choice)
                    }
                    ivGuntingenemy.setOnClickListener {
                        player2Choice = 2
                        setBackgroundEnemy(player2Choice)
                    }
                }
            }

                ivKertasp1.setOnClickListener {
                    ivKertasp1.setBackgroundResource(R.drawable.background_btnclick)
                    setEnableButtonP1(false)
                    Toast.makeText(
                        this@GameActivity,
                        "$playerName Memilih Kertas",
                        Toast.LENGTH_SHORT
                    ).show()
                    player1Choice = 1

                    if (enemyName == "CPU") {
                        computerChoose()
                    } else {
                        ivBatuenemy.setOnClickListener {
                            player2Choice = 0
                            setBackgroundEnemy(player2Choice)
                        }
                        ivKertasenemy.setOnClickListener {
                            player2Choice = 1
                            setBackgroundEnemy(player2Choice)
                        }
                        ivGuntingenemy.setOnClickListener {
                            player2Choice = 2
                            setBackgroundEnemy(player2Choice)
                        }
                    }
                }

                ivGuntingp1.setOnClickListener {
                    ivGuntingp1.setBackgroundResource(R.drawable.background_btnclick)
                    setEnableButtonP1(false)
                    Toast.makeText(
                        this@GameActivity,
                        "$playerName Memilih Gunting",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    player1Choice = 2

                    if (enemyName == "CPU") {
                        computerChoose()
                    } else {
                        ivBatuenemy.setOnClickListener {
                            player2Choice = 0
                            setBackgroundEnemy(player2Choice)
                        }
                        ivKertasenemy.setOnClickListener {
                            player2Choice = 1
                            setBackgroundEnemy(player2Choice)
                        }
                        ivGuntingenemy.setOnClickListener {
                            player2Choice = 2
                            setBackgroundEnemy(player2Choice)
                        }
                    }
                }

                ivClose.setOnClickListener {
                    finish()
                }

                ivRefresh.setOnClickListener {
                    restart()
                }


            }
        }


    private fun setBackgroundEnemy(player2Choice: Int) {

        when (player2Choice) {
            0 -> {
                binding.ivBatuenemy.setBackgroundResource(R.drawable.background_btnclick)
                start(player1Choice, player2Choice)
            }
            1 -> {
                binding.ivKertasenemy.setBackgroundResource(R.drawable.background_btnclick)
                start(player1Choice, player2Choice)
            }
            2 -> {
                binding.ivGuntingenemy.setBackgroundResource(R.drawable.background_btnclick)
                start(player1Choice, player2Choice)
            }
        }

    }

    private fun computerChoose() {
        with(binding) {
            gameActivityViewModel.game.computerChoose()
            player2Choice = gameActivityViewModel.enemy.getPoint()

            when (player2Choice) {
                0 -> ivBatuenemy.setBackgroundResource(R.drawable.background_btnclick)
                1 -> ivKertasenemy.setBackgroundResource(R.drawable.background_btnclick)
                2 -> ivGuntingenemy.setBackgroundResource(R.drawable.background_btnclick)
            }

            start(player1Choice, player2Choice)
        }


    }

    private fun setEnableButtonP1(active: Boolean) {

        with(binding) {
            ivBatup1.isEnabled = active
            ivKertasp1.isEnabled = active
            ivGuntingp1.isEnabled = active
        }
    }

    private fun setEnableButtonEnemy(active: Boolean) {
        with(binding) {
            ivBatuenemy.isEnabled = active
            ivKertasenemy.isEnabled = active
            ivGuntingenemy.isEnabled = active
        }
    }

    private fun restart() {
        with(binding) {
            ivBatup1.setBackgroundResource(0)
            ivKertasp1.setBackgroundResource(0)
            ivGuntingp1.setBackgroundResource(0)
            ivBatuenemy.setBackgroundResource(0)
            ivKertasenemy.setBackgroundResource(0)
            ivGuntingenemy.setBackgroundResource(0)
            setEnableButtonP1(true)
            setEnableButtonEnemy(true)
        }
    }

    private fun openDialog(result: String) {
        val builder = AlertDialog.Builder(this@GameActivity, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.dialog_result, null)
        val buttonPlayAgain = view.findViewById<Button>(R.id.bt_dialogplayagain)
        val buttonBack = view.findViewById<Button>(R.id.bt_dialogback)
        val tvResult = view.findViewById<TextView>(R.id.tv_dialogresult)
        builder.setView(view)

        tvResult.text = result

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

    private fun showResult(result: String) {
        openDialog(result)
    }

    private fun start(player1Choice: Int, player2Choice: Int) {
        with(binding) {
            val playerName = tvP1.text.toString()
            val enemyName = tvEnemy.text.toString()


            gameActivityViewModel.startGame(
                playerName,
                enemyName,
                player1Choice,
                player2Choice
            )

            gameActivityViewModel.gameResult.observe(this@GameActivity, { result ->

                result?.let {
                    showResult(result.name)
                    Log.d("Hasil bug", result.name)
                }

            })
        }
    }
}
