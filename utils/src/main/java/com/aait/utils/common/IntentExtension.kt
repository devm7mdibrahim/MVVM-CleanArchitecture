package com.aait.utils.common

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import java.util.*


fun Activity.openWhatsapp(phone: String) {
    val url = "https://api.whatsapp.com/send?phone=966$phone"
    val i = Intent(Intent.ACTION_VIEW)
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    i.data = Uri.parse(url)
    startActivity(i)
}


fun Context.openMap(lat: Double, lon: Double, address: String) {
    val uri = java.lang.String.format(
        Locale.ENGLISH,
        "http://maps.google.com/maps?daddr=%f,%f (%s)",
        lat,
        lon,
        "cairo"
    )
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    startActivity(intent)
}


fun Activity.openTwitterProfile(userName: String) {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("twitter://user?screen_name=$userName")
            )
        )
    } catch (e: Exception) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/#!/$userName")
            )
        )
    }
}

fun Activity.openFacebookProfile(userId: String) {
    try {
        getPackageManager()
            ?.getPackageInfo("com.facebook.katana", 0) // Checks if FB is even installed.
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/$userId")
        ) // Try to make intent with FB's URI
        startActivity(intent)
    } catch (e: Exception) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(userId)
        ) // catches and opens a url to the desired page
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
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

fun Activity.openInstagramProfile(profile: String) {
    val userProfileLink: String = if (profile.startsWith("http")) profile else "http://instagram.com/xxx$profile"
    val uri = Uri.parse(profile)
    val likeIng = Intent(Intent.ACTION_VIEW, uri)
    likeIng.setPackage("com.instagram.android")
    try {
        likeIng.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(userProfileLink))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}

fun Activity.pickDocument() {
    val intent = Intent()
    intent.type = "application/pdf";
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(intent, 1000)
}

fun Activity.openLinkedInPage(linkedId: String) {
    var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@$linkedId"))
    val packageManager: PackageManager = packageManager
    val list =
        packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    if (list.isEmpty()) {
        intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.linkedin.com/profile/view?id=$linkedId")
        )
    }
    startActivity(intent)
}

fun Activity.openLink(link: String) {
    val defaultBrowser =
        Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
    defaultBrowser.setData(Uri.parse(link))
    startActivity(defaultBrowser)
}

fun Activity.openSnapChatProfile(userId: String) {
    val userProfileLink: String
    userProfileLink =
        if (userId.startsWith("http")) userId else "https://snapchat.com/add/$userId"
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(userProfileLink))
        intent.setPackage("com.snapchat.android")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    } catch (e: Exception) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(userProfileLink))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}

fun Activity.shareLink(link: String?) {
    val shareIntent = Intent()
    shareIntent.putExtra(Intent.EXTRA_TEXT, link)
    shareIntent.type = "text/plain"
    startActivity(Intent.createChooser(shareIntent, "ارسال"))
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


