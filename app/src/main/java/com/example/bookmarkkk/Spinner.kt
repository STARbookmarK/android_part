package com.example.bookmarkkk

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

class Spinner(context: Context) {
    private var context: Context = context

    fun stateSpinnerSet(): ArrayAdapter<CharSequence> {
        var adapter =
            ArrayAdapter.createFromResource(context, R.array.state, R.layout.state_spinner_style)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    fun rankSpinnerSet(): ArrayAdapter<CharSequence> {
        var adapter =
            ArrayAdapter.createFromResource(context, R.array.rank, R.layout.rank_spinner_style)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}