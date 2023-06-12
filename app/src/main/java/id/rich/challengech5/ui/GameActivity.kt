package id.rich.challengech5.ui

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playername = intent.getStringExtra("player_name")
        val enemyname = intent.getStringExtra("enemy")

        gameActivityViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(GameActivityViewModel::class.java)


        with(binding) {
            tvP1.text = playername
            tvEnemy.text = enemyname

            ivBatup1.setOnClickListener {
                ivBatup1.setBackgroundResource(R.drawable.background_btnclick)
                setEnableButtonP1(false)
                Toast.makeText(this@GameActivity, "$playername Memilih Batu", Toast.LENGTH_SHORT).show()
                start()
            }

            ivKertasp1.setOnClickListener {
                ivKertasp1.setBackgroundResource(R.drawable.background_btnclick)
                setEnableButtonP1(false)
                Toast.makeText(this@GameActivity, "$playername Memilih Kertas", Toast.LENGTH_SHORT).show()
                start()
            }

            ivGuntingp1.setOnClickListener {
                ivGuntingp1.setBackgroundResource(R.drawable.background_btnclick)
                setEnableButtonP1(false)
                Toast.makeText(this@GameActivity, "$playername Memilih Gunting", Toast.LENGTH_SHORT).show()
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

    private fun start() {
        with(binding) {
            val playerName = tvP1.text.toString()
            val enemyName = tvEnemy.text.toString()
            gameActivityViewModel.startGame(playerName, enemyName)

            gameActivityViewModel.gameResult.observe(this@GameActivity, { result ->

                result?.let {
                    showResult(result.name)
                }

            })
        }
    }

}