package com.github.xetra11.ck3workbench.module.character.app

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NotificationsService {
    private const val MAX_BUFFER_SIZE = 50
    private val LOG: Logger = LoggerFactory.getLogger(NotificationsService::class.java)
    private val notifications = mutableStateListOf<Notification>(Notification("No notifications"))

    fun notify(message: String) {
        if (notifications.size > MAX_BUFFER_SIZE) {
            notifications.clear()
        }
        notifications.add(Notification(message))
        LOG.info(message)
    }

    fun error(message: String) {
        if (notifications.size > MAX_BUFFER_SIZE) {
            notifications.clear()
        }
        notifications.add(Notification(message, type = NotificationType.ERROR))
        LOG.error(message)
    }

    fun warn(message: String) {
        if (notifications.size > MAX_BUFFER_SIZE) {
            notifications.clear()
        }
        notifications.add(Notification(message, type = NotificationType.WARNING))
        LOG.warn(message)
    }

    fun latestMessage(): Notification {
        return notifications.last()
    }

    fun allMessages(): SnapshotStateList<Notification> {
        return notifications
    }

    class Notification(
        val message: String,
        val type: NotificationType = NotificationType.NOTIFICATION,
    )

    enum class NotificationType {
        ERROR,
        WARNING,
        NOTIFICATION
    }
}
