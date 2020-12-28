import com.github.xetra11.ck3workbench.app.NotificationsService
import java.io.File
import javax.swing.filechooser.FileFilter

class ProjectFileFilter : FileFilter() {
    override fun accept(file: File?): Boolean {
        file?.let { f ->
            if (f.isDirectory) {
                return true
            }
            return f.extension == "wbp"
        } ?: run {
            NotificationsService.error("No file given")
        }
        return false
    }

    override fun getDescription(): String {
        return "Workbench Project File"
    }
}
