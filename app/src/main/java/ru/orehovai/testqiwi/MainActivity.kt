package ru.orehovai.testqiwi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.orehovai.testqiwi.databinding.ActivityMainBinding
import ru.orehovai.testqiwi.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = mainViewModel

        val textView = binding.idCardNumber

        mainViewModel.getForm()

        mainViewModel.getFormData().observe(this, Observer {
            if (it != null){
                for ()
                val arrayAdapter = ArrayAdapter(
                    this, android.R.layout.simple_dropdown_item_1line, mainViewModel.getFormData().value!!.content.elements[1].view.widget.choices
                    //resources.getStringArray(R.array.names)
                )

                textView.setAdapter(arrayAdapter)
                textView.setOnClickListener { textView.showDropDown() }
            }
        })
    }

}
