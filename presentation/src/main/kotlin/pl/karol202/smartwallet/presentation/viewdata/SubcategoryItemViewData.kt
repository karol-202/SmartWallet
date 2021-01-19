package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory

data class SubcategoryItemViewData(val id: String,
                                   val name: String)

fun Subcategory<Existing>.toItemViewData() = SubcategoryItemViewData(id.value, name)
