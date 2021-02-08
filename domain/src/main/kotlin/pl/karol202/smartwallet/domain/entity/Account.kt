package pl.karol202.smartwallet.domain.entity

data class Account<I : Id>(val id: I,
                           val name: String)
