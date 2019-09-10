package ru.orehovai.testqiwi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
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

        val tvCardOrAccountSpinner = binding.idCardNumber
        val tvUrgentSpinner = binding.paymentType

        //android:onItemSelected="@{(parent, view, position, id) -> mainViewModel.onAccountTypeItemClick(parent, position)}"
        tvCardOrAccountSpinner.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            mainViewModel.onAccountTypeItemClick(parent, position)
        }


        mainViewModel.getForm()

        mainViewModel.getFormData().observe(this, Observer {
            if (it != null) {
                mainViewModel.makeSpinner(it.content.elements)
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
                    it.view.widget.choices
                )

                tvCardOrAccountSpinner.setAdapter(arrayAdapter)
                tvCardOrAccountSpinner.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        tvCardOrAccountSpinner.showDropDown()
                        return true
                    }
                })

            }
        })

        mainViewModel.urgentFull.observe(this, Observer {
            if (it != null){
                val urgentAdapter = AccountAdapter(
                    this,
                    R.layout.activity_main,
                    R.id.autoText,
                    it.view.widget.choices
                )

                tvUrgentSpinner.setAdapter(urgentAdapter)
                tvUrgentSpinner.setOnClickListener {
                    tvUrgentSpinner.showDropDown() }
            }
        })

    }

}
