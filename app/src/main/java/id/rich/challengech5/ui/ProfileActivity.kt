package id.rich.challengech5.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewModelScope
import id.rich.challengech5.R
import id.rich.challengech5.database.GameDatabase
import id.rich.challengech5.databinding.ActivityProfileBinding
import id.rich.challengech5.view.SettingActivity
import id.rich.challengech5.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = GameDatabase.getInstance(this)
        val userDao = db.userDao()

        viewModel = ProfileViewModel(application)

        viewModel.viewModelScope.launch {
            val user = viewModel.fetchUserProfile("username")
            // Lakukan sesuatu dengan data pengguna yang diterima dari API
        }

        viewModel.init(userDao)

        val username = intent.getStringExtra("player_name")
        if (username != null) {
            viewModel.fetchUserProfile(username)
        }

        btnClickListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userName.observe(this) { name ->
            binding.tvNamaUser.text = name
        }

//        viewModel.userGender.observe(this, { gender ->
//            if (gender == Gender.FEMALE) {
//                binding.ivGender.setImageResource(R.drawable.girl)
//            }
//        })

        viewModel.navigateToSettingActivity.observe(this) {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        viewModel.showDialog.observe(this) {
            showDialog()
        }

        viewModel.dismissDialog.observe(this) {
            dismissDialog()
        }

        viewModel.setResultAndFinish.observe(this) { resultCode ->
            setResult(resultCode)
            finish()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
        val view = layoutInflater.inflate(R.layout.dialog_logout, null)
        builder.setView(view)

        val btnYes = view.findViewById<Button>(R.id.btn_yes)
        val btnNo = view.findViewById<Button>(R.id.btn_no)

        dialog = builder.create()

        btnYes.setOnClickListener {
            viewModel.onLogOutConfirmation()
        }

        btnNo.setOnClickListener {
            viewModel.onLogOutCanceled()
        }

        dialog?.setCancelable(false)
        dialog?.setOnCancelListener {
            viewModel.onLogOutCanceled()
        }
        dialog?.show()
    }

    private fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }

    private fun btnClickListener() {
        binding.btnSetting.setOnClickListener {
            viewModel.onThemeSettingClicked()
        }
        binding.btnGameHistory.setOnClickListener {
            viewModel.onGameHistoryClicked()
        }
        binding.btnLogOut.setOnClickListener {
            viewModel.onLogOutClicked()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            startActivity(Intent(this, LandingPageActivity::class.java))
            finish()
        }
    }
}
