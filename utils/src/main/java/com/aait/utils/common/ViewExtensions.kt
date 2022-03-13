package com.aait.utils.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// threshold for when contents of collapsing toolbar is hidden
const val COLLAPSING_TOOLBAR_VISIBILITY_THRESHOLD = -75
const val CLICK_THRESHOLD = 150L // a click is considered 150ms or less
const val CLICK_COLOR_CHANGE_TIME = 250L

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

fun View.onSelectChangeColor(
    lifeCycleScope: CoroutineScope,
    clickColor: Int
) = CoroutineScope(lifeCycleScope.coroutineContext).launch {
    val intialColor = (background as ColorDrawable).color
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            clickColor
        )
    )
    delay(CLICK_COLOR_CHANGE_TIME)
    setBackgroundColor(intialColor)
}

fun View.changeColor(newColor: Int) {
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}

fun EditText.disableContentInteraction() {
    keyListener = null
    isFocusable = false
    isFocusableInTouchMode = false
    isCursorVisible = false
    setBackgroundResource(android.R.color.transparent)
    clearFocus()
}

fun EditText.enableContentInteraction() {
    keyListener = EditText(context).keyListener
    isFocusable = true
    isFocusableInTouchMode = true
    isCursorVisible = true
    setBackgroundResource(android.R.color.white)
    requestFocus()
    if (text != null) {
        setSelection(text.length)
    }
}


//fun View.onClick(onClick: () -> Unit) {
//    performClick()
//
//    this.clicks()
//        .throttleFirst(2, TimeUnit.SECONDS)
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe {
//            onClick()
//        }
//
//}

fun EditText.fetchText(): String {
    return this.text.toString().trim()
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

//fun Context.setSelectedStyle(view: TextView) {
//    view.let {
//        TextViewCompat.setTextAppearance(it, R.style.SelectedTextViewStyle)
//        it.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
//    }
//}
//
//fun Context.setNotSelectedStyle(view: TextView) {
//    view.let {
//        TextViewCompat.setTextAppearance(it, R.style.NotSelectedTextViewStyle)
//        it.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
//    }
//}











