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
import ru.orehovai.testqiwi.model.Element
import ru.orehovai.testqiwi.model.FormResponse
import ru.orehovai.testqiwi.utils.Retrofit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    val formData = MutableLiveData<FormResponse>()

    private val accountType = MutableLiveData<Choice>()
    private val urgentType = MutableLiveData<Choice>()

    var validatorRegex = Regex("")


    val accountTypeFull = MutableLiveData<Element>(Element())
    val mfoFull = MutableLiveData<Element>(Element())
    val accountOrCardNumberFull = MutableLiveData<Element>(Element())
    val urgentFull = MutableLiveData<Element>(Element())
    val fNameFull = MutableLiveData<Element>(Element())
    val lNameFull = MutableLiveData<Element>(Element())
    val mNameFull = MutableLiveData<Element>(Element())

    fun setFormData(data: FormResponse) {
        formData.value = data
    }

    fun getFormData(): LiveData<FormResponse> = formData

    fun setAccountType(value: Choice) {
        accountType.value = value
    }

    fun setUrgentType(value: Choice) {
        urgentType.value = value
    }


    fun getAccountType(): LiveData<Choice> = accountType

    fun getUrgentType(): LiveData<Choice> = urgentType

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

    fun makeSpinner(listElements: List<Element>) {
        for (element in listElements) {
            if (element.type == "field" && (element.name == "account_type" /*|| element.name == "urgent"*/)) {
                validatorRegex = element.validator.predicate.pattern.toRegex()
                if (element.validator.message.isNotEmpty() &&
                    element.view.title.isNotEmpty() &&
                    element.view.prompt.isNotEmpty()
                ) {
                    if (element.name == "account_type") accountTypeFull.value = element
                    //else if (element.name == "urgent") urgentFull.value = element
                }
            }
        }

    }

    fun onAccountTypeItemClick(listView: AdapterView<*>, position: Int) {

        clearValues()

        val choice = listView.getItemAtPosition(position) as Choice

        setAccountType(choice)

        for (element in formData.value!!.content.elements) {

            if (element.type == "dependency") {
                if (validatorRegex.matches(choice.value)) {

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
        }
    }

    fun onUrgentItemClick(listView: AdapterView<*>, position: Int) {

        setUrgentType(listView.getItemAtPosition(position) as Choice)
        //urgentFull.updateCorrect(true)

    }

    private fun clearValues() {
        mfoFull.value = Element()
        accountOrCardNumberFull.value = Element()
        urgentFull.value = Element()
        lNameFull.value = Element()
        fNameFull.value = Element()
        mNameFull.value = Element()
    }


    fun onFormFieldTextChanged(value: CharSequence?, element: MutableLiveData<Element>) {
        if (value != null) {
            element.updateCorrect(element.value!!.validator.predicate.pattern.toRegex().matches(value))
        }
    }

    fun onSpinnerTextChanged(value: CharSequence?, element: MutableLiveData<Element>) {
        if (value != null) {
            element.updateCorrect(element.value!!.validator.predicate.pattern.toRegex().matches(urgentType.value!!.title))
        }
    }
//    fun onAccountOrCardNumberTextChanged(accountOrCardNumber: CharSequence?) {
//        if (confirmPassword != null) {
//            if (!TextUtils.isEmpty(confirmPassword)) {
//                when {
//                    confirmPassword.length < 6 -> setConfirmPasswordCorrect(false)
//                    confirmPassword.toString() != getTextPassword().value -> setEqualsPasswords(false)
//                    else -> {
//                        setConfirmPasswordCorrect(true)
//                        setEqualsPasswords(true)
//                    }
//                }
//            }
//        }
//    }
//
//    fun onUrgentTextChanged(urgent: CharSequence?) {
//        if (confirmPassword != null) {
//            if (!TextUtils.isEmpty(confirmPassword)) {
//                when {
//                    confirmPassword.length < 6 -> setConfirmPasswordCorrect(false)
//                    confirmPassword.toString() != getTextPassword().value -> setEqualsPasswords(false)
//                    else -> {
//                        setConfirmPasswordCorrect(true)
//                        setEqualsPasswords(true)
//                    }
//                }
//            }
//        }
//    }
//
//    fun onFNameTextChanged(fName: CharSequence?) {
//        if (confirmPassword != null) {
//            if (!TextUtils.isEmpty(confirmPassword)) {
//                when {
//                    confirmPassword.length < 6 -> setConfirmPasswordCorrect(false)
//                    confirmPassword.toString() != getTextPassword().value -> setEqualsPasswords(false)
//                    else -> {
//                        setConfirmPasswordCorrect(true)
//                        setEqualsPasswords(true)
//                    }
//                }
//            }
//        }
//    }
//
//    fun onLNameTextChanged(lName: CharSequence?) {
//        if (confirmPassword != null) {
//            if (!TextUtils.isEmpty(confirmPassword)) {
//                when {
//                    confirmPassword.length < 6 -> setConfirmPasswordCorrect(false)
//                    confirmPassword.toString() != getTextPassword().value -> setEqualsPasswords(false)
//                    else -> {
//                        setConfirmPasswordCorrect(true)
//                        setEqualsPasswords(true)
//                    }
//                }
//            }
//        }
//    }
//
//    fun onMNamePasswordTextChanged(mName: CharSequence?) {
//        if (confirmPassword != null) {
//            if (!TextUtils.isEmpty(confirmPassword)) {
//                when {
//                    confirmPassword.length < 6 -> setConfirmPasswordCorrect(false)
//                    confirmPassword.toString() != getTextPassword().value -> setEqualsPasswords(false)
//                    else -> {
//                        setConfirmPasswordCorrect(true)
//                        setEqualsPasswords(true)
//                    }
//                }
//            }
//        }
//    }



}

//функция для оповещения наблюдателей после изменения поля isCorrect обычно нужно перезаписать)
fun  MutableLiveData<Element>.updateCorrect(isCorrect: Boolean) {
    val updatedElement = this.value as Element
    updatedElement.isCorrect = isCorrect
    this.value = updatedElement
}