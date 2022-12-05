package com.example.bookmarkkk.ui

import android.content.Context
import android.widget.ArrayAdapter
import com.example.bookmarkkk.R

class Spinner(private val context: Context) {

    fun setRankSpinner(): ArrayAdapter<CharSequence> {
        val adapter =
            ArrayAdapter.createFromResource(context, R.array.rank, R.layout.rank_spinner_style)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}
