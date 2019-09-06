package ru.orehovai.testqiwi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: AutoCompleteTextView = findViewById(R.id.id_card_number)
        val arrayAdapter = ArrayAdapter(
           this, android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.names)
        )

        textView.setAdapter(arrayAdapter)
        textView.setOnClickListener { textView.showDropDown() }
    }

}
