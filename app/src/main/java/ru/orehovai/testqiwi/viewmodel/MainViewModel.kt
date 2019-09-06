package ru.orehovai.testqiwi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.orehovai.testqiwi.model.FormResponse
import ru.orehovai.testqiwi.utils.Retrofit
import java.text.FieldPosition

class MainViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    val formData = MutableLiveData<FormResponse>()

    fun setFormData(data: FormResponse) {
        formData.value = data
    }

    fun getFormData(): LiveData<FormResponse> = formData

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

            }
        }

    }

    fun onAccountTypeItemClick(position: Int) {

        when (position) {
            2 -> {}
            5 -> {}
        }

    }

}