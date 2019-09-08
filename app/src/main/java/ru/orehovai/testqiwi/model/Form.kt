package ru.orehovai.testqiwi.model

import com.google.gson.annotations.SerializedName

data class FormResponse (

	@SerializedName("content") val content : Content
)

data class Content (

    @SerializedName("elements") val elements : List<Elements>
)

data class Elements (

    @SerializedName("type") var type : String,
    @SerializedName("name") var name : String,
    @SerializedName("value") var value : String,
    @SerializedName("validator") var validator : Validator,
    @SerializedName("view") var view : View,
    @SerializedName("condition") var condition : Condition,
    @SerializedName("content") var content : Content,
    @SerializedName("semantics") var semantics : Semantics

)

data class Validator(

    @SerializedName("type") var type : String,
    @SerializedName("predicate") var predicate : Predicate,
    @SerializedName("message") var message : String

    )

data class Predicate (

    @SerializedName("type") var type : String,
    @SerializedName("pattern") var pattern : String

)

data class View (

    @SerializedName("title") var title : String,
    @SerializedName("prompt") var prompt : String,
    @SerializedName("widget") var widget : Widget

)

data class Widget (

    @SerializedName("type") var type : String,
    @SerializedName("choices") var choices : List<Choice>,
    @SerializedName("keyboard") var keyboard : String

)

data class Choice (

    @SerializedName("value") var value : String,
    @SerializedName("title") var title : String

)

data class Condition (

    @SerializedName("type") var type : String,
    @SerializedName("field") var field : String,
    @SerializedName("predicate") var predicate : Predicate

)

data class Semantics (

    @SerializedName("type") var type : String

)