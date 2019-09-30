package ru.orehovai.testqiwi.utils


import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout



object BindingAdapters {

    @BindingAdapter("errorText")
    @JvmStatic
    fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
        view.error = errorMessage
    }

    @BindingAdapter("customInput")
    @JvmStatic
    fun setCustomInput(view: TextInputEditText, inputType: String) {
        if (inputType.isNotEmpty()){
            //этот код вызывает сообщение об ошибке в эдиттекст.причину выянить не смог и поставил вручную
            //if (inputType == "numeric") view.inputType = InputType.TYPE_CLASS_NUMBER
            /*else view.inputType = InputType.TYPE_CLASS_TEXT*/
        }
    }
}
