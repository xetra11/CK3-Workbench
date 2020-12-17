package com.github.xetra11.ck3workbench.module.character.app

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NotificationsService {
    private const val MAX_BUFFER_SIZE = 50
    private val LOG: Logger = LoggerFactory.getLogger(NotificationsService::class.java)
    private val notifications = mutableStateListOf<String>("No notifications")

    fun notify(message: String) {
        if (notifications.size > MAX_BUFFER_SIZE) {
            notifications.clear()
        }
        notifications.add(message)
        LOG.info(message)
    }

    fun latestMessage(): String {
        return notifications.last()
    }

    fun allMessages(): SnapshotStateList<String> {
        return notifications
    }
}
