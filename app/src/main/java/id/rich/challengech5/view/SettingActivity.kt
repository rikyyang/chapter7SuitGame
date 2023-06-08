package id.rich.challengech5.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import id.rich.challengech5.R
import id.rich.challengech5.databinding.ActivitySettingBinding
import id.rich.challengech5.viewmodel.SettingViewModel

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel

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
    }

    private fun updateTheme(theme: String) {
        when (theme) {
            "auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}