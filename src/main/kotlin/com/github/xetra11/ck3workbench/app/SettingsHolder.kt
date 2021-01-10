package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Paths

/**
 * Holds all the app wide settings state
 */
object SettingsHolder {
    var autosave by mutableStateOf(false)

    /**
     * Creates 'settings.cfg' if non exists
     */
    fun init() {
        val settingsFile = Paths.get("settings.cfg").toAbsolutePath().toFile()
        if (!settingsFile.exists()) {
            AppSettings().save()
        }
    }

    /**
     * Convert holder state into [AppSettings] instance
     * @return Instance of [AppSettings] containing the holder state
     */
    fun toAppSettings(): AppSettings {
        return AppSettings(
            autosave = autosave
        )
    }

    /**
     * Load settings from 'settings.cfg' file from filesystem
     * @return Instance of [AppSettings] from settings.cfg file
     */
    fun load(): AppSettings {
        val settingsFile = Paths.get("settings.cfg").toAbsolutePath().toFile()
        if (!settingsFile.exists()) {
            NotificationsService.error("No settings.cfg could be found. Returning default settings")
            return AppSettings()
        }
        return Json.decodeFromString<AppSettings>(settingsFile.readText())
    }

    /**
     * Load settings from 'settings.cfg' file from filesystem and initialize [SettingsHolder] with it
     */
    fun loadToHolder() {
        val appSettings = this.load()
        this.autosave = appSettings.autosave
    }
}
