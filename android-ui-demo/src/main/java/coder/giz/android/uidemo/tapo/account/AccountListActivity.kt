package coder.giz.android.uidemo.tapo.account

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import coder.giz.android.uidemo.R
import coder.giz.android.uidemo.databinding.ActivityAccountListBinding
import coder.giz.android.uidemo.databinding.DialogCreateNewAccountItemBinding
import coder.giz.android.yfui.base.DataBindingBaseActivity
import coder.giz.android.yfutility.components.toast
import coder.giz.android.yfutility.data.toObject
import coder.giz.android.yfutility.data.toPrettyJson
import coder.giz.android.yfutility.util.YFLog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.util.UUID

class AccountListActivity :
    DataBindingBaseActivity<ActivityAccountListBinding>(),
    AccountAdapter.OnItemClickListener,
    AccountDataPopupWindow.Callback {

    private val mAccountDataManager by lazy { AccountDataManager(this) }
    private val mAdapter = AccountAdapter().also { it.setOnItemClickListener(this) }
    private val mAccountDataPopupWindow by lazy {
        AccountDataPopupWindow(this).apply {
            setCallback(this@AccountListActivity)
        }
    }
    private var mCreateNewAccountItemDialog: AlertDialog? = null

    private var mLastJsonFileUri: Uri? = null

    override fun getLayoutId(): Int = R.layout.activity_account_list

    override fun initView() {
        mAdapter.setOnItemClickListener(this)
        mBinding.rvAccounts.adapter = mAdapter.also {
            it.updateData(mAccountDataManager.buildTestAccountList())
        }
        mBinding.run {
            btnCreateFile.setOnClickListener { createFile() }
            btnOpenFile.setOnClickListener { openFile() }
            btnViewFile.setOnClickListener { viewFile() }
        }
        // 读取账号信息列表
        readAccountsData()
    }

    private fun readAccountsData() {
        if (mAccountDataManager.hasJsonFileUri()) {
            val accountsData = mAccountDataManager.readAccountsData()
            mAdapter.updateData(accountsData.accounts)
        } else {
            openFile()
        }
    }

    override fun onAccountItemClick(itemView: View, accountItem: AccountItem) {
        mAccountDataPopupWindow.run {
            setAccountItem(accountItem)
            showAsDropDown(itemView)
        }
    }

    override fun onAddNewClick() {
        showCreateNewAccountItemDialog()
    }

    private fun showCreateNewAccountItemDialog() {
        val binding = DialogCreateNewAccountItemBinding.inflate(layoutInflater).apply {
            btnSave.setOnClickListener {
                val email = etEmail.text?.toString()
                val password = etPassword.text?.toString()
                if (email.isNullOrBlank() || password.isNullOrBlank()) {
                    toast("邮箱或密码不能为空")
                    return@setOnClickListener
                }
                val accountItem = AccountItem(UUID.randomUUID().toString(), R.drawable.avatar, email, password)
                addAccountItem(accountItem)
                mCreateNewAccountItemDialog?.dismiss()
                mCreateNewAccountItemDialog = null
            }
        }
        mCreateNewAccountItemDialog = MaterialAlertDialogBuilder(this)
            .setView(binding.root)
            .setBackground(ContextCompat.getDrawable(this, R.drawable.bg_round_corner_16))
            .create()
        mCreateNewAccountItemDialog?.show()
    }

    private fun addAccountItem(item: AccountItem) {
        if (mAccountDataManager.hasJsonFileUri()) {
            mAccountDataManager.addAccountItem(item)
            readAccountsData()
        } else {
            createFile()
        }
    }

    override fun deleteAccount(item: AccountItem) {
        mAccountDataManager.removeAccountItem(item)
    }

    override fun switchAccount(item: AccountItem) {

    }

    /**
     * 创建文件。
     * 文件路径：/storage/emulated/0/GAndroidJourney/accounts.json
     */
    private fun createFile() {
        // 动作：ACTION_CREATE_DOCUMENT
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            // 类别
            addCategory(Intent.CATEGORY_OPENABLE)
            // 文件类型
            type = "application/json"
            // 文件名
            putExtra(Intent.EXTRA_TITLE, "accounts.json")

//            putExtra(DocumentsContract.EXTRA_INITIAL_URI, jsonFileUri)
        }
        startActivityForResult(intent, REQUEST_CODE_CREATE_FILE)
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
//            putExtra(DocumentsContract.EXTRA_INITIAL_URI, jsonFileUri)
        }
        startActivityForResult(intent, REQUEST_CODE_PICKER_FILE)
    }

    private fun viewFile() {
        mLastJsonFileUri?.let(this::readJsonFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CREATE_FILE -> {
                    log { "Create file uri: ${data?.data}" }
                    mLastJsonFileUri = data?.data
                    // 保存文件Uri
                    data?.data?.let {
                        // 获取权限
                        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        contentResolver.takePersistableUriPermission(it, takeFlags)
                        mAccountDataManager.saveJsonFileUri(it)
                    }
                }
                REQUEST_CODE_PICKER_FILE -> {
                    log { "Open file uri: ${data?.data}" }
                    mLastJsonFileUri = data?.data
                    data?.data?.let(this::editJsonFile)
                    // 保存文件Uri
                    data?.data?.let {
                        // 获取权限
                        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        contentResolver.takePersistableUriPermission(it, takeFlags)
                        mAccountDataManager.saveJsonFileUri(it)
                        readAccountsData()
                    }
                }
            }
        }
    }

    private fun readJsonFile(uri: Uri) {
        val sb = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    sb.append(line)
                    line = reader.readLine()
                }
            }
        }
        val content = sb.toString()
        val accountsData: AccountsData = content.toObject()
        log {
            "Content: $content\nAll accounts json: ${accountsData.toPrettyJson()}"
        }
        mBinding.tvJsonContent.text = accountsData.toPrettyJson()
    }

    private fun editJsonFile(uri: Uri) {
        val content = mAccountDataManager.getAccountsData().toPrettyJson()
        try {
            // "w"：从0位置开始覆盖内容写入。如果新内容少于原内容，那么原内容会剩余一部分。
            // "wa"：追加内容写入。
            // "wt"：覆盖全部内容写入。
            // 方式一：使用FileOutputStream，用于写入字节流数据。
//            contentResolver.openFileDescriptor(uri, "w")?.use {
//                FileOutputStream(it.fileDescriptor).use { fos ->
//                    fos.write(content.toByteArray())
//                }
//            }

            // 方式二：使用FileWrite，用于写入字符数据。
            contentResolver.openFileDescriptor(uri, "wt")?.use {
                BufferedWriter(FileWriter(it.fileDescriptor)).use { bw ->
                    bw.write(content)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private inline fun log(msg: () -> String) {
        YFLog.w(TAG, msg())
    }

    companion object {
        private const val TAG = "AccountListActivity"
        private const val REQUEST_CODE_CREATE_FILE = 1
        private const val REQUEST_CODE_PICKER_FILE = 2
    }

}