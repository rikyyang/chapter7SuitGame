package id.rich.challengech5.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import id.rich.challengech5.R
import id.rich.challengech5.database.GameDatabase
import id.rich.challengech5.database.UserDao
import id.rich.challengech5.databinding.ActivityProfileBinding
import id.rich.challengech5.model.Gender
import id.rich.challengech5.presenter.ProfilePresenter
import id.rich.challengech5.view.ProfileContract
import id.rich.challengech5.view.SettingActivity
import id.rich.challengech5.viewmodel.ProfileViewModel


class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userDao: UserDao
    private lateinit var presenter: ProfileContract.Presenter
    private var dialog: AlertDialog? = null
    private lateinit var viewModel: ProfileViewModel
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var playerView: PlayerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //view Model ProfileViewModel
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.showVideoDialog.observe(this) {videoUrl ->
            if (!videoUrl.isNullOrEmpty()) {
                showVideoDialog(videoUrl)
            }
        }

        val db = GameDatabase.getInstance(this)
        userDao = db.userDao()

        presenter = ProfilePresenter(this, userDao, this)

        val username = intent.getStringExtra("player_name")
        presenter.setDataProfil(username)

        btnClickListener()
    }

    //menampilkan video dengan dialog
    private fun showVideoDialog(videoUrl: String) {

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_video, null)
        builder.setView(dialogView)

        playerView = dialogView.findViewById(R.id.videoPlayerView)
        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)

        exoPlayer = SimpleExoPlayer.Builder(this)
            .setTrackSelector(DefaultTrackSelector(this))
            .build()

        playerView.player = exoPlayer
        val mediaSource = buildMediaSource(videoUrl)
        exoPlayer.setMediaSource(mediaSource)

        exoPlayer.prepare()
        exoPlayer.play()

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()

        closeButton.setOnClickListener {
            exoPlayer.stop()
            dialog.dismiss()
        }


    }

    private fun buildMediaSource(videoUrl: String): MediaSource{

        val userAgent = Util.getUserAgent(this, "ChallengeCH5")
        val dataSourceFactory = DefaultDataSourceFactory(this, userAgent)
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(videoUrl))

    }

    override fun showUserName(name: String) {
        binding.tvNamaUser.text = name
    }

    override fun showUserGender(gender: Gender) {
        if (gender == Gender.FEMALE) {
            binding.ivGender.setImageResource(R.drawable.girl)
        }
    }

    override fun navigateToThemeActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    override fun showDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
        val view = layoutInflater.inflate(R.layout.dialog_logout, null)
        builder.setView(view)

        val btnYes = view.findViewById<Button>(R.id.btn_yes)
        val btnNo = view.findViewById<Button>(R.id.btn_no)

        dialog = builder.create()

        btnYes.setOnClickListener {
            presenter.onLogOutConfirmation()
        }

        btnNo.setOnClickListener {
            presenter.onLogOutCanceled()
        }

        dialog?.setCancelable(false)
        dialog?.setOnCancelListener {
            presenter.onLogOutCanceled()
        }
        dialog?.show()
    }

    override fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }

    override fun setResultAndFinish(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    private fun btnClickListener() {
        binding.btnThemeSetting.setOnClickListener {
            presenter.onThemeSettingClicked()
        }
        binding.btnGameHistory.setOnClickListener {
            presenter.onGameHistoryClicked()
        }

        //viewmodel tidak menggunakan presenter
        binding.btnVideo.setOnClickListener {
            val videoUrl = "https://vimeo.com/834328391"
            viewModel.openVideoDialog(videoUrl)
        }

        binding.btnLogOut.setOnClickListener {
            presenter.onLogOutClicked()
        }
    }
}


