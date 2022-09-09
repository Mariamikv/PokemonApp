package com.example.pokemon.data.responses


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeX
)