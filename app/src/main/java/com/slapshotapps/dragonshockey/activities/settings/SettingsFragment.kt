package com.slapshotapps.dragonshockey.activities.settings


import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.work.*
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent
import com.slapshotapps.dragonshockey.activities.HockeyFragment
import com.slapshotapps.dragonshockey.databinding.FragmentSettingsBinding
import com.slapshotapps.dragonshockey.managers.NotificationManager
import com.slapshotapps.dragonshockey.managers.UserPrefsManager
import com.slapshotapps.dragonshockey.receivers.NotificationBroadcast
import com.slapshotapps.dragonshockey.receivers.RecreateAlarmOnDeviceBoot
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

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        return binding.root
    }

    override fun onResumeWithCredentials() {
        //no-op
    }

    override fun noCredentialsOnResume() {
        //no-op
    }

    override fun onResume() {
        super.onResume()

        val viewModel = SettingsViewModel(UserPrefsManager(context!!), this, NotificationManager(context!!))
        binding.item = viewModel
        lifecycle.addObserver(viewModel)
    }

    override fun onEnableNotifications() {

        analyticEventListener?.logContentSelectedEvent(HockeyAnalyticEvent.NOTIFICATIONS_ENABLED)


        val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val notificationWork = PeriodicWorkRequestBuilder<UpcomingGameChecker>(24L, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(NOTIFICATION_SCHEDULE_CHECKER_TASK)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                .build()

        val workManager = WorkManager.getInstance()
        cancelNotificationWork(workManager)
        workManager.enqueue(notificationWork)

        enableBroadcastReceivers(true)
    }

    override fun onDisableNotifications() {
        analyticEventListener?.logContentSelectedEvent(HockeyAnalyticEvent.NOTIFICATIONS_DISABLED)

        cancelNotificationWork(WorkManager.getInstance())

        enableBroadcastReceivers(false)
    }

    private fun cancelNotificationWork(workManager: WorkManager) {
        workManager.cancelAllWorkByTag(NOTIFICATION_SCHEDULE_CHECKER_TASK)
    }

    private fun enableBroadcastReceivers(enabled: Boolean) {
        val bootBroadcastReceiver = ComponentName(context!!, RecreateAlarmOnDeviceBoot::class.java)

        context?.packageManager?.setComponentEnabledSetting(
                bootBroadcastReceiver,
                if (enabled) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        )

        val notificationBroadcastReceiver = ComponentName(context!!, NotificationBroadcast::class.java)
        context?.packageManager?.setComponentEnabledSetting(
                notificationBroadcastReceiver,
                if (enabled) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        )
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
