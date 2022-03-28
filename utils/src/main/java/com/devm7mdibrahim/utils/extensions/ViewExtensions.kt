package com.devm7mdibrahim.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.devm7mdibrahim.utils.R


fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.fadeIn() {
    val animationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
    apply {
        toVisible()
        alpha = 0f
        animate()
            .alpha(1f)
            .setDuration(animationDuration.toLong())
            .setListener(null)
    }
}

fun View.fadeOut(todoCallback: () -> Unit) {
    val animationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
    apply {
        animate()
            .alpha(0f)
            .setDuration(animationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    toInvisible()
                    todoCallback.invoke()
                }
            })
    }
}

fun View.changeColor(newColor: Int) {
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}

fun View.onBackPressed(backPressed: () -> Unit) {
    isFocusableInTouchMode = true
    requestFocus()

    setOnKeyListener(object : View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    backPressed.invoke()
                    return true
                }
            }
            return false
        }
    })
}

fun View.onClick(clickListener: () -> Unit) {
    setOnClickListener {
        hideKeyboard()
        clickListener.invoke()
    }
}


fun ImageView.loadImageFromUrl(url: String?) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(this.context)
        .load(url).placeholder(circularProgressDrawable)
        .into(this)
}

fun ImageView.loadImageFromUri(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadImageFromDrawable(context: Context, url: Int) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.setColorTint(resColor: Int) {
    this.setColorFilter(
        ContextCompat.getColor(context, resColor),
        android.graphics.PorterDuff.Mode.MULTIPLY
    )
}

fun EditText.fetchText(): String {
    return this.text.toString().trim()
}

@Suppress("DEPRECATION")
fun TextView.setHtml(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(text)
    }
}

fun AppCompatActivity.setBackToolbar(
    title: String,
    toolbar: Toolbar?,
) {
    this.setSupportActionBar(toolbar)
    this.supportActionBar?.title = title
    this.supportActionBar?.setDisplayShowTitleEnabled(true)
    this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar?.setNavigationIcon(R.drawable.ic_back)
}