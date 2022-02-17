package com.aait.utils.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.aait.utils.R

object Utils {


    fun shareApp(mContext: Context) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=" + mContext.packageName
        )
        sendIntent.type = "text/plain"
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(sendIntent)
    }

    fun openAppInStore(mContext: Context) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                "https://play.google.com/store/apps/details?id=${mContext.packageName}"
            )
        )
        mContext.startActivity(intent)
    }

    fun getFacebookPageURL(context: Context, facebook: String?): String? {
        val packageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.facebook.orca", 0).versionCode
            if (versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=$facebook"
            } else { //older versions of fb app
                "fb://page/$facebook"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            facebook //normal web url
        }
    }

    fun openBrowser(context: Context, url: String?) {
        if (url.isNullOrEmpty())
            return
        else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                context.startActivity(browserIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun openPhone(context: Context, phone: String?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openWhats(context: Context, phone: String?) {
        val url = "https://api.whatsapp.com/send?phone=$phone"
        try {
            val pm: PackageManager = context.packageManager
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        } catch (e: PackageManager.NameNotFoundException) {
            openBrowser(context, url)
        }
    }

    fun openEmail(context: Context, email: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: $email")
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createDrawableFromView(context: Context, view: View): Bitmap? {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun setBackToolbar(
        activity: AppCompatActivity,
        title: String,
        toolbar: Toolbar?,
    ) {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar!!.title = title
        activity.supportActionBar!!.setDisplayShowTitleEnabled(true)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationIcon(R.drawable.ic_back)
    }

    fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }

    fun hiddenKeyBord(view: View, context: Context) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}