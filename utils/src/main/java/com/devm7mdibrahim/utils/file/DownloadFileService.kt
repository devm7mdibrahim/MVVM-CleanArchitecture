package com.devm7mdibrahim.utils.file

import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.widget.Toast
import androidx.core.app.JobIntentService
import java.io.File


class DownloadFileService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val fileUrl = intent.getStringExtra("fileUrl")
        val fileName = intent.getStringExtra("fileName")

        val file = File(this.filesDir, "myDir")

        downloadAndOpenPdf(fileUrl, file)
    }


    private fun downloadAndOpenPdf(url: String?, file: File) {
        if (!file.isFile) {

            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val req = DownloadManager.Request(Uri.parse(url))
            req.setDestinationUri(Uri.fromFile(file))
            req.setTitle("Some title")

            val receiver: BroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    unregisterReceiver(this)
                    if (file.exists()) {
                        openPdfDocument(file)
                    }
                }
            }

            registerReceiver(
                receiver, IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE
                )
            )

            dm.enqueue(req)
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()

        } else {
            openPdfDocument(file)
        }

    }

    private fun openPdfDocument(file: File?): Boolean {
        val target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(Uri.fromFile(file), "application/pdf")
        target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        return try {
            startActivity(target)
            true
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No PDF reader found", Toast.LENGTH_LONG).show()
            false
        }
    }


}