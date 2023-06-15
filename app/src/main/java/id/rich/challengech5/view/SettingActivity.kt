package id.rich.challengech5.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import id.rich.challengech5.R
import id.rich.challengech5.databinding.ActivitySettingBinding
import id.rich.challengech5.service.ApiClient
import id.rich.challengech5.service.ApiService
import id.rich.challengech5.service.BaseResponse
import id.rich.challengech5.viewmodel.SettingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel
    private lateinit var apiService: ApiService
    private lateinit var user: BaseResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        viewModel.theme.observe(this) { newTheme ->
            updateTheme(newTheme)
        }

        binding.rgThemeOptions.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                R.id.rb_autoTheme -> "auto"
                R.id.rb_lightTheme -> "light"
                R.id.rb_darkTheme -> "dark"
                else -> "auto"
            }
            viewModel.updateTheme(theme)
        }

        viewModel.getCurrentTheme()

        getUserProfile()

        binding.btnUpdate.setOnClickListener {
            updateUserProfile()
        }

    }

    private fun getUserProfile() {
        val apiService = ApiClient.instance
        val call = apiService.getDataUserProfile()

        call.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    user = response.body()!!
                    populateProfileData()
                } else {
                    Toast.makeText(applicationContext, "Gagal mengambil data profil", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun populateProfileData() {
        binding.etNewName.setText(user.username)
    }

    private fun updateUserProfile() {
        val newName = binding.etNewName.text.toString()

        val call = apiService.updateDataUserProfile(user._id, newName)

        call.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    user = response.body()!!
                    Toast.makeText(applicationContext, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Koneksi gagal", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTheme(theme: String) {
        when (theme) {
            "auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
