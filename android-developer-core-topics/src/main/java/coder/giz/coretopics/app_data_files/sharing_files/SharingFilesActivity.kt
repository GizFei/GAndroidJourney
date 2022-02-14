package coder.giz.coretopics.app_data_files.sharing_files

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.components.toast
import coder.giz.android.yfutility.util.YFLog
import coder.giz.coretopics.R
import coder.giz.coretopics.databinding.ActivitySharingFilesBinding
import java.io.File
import java.io.PrintStream

/**
 * [分享文件](https://developer.android.com/training/secure-file-sharing/setup-sharing)
 */
class SharingFilesActivity : DataBindingBaseActivity<ActivitySharingFilesBinding>() {

    private var mTxtFilePath: String? = null
        get() {
            YFLog.e(TAG, "mTxtFilePath field: $field")
            return if (field == null) {
                defaultTxtFilePath()
            } else {
                field
            }
        }

    override fun getLayoutId(): Int = R.layout.activity_sharing_files

    override fun initView() {
        mBinding.btnSaveFile.setOnClickListener { saveTxtFile(DEFAULT_TXT_FILENAME) }
        mBinding.btnSaveFiles.setOnClickListener { saveTxtFiles() }
        mBinding.btnShareFile.setOnClickListener { shareTxtFile() }
        mBinding.btnListFiles.setOnClickListener { listTxtFiles() }
    }

    // region 1、创建文件，并写入内容
    private fun saveTxtFiles() {
        val filenames = listOf(
            "test123.txt",
            "demo.txt",
            "example.txt",
        )
        for (fn in filenames) {
            saveTxtFile(fn)
        }
    }

    private fun saveTxtFile(filename: String) {
        val externalFilesDir = getExternalFilesDir()
        if (externalFilesDir == null) {
            YFLog.e(TAG, "The external files dir doesn't exist.")
            return
        }

        val txtFolderPath = File(externalFilesDir, DEFAULT_TXT_FOLDER_NAME).absolutePath
        val txtFile = generateTxtFile(txtFolderPath, filename)
        if (txtFile == null) {
            YFLog.e(TAG, "The txt file \"$txtFolderPath/$filename\" is created failed.")
            return
        }
        mTxtFilePath = txtFile.absolutePath
        YFLog.e(TAG, "创建文件成功。文件路径：${txtFile.absolutePath}")

        val txtContent = generateTxtContent()
        val output = PrintStream(txtFile.outputStream())
        output.println(txtContent)

        toast("文件保存成功。")
    }

    private fun generateTxtFile(folderPath: String, filename: String): File? {
        val folder = File(folderPath)
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                YFLog.e(TAG, "generateTxtFile failed.")
                return null
            }
        }
        return File(folderPath, filename)
    }

    private fun generateTxtContent(): String {
        return """
            您可以不使用 Uri.fromFile()，而使用 URI 权限来授予其他应用对特定 URI 的访问权限。
            虽然 URI 权限不适用于Uri.fromFile()生成的file:// URI，但它们适用于与内容提供程序关联的 URI。
            FileProvider API 可以帮助您创建此类 URI。此方法也适用于未存储在外部存储空间中，
            而是存储在发送 intent 的应用的本地存储空间中的文件。
            在 onItemClick() 中，获取所选文件的文件名所对应的 File 对象，并将其作为参数传递给 getUriForFile()，
            同时传递您在 FileProvider 的 <provider> 元素中指定的授权。所生成的内容 URI 包含授权、
            与文件目录对应的路径段（在 XML 元数据中指定）以及文件的名称（包括其扩展名）。
            如需了解 FileProvider 如何基于 XML 元数据将目录映射到路径段，请参阅指定可共享目录一节。
        """.trimIndent()
    }

    private fun defaultTxtFilePath(): String? {
        val externalFilesDir = getExternalFilesDir() ?: return null
        return externalFilesDir.absolutePath +
                File.separatorChar + DEFAULT_TXT_FOLDER_NAME +
                File.separatorChar + DEFAULT_TXT_FILENAME
    }

    /**
     * 获取应用外部存储目录。
     */
    private fun getExternalFilesDir(): File? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10(API 29)以上，使用专属外部存储目录
            // 路径：/storage/emulated/0/Android/data/包名/files
            this.getExternalFilesDir(null)
        } else {
            // 否则，使用旧版的外部存储目录
            // 路径：/storage/emulated/0/
            Environment.getExternalStorageDirectory()
        }
    }
    // endregion

    // region 2、分享文件
    private fun shareTxtFile() {
        val path = mTxtFilePath ?: run {
            YFLog.e(TAG, "The sharing file txt path is null.")
            return
        }
        val txtFile = File(path)
        if (!txtFile.exists()) {
            YFLog.e(TAG, "The sharing txt file [${txtFile.absolutePath}] doesn't exist.")
            toast("文件不存在！")
            return
        }

        // 创建uri
        val txtUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Android 7及以上，使用FileProvider
            FileProvider.getUriForFile(
                applicationContext,
                "${applicationInfo.packageName}.fileprovider",
                txtFile
            )
        } else {
            // Android 6及以下，直接使用Uri
            Uri.fromFile(txtFile)
        }
        YFLog.w(TAG, "Txt file uri: $txtUri")

        val intent = Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            putExtra(Intent.EXTRA_STREAM, txtUri)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, "分享文件"))
    }
    // endregion

    // region 3、列出应用外部存储目录下，/txt子目录中的所有文件
    @SuppressLint("SetTextI18n")
    private fun listTxtFiles() {
        val externalFilesDir = getExternalFilesDir() ?: run {
            YFLog.e(TAG, "External files dir is null.")
            return
        }

        val txtFolder = File(externalFilesDir, DEFAULT_TXT_FOLDER_NAME)
        if (txtFolder.exists() && txtFolder.isDirectory) {
            val files = txtFolder.listFiles() ?: emptyArray()
            for (file in files) {
                mBinding.tvFilePaths.text = (mBinding.tvFilePaths.text.toString() + "\n" + file.absolutePath).trim()
                if (file.isDirectory) {
                    YFLog.i(TAG, "Directory path: ${file.absolutePath}")
                } else {
                    YFLog.i(TAG, "File path: ${file.absolutePath}")
                }
            }
        } else {
            YFLog.e(TAG, "Txt folder [${txtFolder.absolutePath}] not exists or not directory.")
        }
    }
    // endregion

    companion object {
        private const val TAG = "SharingFiles"
        private const val DEFAULT_TXT_FOLDER_NAME = "txt"
        private const val DEFAULT_TXT_FILENAME = "test123.txt"
    }
}