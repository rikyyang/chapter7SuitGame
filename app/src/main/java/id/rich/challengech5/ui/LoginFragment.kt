package id.rich.challengech5.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import id.rich.challengech5.R
import id.rich.challengech5.repository.LoginRepository
import id.rich.challengech5.service.ApiClient
import id.rich.challengech5.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import viewModelsFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private val apiService by lazy {ApiClient.instance}
    private val repository by lazy {LoginRepository(apiService)}
    private val viewModel: LoginViewModel by viewModelsFactory { LoginViewModel(repository) }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var playername: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bt_login = view.findViewById<Button>(R.id.bt_login)
        playername = view.findViewById(R.id.et_playername)
        val password = view.findViewById<EditText>(R.id.et_password)
        val image_glider = view.findViewById<ImageView>(R.id.iv_landingpage3)
        val bt_register = view.findViewById<TextView>(R.id.bt_register)

        observeLogin(view.context)

        Glide.with(this)
            .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
            .circleCrop()
            .into(image_glider)

        bt_register.setOnClickListener{
            bt_register.setBackgroundResource(R.drawable.background_btnclick)
            val intent = Intent(activity, RegisterActivity::class.java)
            Handler(Looper.getMainLooper()).postDelayed({
               bt_register.setBackgroundResource(R.drawable.background_btnawal)
            }, 1000)

            startActivity(intent)
        }

        bt_login.setOnClickListener {
            if (playername.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                showMessage("Mohon isi username dan password")
            } else {
                viewModel.login(playername.text.toString(), password.text.toString())
            }
        }
    }

    private fun showMessage(message: String) {
        Looper.prepare()
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        Looper.loop()
    }

    private fun nextScreen() {
        val intent = Intent(activity, MenuPageActivity::class.java)
        intent.putExtra("player_name", playername.text.toString())
        startActivity(intent)
        requireActivity().finish()
    }

    private fun saveLogin(data: Any) {
        sharedPreferences = requireActivity().getSharedPreferences("LoginPreferences",
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        editor.putString("username", data.username)
        editor.putString("email", data.email)
        editor.putString("token", data.token)
        editor.apply()
    }

    private fun observeLogin(context: Context) {
        viewModel.onSuccess.observe(requireActivity()) { onSuccess ->
            if (onSuccess){
                saveLogin(viewModel.onSuccessData)

                nextScreen()
            }
            else{
                showMessage(viewModel.errCause.value.toString())
            }
        }
    }

}