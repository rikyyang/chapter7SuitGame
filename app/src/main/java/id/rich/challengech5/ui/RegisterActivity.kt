package id.rich.challengech5.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import id.rich.challengech5.databinding.ActivityRegisterBinding
import id.rich.challengech5.repository.SuitGameRepository
import id.rich.challengech5.service.ApiClient
import id.rich.challengech5.viewmodel.SuitGameViewModel
import viewModelsFactory

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val apiService by lazy { ApiClient.instance }
    private val repository by lazy { SuitGameRepository(apiService) }
    private val viewModel: SuitGameViewModel by viewModelsFactory { SuitGameViewModel(repository) }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerNewUser()
        observeRegister(applicationContext)
    }

    private fun registerNewUser() {
        binding.btnRegister.setOnClickListener {
            viewModel.registerNewUser(binding.tvEmail.text.toString(), binding.tvUsername.text.toString(), binding.tvPassword.text.toString())
        }
    }

    private fun observeRegister(context: Context) {

        viewModel.onSuccess.observe(this) { onSuccess ->
            if (onSuccess){
                Toast.makeText(context, "Success register new user!", Toast.LENGTH_LONG).show()
                nextScreen()
            }
            else{
                Toast.makeText(context, viewModel.errCause.value, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun nextScreen() {
        finish()
    }
}
