package com.github.xetra11.ck3workbench.app

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Paths

/**
 * Application settings
 * @param autosave Flag if application should autosave the project
 */
@Serializable
data class AppSettings(
   var autosave: Boolean = false
)

fun AppSettings.save() {
   val settingsFile = Paths.get("settings.cfg").toAbsolutePath().toFile()
   val settingsData = Json.encodeToString(this)
   settingsFile.writeText(settingsData)
}
