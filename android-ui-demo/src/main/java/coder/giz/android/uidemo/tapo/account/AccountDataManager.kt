package coder.giz.android.uidemo.tapo.account

import android.content.Context
import android.net.Uri
import androidx.core.content.edit
import androidx.core.net.toUri
import coder.giz.android.uidemo.R
import coder.giz.android.yfutility.data.toObjectOrNull
import coder.giz.android.yfutility.data.toPrettyJson
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.util.UUID

/**
 * Created by GizFei on 2022/3/1
 */
class AccountDataManager(private val mContext: Context) {

    companion object {
        private const val SP_FILENAME = "AccountDataManagerSP"
        private const val SP_KEY_ACCOUNTS_JSON_FILE_URI = "AccountsJsonFileUri"
    }

    private val mSP = mContext.getSharedPreferences(SP_FILENAME, Context.MODE_PRIVATE)

    /**
     * AccountItem映射表。key: uuid, value: AccountItem
     */
    private val mAccountItemMap = mutableMapOf<String, AccountItem>()
    private var mMapInitialized = false

    fun buildTestAccountList(): List<AccountItem> {
        return listOf(
            AccountItem(UUID.randomUUID().toString(), R.drawable.avatar, "XiaoHong", "xiaohong@email.com"),
            AccountItem(UUID.randomUUID().toString(), R.drawable.avatar, "XiaoMing", "xiaoming@email.com"),
            AccountItem(UUID.randomUUID().toString(), R.drawable.avatar, "ZhangSan", "zhangsan@email.com"),
            AccountItem(UUID.randomUUID().toString(), R.drawable.avatar, "LiSi", "lisi@email.com"),
            AccountItem(UUID.randomUUID().toString(), R.drawable.avatar, "ZhaoWu", "zhaowu@email.com"),
        )
    }

    fun getAccountsData(): AccountsData {
        return AccountsData(buildTestAccountList())
    }

    /**
     * 读取账号信息。
     * 1. 检测缓存的文件Uri（SP）
            1). 有
                a. 使用文件Uri打开该文件
                b. 读取账号信息
            2). 无
                a. 让用户选择文件，拿到文件Uri
                b. 缓存文件Uri（SP）
                c. 读取账号信息
     */
    fun readAccountsData(): AccountsData {
        ensureAccountsItemMapInit()
        return AccountsData(mAccountItemMap.values.toList())
    }

    private fun ensureAccountsItemMapInit() {
        if (!mMapInitialized) {
            initAccountsItemMap()
            mMapInitialized = true
        }
    }

    private fun initAccountsItemMap() {
        val jsonFileUri = getJsonFileUriInSP()
        val accountsData = if (jsonFileUri != null) {
            val originContent = readJsonFile(jsonFileUri)
            originContent.toObjectOrNull() ?: AccountsData(emptyList())
        } else {
            AccountsData(emptyList())
        }
        mAccountItemMap.clear()
        mAccountItemMap.putAll(accountsData.accounts.associateBy { it.uuid })
    }

    /**
     * 保存文件Uri
     */
    fun saveJsonFileUri(fileUri: Uri) {
        mSP.edit {
            putString(SP_KEY_ACCOUNTS_JSON_FILE_URI, fileUri.toString())
        }
    }

    fun hasJsonFileUri(): Boolean {
        val jsonFileUri = getJsonFileUriInSP()
        return jsonFileUri != null
    }

    /**
     * 添加一个账号信息。
     * 2. 检测缓存的文件Uri（SP）
     *      1). 有
     *          a. 使用文件Uri打开该文件
     *          b. 保存账号信息（追加文件内容）
     *      2). 无
     *          a. 则创建文件，让用户选择文件存储处后，拿到文件Uri
     *          b. 缓存文件Uri（SP）
     *          c. 保存账号信息
     */
    fun addAccountItem(item: AccountItem) {
        ensureAccountsItemMapInit()
        mAccountItemMap[item.uuid] = item
        saveAccountsData()
    }

    /**
     * 移除一个账号信息。
     */
    fun removeAccountItem(item: AccountItem) {
        ensureAccountsItemMapInit()
        mAccountItemMap.remove(item.uuid)
        saveAccountsData()
    }

    private fun saveAccountsData() {
        val content = AccountsData(mAccountItemMap.values.toList()).toPrettyJson()
        val jsonFileUri = getJsonFileUriInSP()
        jsonFileUri?.let {
            saveToJsonFile(it, content)
        }
    }

    private fun getJsonFileUriInSP() = mSP.getString(SP_KEY_ACCOUNTS_JSON_FILE_URI, null)?.toUri()

    /**
     * 将新的账号信息保存到文件中。
     * 1. 读取原文件中的账号列表
     * 2. 向列表中新增账号信息
     * 3. 将新列表保存到文件
     */
    @Deprecated("旧实现")
    private fun appendAccountItemToFile(fileUri: Uri, item: AccountItem) {
        val originContent = readJsonFile(fileUri)
        val originAccounts = originContent.toObjectOrNull<AccountsData>()?.accounts
            ?: listOf()
        val newAccounts = originAccounts.toMutableList().apply { add(item) }
        val accountsData = AccountsData(newAccounts)
        saveToJsonFile(fileUri, accountsData.toPrettyJson())
    }

    private fun saveToJsonFile(fileUri: Uri, content: String) {
        try {
            // wt：覆盖写入。
            mContext.contentResolver.openFileDescriptor(fileUri, "wt")?.use {
                BufferedWriter(FileWriter(it.fileDescriptor)).use { bw ->
                    bw.write(content)
                }
            }
        } catch (e: FileNotFoundException) {
            clearFileUri()
            e.printStackTrace()
        } catch (e: IOException) {
            clearFileUri()
            e.printStackTrace()
        } catch (e: Exception) {
            clearFileUri()
            e.printStackTrace()
        }
    }

    private fun readJsonFile(fileUri: Uri): String {
        val sb = StringBuilder()
        try {
            mContext.contentResolver.openInputStream(fileUri)?.use {
                BufferedReader(InputStreamReader(it)).use { reader ->
                    var line: String? = reader.readLine()
                    while (line != null) {
                        sb.append(line)
                        line = reader.readLine()
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            clearFileUri()
            e.printStackTrace()
        } catch (e: IOException) {
            clearFileUri()
            e.printStackTrace()
        } catch (e: Exception) {
            clearFileUri()
            e.printStackTrace()
        }
        return sb.toString()
    }

    private fun clearFileUri() {
        mSP.edit {
            remove(SP_KEY_ACCOUNTS_JSON_FILE_URI)
        }
    }

}