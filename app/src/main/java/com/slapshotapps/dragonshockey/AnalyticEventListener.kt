package com.slapshotapps.dragonshockey


interface AnalyticEventListener {
    fun logContentSelectedEvent(contentType: String, itemID: String)
}