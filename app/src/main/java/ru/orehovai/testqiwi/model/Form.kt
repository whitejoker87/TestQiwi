package ru.orehovai.testqiwi.model

import com.google.gson.annotations.SerializedName

data class FormResponse (

	@SerializedName("content") val content : Content
)

data class Content (

    @SerializedName("elements") val elements : List<Elements>
)

data class Elements (

    @SerializedName("type") val type : String,
    @SerializedName("name") val name : String,
    @SerializedName("value") val value : String,
    @SerializedName("validator") val validator : Validator,
    @SerializedName("view") val view : View,
    @SerializedName("condition") val condition : Condition,
    @SerializedName("content") val content : Content,
    @SerializedName("semantics") val semantics : Semantics

)

data class Validator(

    @SerializedName("type") val type : String,
    @SerializedName("predicate") val predicate : Predicate,
    @SerializedName("message") val message : String

    )

data class Predicate (

    @SerializedName("type") val type : String,
    @SerializedName("pattern") val pattern : String

)

data class View (

    @SerializedName("title") val title : String,
    @SerializedName("prompt") val prompt : String,
    @SerializedName("widget") val widget : Widget

)

data class Widget (

    @SerializedName("type") val type : String,
    @SerializedName("choices") val choices : List<Choice>,
    @SerializedName("keyboard") val keyboard : String

)

data class Choice (

    @SerializedName("value") val value : String,
    @SerializedName("title") val title : String

)

data class Condition (

    @SerializedName("type") val type : String,
    @SerializedName("field") val field : String,
    @SerializedName("predicate") val predicate : Predicate

)

data class Semantics (

    @SerializedName("type") val type : String

)