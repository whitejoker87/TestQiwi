package ru.orehovai.testqiwi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.PendingIntent.getActivity
import android.database.Cursor
import android.text.InputFilter
import android.text.Spanned
import android.view.MotionEvent
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import java.security.AccessController.getContext
import java.util.Locale.filter




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: AutoCompleteTextView = findViewById(R.id.newMeasure)
        val arrayAdapter = ArrayAdapter(
           this, android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.names)
        )

        textView.setAdapter(arrayAdapter)
        textView.setOnClickListener { textView.showDropDown() }
    }

}
