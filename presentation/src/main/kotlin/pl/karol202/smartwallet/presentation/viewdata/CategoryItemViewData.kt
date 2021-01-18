package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing

data class CategoryItemViewData(val id: String,
                                val name: String,
                                val type: CategoryTypeViewData)

fun Category<Existing>.toItemViewData() = CategoryItemViewData(id.value, name, type.toViewData())
