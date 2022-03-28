package com.devm7mdibrahim.utils.file

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class FileUtils constructor(val context: Context) {

    fun saveFileInStorage(uri: Uri): String {
        var path = ""
        runBlocking {
            try {
                val file: File?
                val mimeType: String? = context.contentResolver.getType(uri)
                if (mimeType != null) {
                    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                    val fileName = context.contentResolver.getFileName(uri)

                    if (fileName != "") {
                        file = File(
                            context.getExternalFilesDir(
                                Environment.DIRECTORY_DOWNLOADS
                            )?.absolutePath.toString() + "/" + fileName
                        )
                        val output: OutputStream = FileOutputStream(file)
                        output.use { it ->
                            val buffer =
                                ByteArray(inputStream?.available()!!)
                            var read: Int
                            while (inputStream.read(buffer).also { read = it } != -1) {
                                it.write(buffer, 0, read)
                            }
                            it.flush()
                            path = file.absolutePath //use this path
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return path
    }

    @SuppressLint("Range")
    fun ContentResolver.getFileName(uri: Uri): String {
        var mName = ""
        val cursor = query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            mName = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return mName
    }
}
