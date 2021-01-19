package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.entity.asId

data class SubcategoryEditViewData(val categoryId: String,
                                   val name: String)
{
	fun withName(name: String) = copy(name = name)
}

fun Subcategory<Existing>.toEditViewData() = SubcategoryEditViewData(categoryId, name)

fun SubcategoryEditViewData.toEntity() = Subcategory(New, categoryId, name)

fun SubcategoryEditViewData.toEntity(id: String) = Subcategory(id.asId(), categoryId, name)
