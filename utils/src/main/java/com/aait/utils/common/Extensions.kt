package com.aait.utils.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.*
import android.text.format.DateUtils
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.aait.utils.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.loadImageFromUrl(url: String?) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(this.context)
        .load(url).placeholder(circularProgressDrawable)
//        .error(R.drawable.marsol_logo)
        .into(this)
}

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T : Any> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}

inline fun <reified T : Any> convertJsonStringToObject(jsonString: String): List<T> {
    return Gson().fromJson(jsonString, emptyArray<T>().javaClass).toList()
}

fun ImageView.loadCircleImg(url: String?, context: Context?) {
    val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
    circularProgressDrawable?.strokeWidth = 5f
    circularProgressDrawable?.centerRadius = 30f
    circularProgressDrawable?.start()
    Glide.with(this).load(url).placeholder(circularProgressDrawable).apply(
        RequestOptions.overrideOf(500, 500).circleCrop()
    )
//        .error(R.drawable.marsol_logo)
        .into(this)
}

fun ImageView.loadImageWithOverride(url: String?, height: Int, width: Int, context: Context?) {
    val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
    circularProgressDrawable?.strokeWidth = 5f
    circularProgressDrawable?.centerRadius = 30f
    circularProgressDrawable?.start()
    Glide.with(this).load(url)
        .placeholder(circularProgressDrawable).apply(
            RequestOptions.overrideOf(width, height)
        )
//        .error(R.drawable.ic_)
        .into(this)
}

fun ImageView.loadImageWithCurve(
    url: String?,
    height: Int,
    width: Int,
    curve: Int,
    context: Context?
) {
    val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
    circularProgressDrawable?.strokeWidth = 5f
    circularProgressDrawable?.centerRadius = 30f
    circularProgressDrawable?.start()
    Glide.with(this).load(url)
        .placeholder(circularProgressDrawable).apply(
            RequestOptions.overrideOf(width, height).transform(RoundedCorners(curve))
        )
//        .error(R.drawable.marsol_logo)
        .into(this)
}

fun ImageView.loadImageFromDrawable(context: Context, url: Int) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadImageFullFromUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
//        .error(R.drawable.logo_three)
        .into(this)
}

fun ImageView.loadImageFromUri(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun TextInputLayout.getTextValue(): String {
    return this.editText?.text.toString()
}


fun getLatLng(url: String?): LatLng {
    val latlong = url?.split(",")
    return if (latlong?.get(0) != "" && latlong?.get(0) != "null") {
        val latitude = (latlong?.get(0))?.toDouble()
        val longitude = (latlong?.get(1))?.toDouble()
        LatLng(latitude!!, longitude!!)

    } else {
        LatLng(0.0, 0.0)

    }
}

fun Any.onPrintLog(tag: String = "MARSOL LOG ==>>> ") {
    Log.d(tag, Gson().toJson(this))
}

inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

/**
 * Extension method to simplify the code needed to apply spans on a specific sub string.
 */
inline fun SpannableStringBuilder.withSpan(
    vararg spans: Any,
    action: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    val from = length
    action()

    for (span in spans) {
        setSpan(span, from, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return this
}

fun ImageView.setColorTint(resColor: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, resColor),
        android.graphics.PorterDuff.Mode.MULTIPLY
    )
}

fun Context.setPriceWithCurrency(
    price: String?,
    currency: String = getString(R.string.sar)
): String {
    if (price == null) return ""
    return StringBuilder().append(price).append(" ").append(currency).toString()
}

/**
 * Extension method to provide simpler access to {@link ContextCompat#getColor(int)}.
 */
fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

/**
 *  Extension method to provide simpler access to {@link ContextCompat#getColor(int)}
 *  from a [Fragment].
 */
fun Fragment.getColorCompat(color: Int) = context?.getColorCompat(color)

/**
 * Extension method to provide simpler access to {@link ContextCompat#getDrawableCompat(int)}.
 */
fun Context.getDrawableCompat(drawableResId: Int): Drawable? = ContextCompat
    .getDrawable(this, drawableResId)

/**
 * Extension method to provide simpler access to {@link ContextCompat#getDrawableCompat(int)}
 * from a [Fragment].
 */
fun Fragment.getDrawableCompat(drawableResId: Int) = context?.getDrawableCompat(drawableResId)!!

/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.getString(stringResId: Int): String = resources.getString(stringResId)

/**
 * Extension method to provide show keyboard for View.
 */

fun TextView.setHtml(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(text)
    }
}

/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

/**
 * Extension method to int time to 2 digit String
 */
fun Int.twoDigitTime() = if (this < 10) "0" + toString() else toString()

/**
 * Extension method to provide quicker access to the [LayoutInflater] from [Context].
 */
fun Context.getLayoutInflater() =
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
fun View.getLayoutInflater() = context.getLayoutInflater()

/**
 * Extension method to replace all text inside an [Editable] with the specified [newValue].
 */
fun Editable.replaceAll(newValue: String) {
    replace(0, length, newValue)
}

/**
 * Extension method to replace all text inside an [Editable] with the specified [newValue] while
 * ignoring any [android.text.InputFilter] set on the [Editable].
 */
fun Editable.replaceAllIgnoreFilters(newValue: String) {
    val currentFilters = filters
    filters = emptyArray()
    replaceAll(newValue)
    filters = currentFilters
}

/**
 * Extension method to cast a char with a decimal value to an [Int].
 */
fun Char.decimalValue(): Int {
    if (!isDigit())
        throw IllegalArgumentException("Out of range")
    return this.toInt() - '0'.toInt()
}


fun String.dateInFormat(format: String): Date? {
    val dateFormat = SimpleDateFormat(format, Locale.US)
    var parsedDate: Date? = null
    try {
        parsedDate = dateFormat.parse(this)
    } catch (ignored: ParseException) {
        ignored.printStackTrace()
    }
    return parsedDate
}

fun getClickableSpan(color: Int, action: (view: View) -> Unit): ClickableSpan {

    return object : ClickableSpan() {
        override fun onClick(view: View) {
            action(view)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = color
        }
    }
}


fun View.displaySnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snackbar = Snackbar.make(this, message, duration)
    snackbar.show()
    return snackbar
}

/**
 * Extension method use to display a [Snackbar] message to the userModel.
 */
fun View.displaySnakbar(messageResId: Int, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snakbar = Snackbar.make(this, messageResId, duration)
    snakbar.show()
    return snakbar
}

/**
 * Extension method to return the view location on screen as a [Point].
 */
fun View.locationOnScreen(): Point {
    val location = IntArray(2)
    getLocationOnScreen(location)
    return Point(location[0], location[1])
}

/**
 * Extension method to return the view location in window as a [Point].
 */
fun View.locationInWindow(): Point {
    val location = IntArray(2)
    getLocationInWindow(location)
    return Point(location[0], location[1])
}

/**
 * Extension method used to return the value of the specified float raised to the power
 * of the specified [exponent].
 */
fun Float.pow(exponent: Float) = Math.pow(this.toDouble(), exponent.toDouble()).toFloat()

/**
 * Convert a [Boolean] value to a view visibility [Int].
 */
fun Boolean.toViewVisibility(valueForFalse: Int = View.GONE): Int {
    return if (this) {
        View.VISIBLE
    } else {
        valueForFalse
    }
}

/**
 * Method used to easily retrieve [WindowManager] from [Context].
 */
fun Context.getWindowManager() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

/**
 * Method used to easily retrieve display size from [Context].
 */
fun Context.getDisplaySize() = Point().apply {
    getWindowManager().defaultDisplay.getSize(this)
}

/**
 * Method used to easily retrieve display size from [View].
 */
fun View.getDisplaySize() = context.getDisplaySize()


/**
 * Return whether Keyboard is currently visible on screen or not.
 *
 * @return true if keyboard is visible.
 */
fun Activity.isKeyboardVisible(): Boolean {
    val r = Rect()

    //r will be populated with the coordinates of your view that area still visible.
    window.decorView.getWindowVisibleDisplayFrame(r)

    //get screen height and calculate the difference with the usable area from the r
    val height = getDisplaySize().y
    val diff = height - r.bottom

    // If the difference is not 0 we assume that the keyboard is currently visible.
    return diff != 0
}

/**
 * Provides simpler access to the [ViewTreeObserver] inside a fragment.
 *
 * @return the [ViewTreeObserver] of the [Activity] this fragment currently attached to, or null
 * if the fragment is detached.
 */
fun Fragment.getViewTreeObserver() = activity?.window?.decorView?.viewTreeObserver

/**
 * Extension method to set width for View.
 */
fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

/**
 * Extension method to display Width for Context.
 */
fun Context.displayWidth(): Int = getDisplaySize().x

/**
 * Retrieve a decoded bitmap from resources, or null if the image could not be decoded.
 */
fun Context.decodeBitmap(resId: Int): Bitmap? = BitmapFactory.decodeResource(resources, resId)

@SuppressLint("SimpleDateFormat")
fun getDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date = inputFormat.parse(date)
    return DateUtils.getRelativeTimeSpanString(
        date.time,
        Calendar.getInstance().timeInMillis,
        DateUtils.MINUTE_IN_MILLIS
    )
        .toString()

}

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun onOpenWhatsApp(contact: String, context: Context) {

    val url = "https://api.whatsapp.com/send?phone=$contact"
    try {
        val pm: PackageManager = context.getPackageManager()
        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context.startActivity(i)
    } catch (e: PackageManager.NameNotFoundException) {
        Toast.makeText(
            context,
            "Whatsapp app not installed in your phone",
            Toast.LENGTH_SHORT
        ).show()
        e.printStackTrace()
    }
}

fun Context.openNotificationSettings() {
    val intent = Intent()
    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
    intent.putExtra("android.provider.extra.APP_PACKAGE", this.packageName)
    startActivity(intent)
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

fun onCallApp(contact: String, context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$contact")
    context.startActivity(intent)
}

fun showCalender(
    context: Context,
    text_view_data: TextView,
    data1: DateTxt
) {
    val mDatePicker = DatePickerDialog(
        context, AlertDialog.BUTTON_POSITIVE,
        { _, selectedYear, selectedMonth, selectedDay ->
            val symbols = DecimalFormatSymbols(Locale.US)
            val mFormat = DecimalFormat("00", symbols)
            val data =
                selectedYear.toString() + "/" + mFormat.format((selectedMonth + 1.toDouble())) + "/" + mFormat.format(
                    selectedDay.toDouble()
                )

            data1.date =
                selectedYear.toString() + "-" + mFormat.format((selectedMonth + 1.toDouble())) + "-" + mFormat.format(
                    selectedDay.toDouble()
                )
            data1.date_txt = (data)
            data1.date_txt =
                (mFormat.format(selectedDay.toDouble()))
            data1.month =
                (mFormat.format((selectedMonth + 1.toDouble())))

            data1.day = mFormat.format(selectedDay.toDouble())
            data1.month = mFormat.format(selectedMonth.toDouble())
            data1.year = (selectedYear.toString())
            text_view_data.text = data

        },
        data1.year?.toInt()!!,
        data1.month?.toInt()!!,
        data1.day?.toInt()!!
    )
    mDatePicker.show()
}

fun showTimer(context: Context, text_view_data: TextView, time: TimeTxt) {
    val mTimePicker = IntervalTimePicker(
        context, { view, hourOfDay, minute ->

            val symbols = DecimalFormatSymbols(Locale.US)
            val mFormat = DecimalFormat("00", symbols)
            time.time =
                mFormat.format(hourOfDay.toDouble()) + ":" + mFormat.format(
                    minute.toDouble()
                ) + ":00"

            val data =
                mFormat.format(hourOfDay.toDouble()) + ":" + mFormat.format(
                    minute.toDouble()
                )
            time.time_txt = (data)
            time.second = ("00")
            time.minute =
                (mFormat.format(minute.toDouble()))
            time.hour = (mFormat.format(hourOfDay.toDouble()))
            text_view_data.text = data
        }, time.hour?.toInt()!!, time.minute?.toInt()!!, true
    )

    mTimePicker.show()
}


data class DateTxt(
    var day: String?,
    var month: String?,
    var year: String?,
    var date_txt: String?,
    var date: String?
)

data class TimeTxt(
    var hour: String?,
    var minute: String?,
    var second: String?,
    var time_txt: String?,
    var time: String?
)
