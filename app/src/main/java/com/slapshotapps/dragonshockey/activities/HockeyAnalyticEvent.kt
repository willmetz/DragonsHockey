package com.slapshotapps.dragonshockey.activities


enum class HockeyAnalyticEvent(public val contentType: String, public val itemID: String) {
    ROSTER_SELECTED("Roster", "901"),
    SCHEDULE_SELECTED("Schedule", "900"),
    STATS_SELECTED("Stats", "902"),
    NOTIFICATIONS_ENABLED("NotificationsEnabled", "903"),
    NOTIFICATIONS_DISABLED("NotificationsDisabled", "904"),
    NOTIFICATION_TRIGGERED("NotificationTriggered", "905")
}