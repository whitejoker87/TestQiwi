package ru.orehovai.testqiwi.viewmodel

import android.app.Application
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.orehovai.testqiwi.model.Choice
import ru.orehovai.testqiwi.model.Elements
import ru.orehovai.testqiwi.model.FormResponse
import ru.orehovai.testqiwi.utils.Retrofit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    val formData = MutableLiveData<FormResponse>()

    val accountType = MutableLiveData<Choice>()

    var validatorRegexAccountType = Regex("")


    val accountTypeFull = MutableLiveData<Elements>(Elements())
    val mfoFull = MutableLiveData<Elements>(Elements())
    val accountOrCardNumberFull = MutableLiveData<Elements>(Elements())
    val urgentFull = MutableLiveData<Elements>(Elements())
    val fNameFull = MutableLiveData<Elements>(Elements())
    val lNameFull = MutableLiveData<Elements>(Elements())
    val mNameFull = MutableLiveData<Elements>(Elements())


    /*values of form data*/


    fun setFormData(data: FormResponse) {
        formData.value = data
    }

    fun getFormData(): LiveData<FormResponse> = formData

    fun setAccountType(value: Choice) {
        accountType.value = value
    }

//    fun getAccountType(): LiveData<Choice> = accountType

    fun getForm() {
        Retrofit.api?.getForm()?.enqueue(object : Callback<FormResponse> {
            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                Toast.makeText(context, "Ошибка при загрузке данных!", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
                if (response.body() != null) {
                    setFormData(response.body()!!)
                }
            }

        })
    }

    fun makeForm() {
        for (element in formData.value!!.content.elements) {
            if (element.type == "field" && element.name == "account_type") {
                validatorRegexAccountType = element.validator.predicate.pattern.toRegex()
                if (element.validator.message.isNotEmpty() &&
                    element.view.title.isNotEmpty() &&
                    element.view.prompt.isNotEmpty()
                ) {
                    accountTypeFull.value = element
                }
            }
        }

    }

    fun onAccountTypeItemClick(listView: AdapterView<*>, position: Int) {

        clearValues()

        val choice = listView.getItemAtPosition(position) as Choice

        for (element in formData.value!!.content.elements) {
            setAccountType(choice)
            if (element.type == "dependency") {
                if (validatorRegexAccountType.matches(choice.value)) {

                    if (element.condition.predicate.pattern.toRegex().matches(choice.value)) {
                        for (elementInner in element.content.elements) {
                            when (elementInner.name) {
                                "mfo" -> { mfoFull.value = elementInner }
                                "account" -> { accountOrCardNumberFull.value = elementInner}
                                "urgent" -> { urgentFull.value = elementInner}
                                "lname" -> { lNameFull.value = elementInner}
                                "fname" -> { fNameFull.value = elementInner}
                                "mname" -> { mNameFull.value = elementInner}
                            }
                        }

                    }
                }
            }
//            if (element.type == "dependency" && element.condition.field == "account_type") {
//                if (element.condition.predicate.pattern.toRegex().matches(choice.value)) {
//                    when (choice.value) {
//                        2 -> {}
//                        5 -> {}
//                    }
//                }
//            }
        }

    }

    private fun clearValues() {
        mfoFull.value = Elements()
        accountOrCardNumberFull.value = Elements()
        urgentFull.value = Elements()
        lNameFull.value = Elements()
        fNameFull.value = Elements()
        mNameFull.value = Elements()
    }

}