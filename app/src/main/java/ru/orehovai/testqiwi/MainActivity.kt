package ru.orehovai.testqiwi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.orehovai.testqiwi.databinding.ActivityMainBinding
import ru.orehovai.testqiwi.viewmodel.MainViewModel
import android.widget.AdapterView




class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        val textView = binding.idCardNumber

//android:onItemSelected="@{(parent, view, position, id) -> mainViewModel.onAccountTypeItemClick(parent, position)}"
        textView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            mainViewModel.onAccountTypeItemClick(parent, position)
        }
        mainViewModel.getForm()

        mainViewModel.getFormData().observe(this, Observer {
            if (it != null) {
                mainViewModel.makeForm()
            }
        })

        mainViewModel.accountTypeFull.observe(this, Observer {
            if (it != null){
                binding.invalidateAll()
                //binding.executePendingBindings()
                val arrayAdapter = AccountAdapter(
                    this,
                    R.layout.activity_main,
                    R.id.autoText,
                    mainViewModel.accountTypeFull.value!!.view.widget.choices
                )

                textView.setAdapter(arrayAdapter)
                textView.setOnClickListener {
                    textView.showDropDown() }
            }
        })

        mainViewModel.accountOrCardNumberFull.observe(this, Observer {
            if (it != null){
                //binding.invalidateAll()
            }
        })

    }

}
