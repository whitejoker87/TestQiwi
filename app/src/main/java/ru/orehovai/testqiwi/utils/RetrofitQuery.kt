package ru.orehovai.testqiwi.utils

import retrofit2.Call
import retrofit2.http.*
import ru.orehovai.testqiwi.model.FormResponse


interface RetrofitQuery {

    /*проверка на занятый адрес почты*/
    @GET("mobile/form/form.json")
    fun getForm(): Call<FormResponse>

}


