package com.slapshotapps.dragonshockey.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.slapshotapps.dragonshockey.activities.HockeyAnalyticEvent


fun logAnalyticEvent(analyticEvent: HockeyAnalyticEvent, firebaseAnalytics: FirebaseAnalytics?) {
    val bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, analyticEvent.contentType)
    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, analyticEvent.itemID)
    firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
}