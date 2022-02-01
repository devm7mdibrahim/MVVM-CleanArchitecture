package com.aait.utils.spinner

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

object SpinnerUtil {

    fun setSpinnerAdapter(
        context: Context,
        spinner: Spinner,
        list: List<String>,
        view: Int? = null,
        onItemSelected: (Int) -> Unit
    ): ArrayAdapter<String> {

        val arrayAdapter = ArrayAdapter(context, view ?: android.R.layout.simple_spinner_item, list)
        arrayAdapter.setDropDownViewResource(view ?: android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                onItemSelected(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        return arrayAdapter
    }
}