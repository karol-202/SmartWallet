package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.asId

data class CategoryEditViewData(val name: String,
                                val type: CategoryTypeViewData)
{
	fun withName(name: String) = copy(name = name)

	fun withType(type: CategoryTypeViewData) = copy(type = type)
}

fun Category<Existing>.toEditViewData() = CategoryEditViewData(name, type.toViewData())

fun CategoryEditViewData.toEntity() = Category(New, name, type.toEntity(), true)

fun CategoryEditViewData.toEntity(id: String) = Category(id.asId(), name, type.toEntity(), true)
