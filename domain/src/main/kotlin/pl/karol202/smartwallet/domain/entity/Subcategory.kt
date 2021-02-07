package pl.karol202.smartwallet.domain.entity

data class Subcategory<I : Id>(val id: I,
                               val categoryId: String,
                               val name: String,
                               val isOthers: Boolean)
