package ru.orehovai.testqiwi.utils


import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout



object BindingAdapters {

    @BindingAdapter("errorText")
    @JvmStatic
    fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
        view.error = errorMessage
    }

    @BindingAdapter("customInput")
    @JvmStatic
    fun setCustomInput(view: EditText, inputType: Int) {
        view.inputType = inputType
    }
}
