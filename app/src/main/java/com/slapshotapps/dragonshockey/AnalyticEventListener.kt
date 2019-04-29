package com.slapshotapps.dragonshockey

import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent


interface AnalyticEventListener {
    fun logContentSelectedEvent(analyticEvent: HockeyAnalyticEvent)
}