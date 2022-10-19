package com.yogi.innobuzztest.ui.accessibility;

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.yogi.innobuzztest.utils.PrefencesManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MyAccessibilityService : AccessibilityService() {

    @Inject
    lateinit var prefencesManager: PrefencesManager

    lateinit var handler: Handler
    private val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa")

    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())
    }

    override fun onInterrupt() {

    }

    override fun onServiceConnected() {
        prefencesManager.saveString("msg", "Service Connected at ${sdf.format(Date())}")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            handler.post {
                Toast.makeText(
                    applicationContext,
                    "WhatsApp Launched",
                    Toast.LENGTH_LONG
                ).show()
            }
            prefencesManager.saveString(
                "msg",
                prefencesManager.getString("msg") + "\nWhatsApp Launched at ${sdf.format(Date())}"
            )
        }
    }

}