package com.slapshotapps.dragonshockey.activities.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.slapshotapps.dragonshockey.R
import com.slapshotapps.dragonshockey.databinding.FragmentSettingsBinding
import com.slapshotapps.dragonshockey.managers.UserPrefsManager


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        val viewModel = SettingsViewModel(UserPrefsManager(context!!))
        binding.item = viewModel
        binding.timeSelection.setOnTimeChangedListener(viewModel)

        lifecycle.addObserver(viewModel)


        return binding.root
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
