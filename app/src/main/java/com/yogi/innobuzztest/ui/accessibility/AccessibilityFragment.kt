package com.yogi.innobuzztest.ui.accessibility

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import com.yogi.innobuzztest.databinding.FragmentAccessibilityBinding
import com.yogi.innobuzztest.utils.PrefencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccessibilityFragment : Fragment() {

    private var _binding: FragmentAccessibilityBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var prefencesManager: PrefencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccessibilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            bt.setOnClickListener { requestPermission() }
        }
    }

    override fun onResume() {
        super.onResume()
        val granted = checkAccessibilityPermission()

        val pair = if (granted) Pair("Granted", "#00ff00".toColorInt())
        else Pair("Denied", "#ff0000".toColorInt())
        binding.tvStatus.apply {
            text = pair.first
            setTextColor(pair.second)
        }
        binding.bt.text = if (granted) "Change Permissions" else "Grant Permissions"
        binding.tvMsg.text = prefencesManager.getString("msg") ?: "No Message"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAccessibilityPermission(): Boolean {
        var accessEnabled = 0
        try {
            accessEnabled = Settings.Secure.getInt(
                requireContext().contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        return accessEnabled != 0
    }

    private fun requestPermission() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}