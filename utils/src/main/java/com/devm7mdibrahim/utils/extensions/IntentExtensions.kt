package com.devm7mdibrahim.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import java.util.*


fun Activity.openWhatsapp(phone: String) {
    val url = "https://api.whatsapp.com/send?phone=$phone"
    val i = Intent(Intent.ACTION_VIEW)
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    i.data = Uri.parse(url)
    startActivity(i)
}


fun Context.openMap(lat: Double, long: Double, address: String) {
    val uri = java.lang.String.format(
        Locale.ENGLISH,
        "http://maps.google.com/maps?daddr=%f,%f (%s)",
        lat,
        long,
        address
    )
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    startActivity(intent)
}

fun Activity.openGmail(email: String) {
    val emailIntent = Intent(
        Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", email, null
        )
    )
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
    startActivity(Intent.createChooser(emailIntent, "Send email..."))
}

fun Context.shareApp() {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        "https://play.google.com/store/apps/details?id=" + this.packageName
    )
    sendIntent.type = "text/plain"
    sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(sendIntent)
}

fun Context.openAppInStore() {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(
            "https://play.google.com/store/apps/details?id=${this.packageName}"
        )
    )
    this.startActivity(intent)
}

fun Context.openBrowser(url: String?) {
    if (url.isNullOrEmpty()) return

    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        this.startActivity(browserIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.shareLink(link: String?) {
    val shareIntent = Intent()
    shareIntent.putExtra(Intent.EXTRA_TEXT, link)
    shareIntent.type = "text/plain"
    startActivity(Intent.createChooser(shareIntent, ""))
}

fun Activity.openDialer(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    startActivity(intent)
}

fun Activity.openPDF(pdf: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW)
    browserIntent.setDataAndType(Uri.parse(pdf), "application/pdf")

    val chooser = Intent.createChooser(browserIntent, "")
    chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK // optional

    startActivity(chooser)
}

fun Context.openTelegram(account: String) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse("http://telegram.me/$account")
    val appName = "org.telegram.messenger"
    try {
        if (isAppAvailable(appName)) i.setPackage(appName)
    } catch (e: PackageManager.NameNotFoundException) {
    }
    startActivity(i)
}

fun Context.isAppAvailable(appName: String): Boolean {
    val pm: PackageManager = this.packageManager
    return try {
        pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Context.openNotificationSettings() {
    val intent = Intent()
    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
    intent.putExtra("android.provider.extra.APP_PACKAGE", this.packageName)
    startActivity(intent)
}