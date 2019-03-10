package com.slapshotapps.dragonshockey.activities.settings


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.databinding.FragmentSettingsBinding
import com.slapshotapps.dragonshockey.managers.NotificationManager
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.observables.ScheduleObserver
import com.slapshotapps.dragonshockey.services.NotificationService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingsFragment : HockeyFragment(), SettingsViewModel.SettingsViewModelListener {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        ScheduleObserver.getHockeySchedule(firebaseDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val viewModel = SettingsViewModel(UserPrefsManager(context!!), this, it, NotificationManager(context!!))
                    binding.item = viewModel
                    binding.timeSelection.setOnTimeChangedListener(viewModel)

                    lifecycle.addObserver(viewModel)
                }, {
                    //TODO
                })
    }

    override fun onShowNotification() {
        val intent = Intent(context, NotificationService::class.java)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(intent)
        }else{
            activity?.startService(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment SettingsFragment.
         */
        @JvmStatic
        fun newInstance() =
                SettingsFragment()
    }
}
