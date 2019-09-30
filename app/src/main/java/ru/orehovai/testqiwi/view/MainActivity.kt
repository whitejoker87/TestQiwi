package ru.orehovai.testqiwi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.orehovai.testqiwi.databinding.ActivityMainBinding
import ru.orehovai.testqiwi.viewmodel.MainViewModel
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.orehovai.testqiwi.R
import ru.orehovai.testqiwi.model.Element
import ru.orehovai.testqiwi.viewmodel.updateCorrect
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    lateinit var textMfo: Disposable
    lateinit var textAccountOrCard: Disposable
    lateinit var textFName: Disposable
    lateinit var textLName: Disposable
    lateinit var textMName: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        val tvCardOrAccountSpinner = binding.idCardNumber
        val tvUrgentSpinner = binding.paymentType

        textMfo = fillDisposable(et_mfo_number, mainViewModel.mfoFull)
        textAccountOrCard = fillDisposable(et_account_or_card_number, mainViewModel.accountOrCardNumberFull)
        textFName = fillDisposable(et_first_name, mainViewModel.fNameFull)
        textLName = fillDisposable(et_last_name, mainViewModel.lNameFull)
        textMName = fillDisposable(et_middle_name, mainViewModel.mNameFull)

        mainViewModel.getForm()

        mainViewModel.getFormData().observe(this, Observer {
            if (it != null) {
                mainViewModel.makeSpinner(it.content.elements)
            }
        })


        mainViewModel.accountTypeFull.observe(this, Observer {
            if (it != null) {
                val arrayAdapter = AccountAdapter(
                    this,
                    R.layout.activity_main,
                    R.id.autoText,
                    it.view.widget.choices
                )

                tvCardOrAccountSpinner.setAdapter(arrayAdapter)
                tvCardOrAccountSpinner.setOnClickListener {
                    tvCardOrAccountSpinner.showDropDown()
                }
            }
        })

        mainViewModel.urgentFull.observe(this, Observer {
            if (it != null && it.name == "urgent") {
                val urgentAdapter = AccountAdapter(
                    this,
                    R.layout.activity_main,
                    R.id.autoText,
                    it.view.widget.choices
                )

                tvUrgentSpinner.setAdapter(urgentAdapter)
                tvUrgentSpinner.setOnClickListener {
                    tvUrgentSpinner.showDropDown()
                }
            }
        })

        //android:onItemSelected="@{(parent, view, position, id) -> mainViewModel.onAccountTypeItemClick(parent, position)}"
        tvCardOrAccountSpinner.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            mainViewModel.onAccountTypeItemClick(parent, position)
        }

        tvUrgentSpinner.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            mainViewModel.onUrgentItemClick(parent, position)
        }

    }


    override fun onDestroy() {
        textMfo.dispose()
        textAccountOrCard.dispose()
        textFName.dispose()
        textLName.dispose()
        textMName.dispose()
        super.onDestroy()
    }


//    android:onTextChanged = "@{(text, start, before, count) -> mainViewModel.onFormFieldTextChanged(text, mainViewModel.mfoFull)}"
    private fun fillDisposable(field: TextView, element: MutableLiveData<Element>): Disposable =
        RxTextView.textChanges(field)
            //.skip(1)
            .throttleLatest(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it -> element.value!!.validator.predicate.pattern.toRegex().matches(it) }
            .subscribe { result -> element.updateCorrect(result) }

}

//comment for review
