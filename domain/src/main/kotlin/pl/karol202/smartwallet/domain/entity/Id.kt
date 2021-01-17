package pl.karol202.smartwallet.domain.entity

sealed class Id

object New : Id()

data class Existing(val value: String) : Id()

fun String.asId() = Existing(this)
