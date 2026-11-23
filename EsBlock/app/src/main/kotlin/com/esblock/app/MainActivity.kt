package com.esblock.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var prefs: Prefs
    private lateinit var statusTitle: TextView
    private lateinit var statusHint: TextView
    private lateinit var countText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = Prefs(this)

        statusTitle = findViewById(R.id.status_title)
        statusHint = findViewById(R.id.status_hint)
        countText = findViewById(R.id.blocked_count)

        findViewById<Button>(R.id.open_settings).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        findViewById<Switch>(R.id.switch_instagram).apply {
            isChecked = prefs.blockInstagram
            setOnCheckedChangeListener { _, checked -> prefs.blockInstagram = checked }
        }

        findViewById<Switch>(R.id.switch_youtube).apply {
            isChecked = prefs.blockYoutube
            setOnCheckedChangeListener { _, checked -> prefs.blockYoutube = checked }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshStatus()
    }

    private fun refreshStatus() {
        val enabled = isServiceEnabled()
        if (enabled) {
            statusTitle.setText(R.string.status_on)
            statusTitle.setTextColor(getColor(R.color.accent))
            statusHint.setText(R.string.status_on_hint)
        } else {
            statusTitle.setText(R.string.status_off)
            statusTitle.setTextColor(getColor(R.color.danger))
            statusHint.setText(R.string.status_off_hint)
        }
        countText.text = getString(R.string.blocked_count, prefs.blockedCount)
    }

    private fun isServiceEnabled(): Boolean {
        val enabled = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false
        return enabled.contains("$packageName/") && enabled.contains("BlockerService")
    }
}
