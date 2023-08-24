package com.slapshotapps.dragonshockey.activities.settings


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.work.*
import com.slapshotapps.dragonshockey.utils.NotificationUtil
import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.databinding.FragmentSettingsBinding
import com.slapshotapps.dragonshockey.managers.NotificationManager
import com.slapshotapps.dragonshockey.managers.NotificationState
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.workers.UpcomingGameChecker
import java.util.concurrent.TimeUnit


const val NOTIFICATION_SCHEDULE_CHECKER_TASK = "NotificationScheduleTester"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingsFragment : HockeyFragment(), SettingsViewModel.SettingsViewModelListener {

    var binding: FragmentSettingsBinding? = null

    private val viewModel: SettingsViewModel by lazy{
        SettingsViewModel(UserPrefsManager(requireContext()), this, NotificationManager(requireContext()))
    }

    private val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    continueEnablingNotifications()
                } else {
                    binding?.notificationOptionsGroup?.check(com.slapshotapps.dragonshockey.R.id.notifications_disabled)
                    showNotificationPrompt()
                }
            }

    private fun showNotificationPrompt() {
        AlertDialog.Builder(requireContext())
                .setTitle("Notification Permission Required")
                .setMessage("To show game notifications permission is required.")
                .setPositiveButton("Ok", null)
                .show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container,false)

        setNotificationOption(viewModel.getNotificationState())

        binding?.notificationOptionsGroup?.setOnCheckedChangeListener{ _, checkedID ->
            when(checkedID){
                com.slapshotapps.dragonshockey.R.id.day_of_game -> {
                    viewModel.onRequestNotificationsEnabled()
                }
                com.slapshotapps.dragonshockey.R.id.day_before_game-> {
                    viewModel.onRequestNotificationsEnabled()
                }
                else -> {
                    viewModel.onDisableNotifications()
                }
            }
        }

        return binding?.root
    }

    private fun setNotificationOption(option: NotificationState){
        when(option){
            NotificationState.DISABLED -> {
                binding?.notificationOptionsGroup?.check(com.slapshotapps.dragonshockey.R.id.notifications_disabled)
            }
            NotificationState.DAY_OF_GAME -> {
                binding?.notificationOptionsGroup?.check(com.slapshotapps.dragonshockey.R.id.day_of_game)
            }
            NotificationState.DAY_BEFORE_GAME -> {
                binding?.notificationOptionsGroup?.check(com.slapshotapps.dragonshockey.R.id.day_before_game)
            }
        }
    }

    override fun onResumeWithCredentials() {
        //no-op
    }

    override fun noCredentialsOnResume() {
        //no-op
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun continueEnablingNotifications() {
        NotificationUtil.createNotificationChannel(requireContext())

        analyticEventListener?.logContentSelectedEvent(HockeyAnalyticEvent.NOTIFICATIONS_ENABLED)

        setupWorkManagerForSchedulePolling()
        viewModel.onNotificationsEnabled(binding?.dayOfGame?.isChecked == true)
    }

    private fun setupWorkManagerForSchedulePolling(){
        val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val notificationWork = PeriodicWorkRequestBuilder<UpcomingGameChecker>(GAME_SCHEDULE_CHECKING_FREQUENCY_HOURS, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(NOTIFICATION_SCHEDULE_CHECKER_TASK)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                .build()

        val workManager = WorkManager.getInstance(requireContext())
        cancelNotificationWork()
        workManager.enqueueUniquePeriodicWork(SCHEDULE_FETCH_JOB_NAME, ExistingPeriodicWorkPolicy.REPLACE, notificationWork)

    }

    private fun cancelNotificationWork() {
        WorkManager.getInstance(requireContext()).apply {
            cancelUniqueWork(SCHEDULE_FETCH_JOB_NAME)
            cancelUniqueWork(UpcomingGameChecker.DISPLAY_NOTIFICATION_WORK_JOB)
        }
    }

    override fun onRequestNotificationsEnabled() {
        if(checkNotificationPermissionRequired()){
            when(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)){
                PackageManager.PERMISSION_GRANTED -> continueEnablingNotifications()
                PackageManager.PERMISSION_DENIED -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }else{
            continueEnablingNotifications()
        }
    }

    private fun checkNotificationPermissionRequired() : Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }


    override fun onNotificationsDisabled() {
        analyticEventListener?.logContentSelectedEvent(HockeyAnalyticEvent.NOTIFICATIONS_DISABLED)

        cancelNotificationWork()
    }

    override fun onEnableNotificationOptions() {
        onRequestNotificationsEnabled()
    }

    companion object {
        const val SCHEDULE_FETCH_JOB_NAME = "scheduleFetchingJob"
        const val GAME_SCHEDULE_CHECKING_FREQUENCY_HOURS = 24L
    }
}
