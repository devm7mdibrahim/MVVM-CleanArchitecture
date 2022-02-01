package com.aait.utils.common

import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.NumberPicker
import android.widget.TimePicker
import java.util.*

class IntervalTimePicker(
    context: Context?, private val mTimeSetListener: OnTimeSetListener?,
    hourOfDay: Int, minute: Int, is24HourView: Boolean
) : TimePickerDialog(
    context, android.R.style.Theme_Material_Light_Dialog_Alert, null, hourOfDay,
    minute / TIME_PICKER_INTERVAL, is24HourView
) {
    private var mTimePicker: TimePicker? = null

    @Suppress("DEPRECATION")
    override fun updateTime(hourOfDay: Int, minuteOfHour: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker!!.hour = hourOfDay
            mTimePicker!!.minute = minuteOfHour / TIME_PICKER_INTERVAL
        } else {
            mTimePicker!!.currentHour = hourOfDay
            mTimePicker!!.currentMinute = minuteOfHour / TIME_PICKER_INTERVAL
        }
    }

    @Suppress("DEPRECATION")
    override fun onClick(dialog: DialogInterface, which: Int) {
        when (which) {
            BUTTON_POSITIVE -> mTimeSetListener?.onTimeSet(
                mTimePicker,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    mTimePicker!!.hour
                else mTimePicker!!.currentHour,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    mTimePicker!!.minute * TIME_PICKER_INTERVAL
                else mTimePicker!!.currentMinute * TIME_PICKER_INTERVAL
            )
            BUTTON_NEGATIVE -> cancel()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {

            mTimePicker = findViewById<View>(
                Resources.getSystem().getIdentifier(
                    "timePicker",
                    "id",
                    "android"
                )
            ) as TimePicker

            val minuteSpinner = mTimePicker!!.findViewById<View>(
                Resources.getSystem().getIdentifier(
                    "minute",
                    "id",
                    "android"
                )
            ) as NumberPicker


            minuteSpinner.minValue = 0
            minuteSpinner.maxValue = 60 / TIME_PICKER_INTERVAL - 1
            val displayedValues: MutableList<String> = ArrayList()
            var i = 0
            while (i < 60) {
                displayedValues.add(String.format("%02d", i))
                i += TIME_PICKER_INTERVAL
            }

            minuteSpinner.displayedValues = displayedValues.toTypedArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TIME_PICKER_INTERVAL = 30
    }
}