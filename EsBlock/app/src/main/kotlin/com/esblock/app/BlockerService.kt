package com.esblock.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

/**
 * Watches Instagram and YouTube. When the full-screen Reels / Shorts viewer is on screen,
 * it presses Back so the person lands on the normal part of the app (feed, DMs, profiles).
 *
 * The view IDs below come from the apps' own screens and can change when those apps update.
 * If blocking stops working after an app update, only this list needs to be refreshed.
 */
class BlockerService : AccessibilityService() {

    private var lastBlockAt = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val pkg = event?.packageName?.toString() ?: return
        val prefs = Prefs(this)

        val ids = when (pkg) {
            INSTAGRAM -> if (prefs.blockInstagram) INSTAGRAM_IDS else return
            YOUTUBE -> if (prefs.blockYoutube) YOUTUBE_IDS else return
            else -> return
        }

        val root = rootInActiveWindow ?: return
        if (root.packageName?.toString() != pkg) return

        if (isShortVideoScreen(root, ids)) {
            blockNow(prefs)
        }
    }

    private fun isShortVideoScreen(root: AccessibilityNodeInfo, ids: List<String>): Boolean {
        for (id in ids) {
            val nodes = root.findAccessibilityNodeInfosByViewId(id)
            if (nodes.any { it.isVisibleToUser }) return true
        }
        return false
    }

    private fun blockNow(prefs: Prefs) {
        val now = System.currentTimeMillis()
        if (now - lastBlockAt < COOLDOWN_MS) return
        lastBlockAt = now

        performGlobalAction(GLOBAL_ACTION_BACK)
        prefs.blockedCount = prefs.blockedCount + 1
        Toast.makeText(this, R.string.blocked_toast, Toast.LENGTH_SHORT).show()
    }

    override fun onInterrupt() {
        // Nothing to clean up.
    }

    private companion object {
        const val COOLDOWN_MS = 900L

        const val INSTAGRAM = "com.instagram.android"
        const val YOUTUBE = "com.google.android.youtube"

        val INSTAGRAM_IDS = listOf(
            "com.instagram.android:id/clips_viewer_view_pager",
            "com.instagram.android:id/root_clips_layout",
            "com.instagram.android:id/clips_swipe_refresh_container"
        )

        val YOUTUBE_IDS = listOf(
            "com.google.android.youtube:id/reel_recycler",
            "com.google.android.youtube:id/reel_player_page_container",
            "com.google.android.youtube:id/reel_watch_fragment_root"
        )
    }
}
