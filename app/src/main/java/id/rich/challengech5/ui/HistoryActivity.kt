package id.rich.challengech5.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import id.rich.challengech5.databinding.ActivityHistoryBinding
import id.rich.challengech5.model.User
import kotlinx.coroutines.NonCancellable.message
import kotlinx.coroutines.internal.ExceptionSuccessfullyProcessed.message

public class HistoryActivity : AppCompatActivity() {
    override protected fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState) ;
        
        ActivityHistoryBinding activityHistoryBinding = DataBindingUtil.setContentView(
                this, R.layout.fragment_login);
        activityHistoryBinding.setProfileActivity(
            new AppViewModel());
        activityHistoryBinding.excutePendingBindings();
    }
}

    ViewPagerAdapter({ "toastMessage"})
        public static void runMe(View view, String message)
{
    val view
    if (message !=null)
                Toast
                    .makeText(view.getContext(), message,
                        Toast.LENGTH_SHORT)
                    .show();
        }