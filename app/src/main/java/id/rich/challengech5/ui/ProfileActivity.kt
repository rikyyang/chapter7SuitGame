package id.rich.challengech5.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import id.rich.challengech5.R
import id.rich.challengech5.database.GameDatabase
import id.rich.challengech5.databinding.ActivityProfileBinding
import id.rich.challengech5.service.ApiClient
import id.rich.challengech5.service.BaseResponse
import id.rich.challengech5.view.SettingActivity
import id.rich.challengech5.viewmodel.ProfileViewModel
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

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

        viewModel.init(userDao)

        btnClickListener()
        observeViewModel()
        fetchDataFromAPI()
    }

    fun fetchDataFromAPI() {
        val apiService = ApiClient.instance
        val call = apiService.getData()

        call.enqueue(object : Callback<BaseResponse> {

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val username = data?.username
                    binding.tvNamaUser.setText(username)

                    val email = data?.email
                    binding.tvNamaEmail.setText(email)

                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Failed to retrieve data: $errorBody"

                    Log.e(TAG, errorMessage)
                    Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable?) {
                val errorMessage = "Failed to make API request: ${t?.message}"

                Log.e(TAG, errorMessage)
                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
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
}
